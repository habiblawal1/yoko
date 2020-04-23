/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
*  contributor license agreements.  See the NOTICE file distributed with
*  this work for additional information regarding copyright ownership.
*  The ASF licenses this file to You under the Apache License, Version 2.0
*  (the "License"); you may not use this file except in compliance with
*  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.apache.yoko.orb.OCI.IIOP;

import org.apache.yoko.orb.CORBA.OutputStream;
import org.apache.yoko.orb.OB.Assert;
import org.apache.yoko.orb.OBPortableServer.POAPolicies;
import org.apache.yoko.orb.OCI.Acceptor;
import org.apache.yoko.orb.OCI.ProfileInfo;
import org.apache.yoko.orb.OCI.ProfileInfoSeqHolder;
import org.apache.yoko.orb.OCI.Transport;
import org.omg.CORBA.LocalObject;
import org.omg.CORBA.NO_IMPLEMENT;
import org.omg.CORBA.SystemException;
import org.omg.IIOP.ProfileBody_1_0;
import org.omg.IIOP.ProfileBody_1_0Helper;
import org.omg.IIOP.ProfileBody_1_1;
import org.omg.IIOP.ProfileBody_1_1Helper;
import org.omg.IIOP.Version;
import org.omg.IOP.Codec;
import org.omg.IOP.IOR;
import org.omg.IOP.IORHolder;
import org.omg.IOP.TAG_ALTERNATE_IIOP_ADDRESS;
import org.omg.IOP.TAG_INTERNET_IOP;
import org.omg.IOP.TaggedComponent;
import org.omg.IOP.TaggedProfile;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.BindException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.apache.yoko.orb.OB.MinorCodes.MinorAccept;
import static org.apache.yoko.orb.OB.MinorCodes.MinorBind;
import static org.apache.yoko.orb.OB.MinorCodes.MinorConnectFailed;
import static org.apache.yoko.orb.OB.MinorCodes.MinorSetsockopt;
import static org.apache.yoko.orb.OB.MinorCodes.MinorSocket;
import static org.apache.yoko.orb.OCI.IIOP.Acceptor_impl.ProfileCardinality.ZERO;
import static org.apache.yoko.orb.OCI.IIOP.Exceptions.asCommFailure;
import static org.apache.yoko.orb.OCI.IIOP.Exceptions.asTransient;

final class Acceptor_impl extends LocalObject implements Acceptor {
    static final Logger logger = Logger.getLogger(Acceptor_impl.class.getName());

    enum ProfileCardinality { ZERO, ONE, MANY }

    // Some data members must not be private because the info object must be able to access them
    // TODO: introduce encapsulation
    public final String[] hosts_;
    public final ServerSocket socket_;
    private final ProfileCardinality profileCardinality;
    private final int port_;
    private final boolean keepAlive_;
    private final InetAddress localAddress_;
    private final AcceptorInfo_impl info_;
    private final ListenerMap listenMap_;
    private final ConnectionHelper connectionHelper_;    // plugin for managing connection config/creation
    private final ExtendedConnectionHelper extendedConnectionHelper_;
    private final Codec codec_;

    // ------------------------------------------------------------------
    // Standard IDL to Java Mapping
    // ------------------------------------------------------------------

    public String id() {
        return PLUGIN_ID.value;
    }

    public int tag() {
        return TAG_INTERNET_IOP.value;
    }

    public int handle() {
        throw new NO_IMPLEMENT();
    }

    public void close() {
        logger.log(Level.FINE, "Closing server socket with host=" + localAddress_ + ", port=" + port_, new Exception("Stack trace"));
        info_._OB_destroy();

        try {
            socket_.close();
            logger.log(Level.FINE, "Closed server socket with host=" + localAddress_ + ", port=" + port_);
        } catch (IOException ex) {
            logger.log(Level.FINE, "Exception closing server socket with host=" + localAddress_ + ", port=" + port_, ex);
        }
    }

    public void shutdown() {}

    public void listen() {}

    public Transport accept(boolean block) {
        Socket socket;
        try {
            if (!block) socket_.setSoTimeout(1);
            else socket_.setSoTimeout(0);

            logger.fine("Accepting connection for host=" + localAddress_ + ", port=" + port_);
            socket = socket_.accept();
            logger.fine("Received inbound connection on socket " + socket);
        } catch (InterruptedIOException ex) {
            if (!block) return null; // Timeout
            else {
                logger.log(Level.FINE, "Failure accepting connection for host=" + localAddress_ + ", port=" + port_, ex);
                throw asCommFailure(ex, MinorAccept);
            }
        } catch (IOException ex) {
            logger.log(Level.FINE, "Failure accepting connection for host=" + localAddress_ + ", port=" + port_, ex);
            throw asCommFailure(ex, MinorAccept);
        }

        // Set TCP_NODELAY and SO_KEEPALIVE options
        try {
            socket.setTcpNoDelay(true);
            if (keepAlive_) socket.setKeepAlive(true);
        } catch (SocketException ex) {
            logger.log(Level.FINE, "Failure configuring server connection for host=" + localAddress_ + ", port=" + port_, ex);
            throw asCommFailure(ex, MinorSetsockopt);
        }

        try {
            Transport tr = new Transport_impl(this, socket, listenMap_);
            logger.fine("Inbound connection received from " + socket.getInetAddress()); 
            return tr;
        } catch (SystemException ex) {
            try {
                socket.close();
            } catch (IOException e) {
            }
            logger.log(Level.FINE, "error creating inbound connection", ex);
            throw ex;
        }

    }

    public Transport connect_self() {
        // Create socket and connect to local address
        Socket socket = null;
        try {
            if (connectionHelper_ != null) {
                socket = connectionHelper_.createSelfConnection(localAddress_, port_);
            } else {
                socket = extendedConnectionHelper_.createSelfConnection(localAddress_, port_);
            }
        } catch (ConnectException ex) {
            logger.log(Level.FINE, "Failure making self connection for host=" + localAddress_ + ", port=" + port_, ex);
            throw asTransient(ex, MinorConnectFailed);
        } catch (IOException ex) {
            logger.log(Level.FINE, "Failure making self connection for host=" + localAddress_ + ", port=" + port_, ex);
            throw asCommFailure(ex, MinorSocket);
        }

        // Set TCP_NODELAY option
        try {
            socket.setTcpNoDelay(true);
        } catch (SocketException ex) {
            logger.log(Level.FINE, "Failure configuring self connection for host=" + localAddress_ + ", port=" + port_, ex);
            try {
                socket.close();
            } catch (IOException e) {
            }
            throw asCommFailure(ex);
        }

        // Create and return new transport
        try {
            return new Transport_impl(this, socket, listenMap_);
        } catch (SystemException ex) {
            try {
                socket.close();
            } catch (IOException e) {
            }
            throw ex;
        }
    }

    public void add_profiles(ProfileInfo profileInfo, POAPolicies policies, IORHolder iorHolder) {
        if (port_ == 0) throw new RuntimeException();
        if (profileCardinality == ZERO) return;

        final IOR ior = iorHolder.value;
        final Version version = new Version(profileInfo.major, profileInfo.minor);
        // the CSIv2 policy may require zeroing the port in the IOR
        final short port = policies.zeroPortPolicy() ? 0 : (short) port_;
        final byte[] key = profileInfo.key;

        if (profileInfo.major == 1 && profileInfo.minor == 0) {
            // IIOP 1.0 doesn't support tagged components so use one profile per host
            for (final String host : hosts_) addNewProfile_1_0(ior, version, host, port, key);

        } else {
            // Filter components according to IIOP version
            final List<TaggedComponent> piComponents = Arrays.asList(profileInfo.components);
            switch (profileCardinality) {
            case ONE:
                // Add a single tagged profile. If there are additional hosts, add a tagged component for each host.
                final String mainHost = hosts_[0];
                final String[] alternateHosts = Arrays.copyOfRange(hosts_, 1, hosts_.length);
                final List<TaggedComponent> components = new ArrayList<>(piComponents);
                for (String host : alternateHosts) {
                    try (OutputStream out = new OutputStream()) {
                        out._OB_writeEndian();
                        out.write_string(host);
                        out.write_ushort(port);
                        components.add(new TaggedComponent(TAG_ALTERNATE_IIOP_ADDRESS.value, out.copyWrittenBytes()));
                    }
                }
                addNewProfile_1_1(ior, version, mainHost, port, key, components);
                break;
            case MANY:
                // Add one profile for each host
                for (final String host : hosts_) addNewProfile_1_1(ior, version, host, port, key, piComponents);
                break;
            }
        }
    }

    private static void addNewProfile_1_0(IOR ior, Version version, String host, short port, byte[] key) {
        ProfileBody_1_0 body = new ProfileBody_1_0(version, host, port, key);
        try (OutputStream out = new OutputStream()) {
            out._OB_writeEndian();
            ProfileBody_1_0Helper.write(out, body);
            ior.profiles = Arrays.copyOf(ior.profiles, ior.profiles.length + 1);
            ior.profiles[ior.profiles.length - 1] = new TaggedProfile(TAG_INTERNET_IOP.value, out.copyWrittenBytes());
        }
    }

    private static void addNewProfile_1_1(IOR ior, Version version, String host, short port, byte[] key, List<TaggedComponent> components) {
        ProfileBody_1_1 body = new ProfileBody_1_1(version, host, port, key, components.toArray(new TaggedComponent[0]));
        try (OutputStream out = new OutputStream()) {
            out._OB_writeEndian();
            ProfileBody_1_1Helper.write(out, body);
            ior.profiles = Arrays.copyOf(ior.profiles, ior.profiles.length + 1);
            ior.profiles[ior.profiles.length - 1] = new TaggedProfile(TAG_INTERNET_IOP.value, out.copyWrittenBytes());
        }
    }

    public ProfileInfo[] get_local_profiles(IOR ior) {
        // Get local profiles for all hosts
        ProfileInfoSeqHolder profileInfoSeq = new ProfileInfoSeqHolder();
        profileInfoSeq.value = new ProfileInfo[0];

        for (String s : hosts_) {
            Util.extractAllProfileInfos(ior, profileInfoSeq, true, s, port_, true, codec_);
        }

        return profileInfoSeq.value;
    }

    public org.apache.yoko.orb.OCI.AcceptorInfo get_info() {
        return info_;
    }

    // ------------------------------------------------------------------
    // Yoko internal functions
    // Application programs must not use these functions directly
    // ------------------------------------------------------------------

    public Acceptor_impl(String address, String[] hosts, ProfileCardinality profileCardinality,
            int port, int backlog, boolean keepAlive, ConnectionHelper helper, ExtendedConnectionHelper extendedConnectionHelper, ListenerMap lm, String[] params, Codec codec) {
        Assert.ensure((helper == null) ^ (extendedConnectionHelper == null));
        this.hosts_ = hosts;
        this.profileCardinality = profileCardinality;
        this.keepAlive_ = keepAlive;
        this.connectionHelper_ = helper;
        this.extendedConnectionHelper_ = extendedConnectionHelper;
        this.codec_ = codec;
        this.info_ = new AcceptorInfo_impl(this);
        this.listenMap_ = lm;

        if (backlog == 0) backlog = 50; // 50 is the JDK's default value

        // Get the local address for use by connect_self
        try {
            // If binding to all network interfaces, use the loopback address.
            this.localAddress_ = (address == null) ? InetAddress.getLocalHost() : Util.getInetAddress(address);
        } catch (UnknownHostException ex) {
            logger.log(Level.FINE, "Host resolution failure", ex);
            throw asCommFailure(ex);
        }

        try {
            // Create socket and bind to requested network interface
            if (address == null) {
                if (connectionHelper_ != null) {
                    this.socket_ = connectionHelper_.createServerSocket(port, backlog);
                } else {
                    this.socket_ = extendedConnectionHelper_.createServerSocket(port, backlog, params);
                }
            } else {
                if (connectionHelper_ != null) {
                    this.socket_ = connectionHelper_.createServerSocket(port, backlog, localAddress_);
                } else {
                    this.socket_ = extendedConnectionHelper_.createServerSocket(port, backlog, localAddress_, params);
                }
            }

            // Read back the port. This is needed if the port was selected by
            // the operating system.
            port_ = socket_.getLocalPort();
            logger.fine("Acceptor created using socket " + socket_);
        } catch (BindException ex) {
            logger.log(Level.FINE, "Failure creating server socket for host=" + localAddress_ + ", port=" + port, ex);
            throw asCommFailure(ex, MinorBind);
        } catch (IOException ex) {
            logger.log(Level.FINE, "Failure creating server socket for host=" + localAddress_ + ", port=" + port, ex);
            throw asCommFailure(ex, MinorSocket);
        }

        // Add this entry to the listenMap_ as an endpoint to remap
        synchronized (listenMap_) {
            for (String s : hosts_) listenMap_.add(s, (short) port_);
        }
    }

    // TODO: get rid of this finalizer, and use weak refs in AccFactory_impl instead to track Acceptors going away.
    public void finalize() throws Throwable {
        if (socket_ != null) {
            close();
        }

        // remove this acceptor from the listenMap_
        synchronized (listenMap_) {
            for (String s : hosts_) listenMap_.remove(s, (short) port_);
        }
    }
    
    public String toString() {
        return "Acceptor listening on " + socket_; 
    }
}
