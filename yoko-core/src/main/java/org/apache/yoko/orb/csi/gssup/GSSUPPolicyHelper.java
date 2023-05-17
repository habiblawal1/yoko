/*==============================================================================
 * Copyright 2010 IBM Corporation and others.
 * 
 * Licensed under the Apache License, Version 2.0 (the License);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *=============================================================================*/
package org.apache.yoko.orb.csi.gssup;

/**
 * Generated by the JacORB IDL compiler.
 */
public class GSSUPPolicyHelper {

    public GSSUPPolicyHelper() {
    }

    public static void insert(org.omg.CORBA.Any any, GSSUPPolicy s) {
        any.insert_Object(s);
    }

    public static GSSUPPolicy extract(org.omg.CORBA.Any any) {
        return narrow(any.extract_Object());
    }

    public static org.omg.CORBA.TypeCode type() {
        return org.omg.CORBA.ORB.init().create_interface_tc("IDL:org/apache/yoko/orb/csi/gssup/GSSUPPolicy:1.0", "GSSUPPolicy");
    }

    public static String id() {
        return "IDL:org/apache/yoko/orb/csi/gssup/GSSUPPolicy:1.0";
    }

    public static GSSUPPolicy read(org.omg.CORBA.portable.InputStream in) {
        return narrow(in.read_Object());
    }

    public static void write(org.omg.CORBA.portable.OutputStream _out, GSSUPPolicy s) {
        _out.write_Object(s);
    }

    public static GSSUPPolicy narrow(org.omg.CORBA.Object obj) {
        if (obj == null)
            return null;
        if (obj instanceof GSSUPPolicy)
            return (GSSUPPolicy) obj;
        else
            throw new org.omg.CORBA.BAD_PARAM("Narrow failed, not a GSSUPPolicy");
    }

    public void write_Object(org.omg.CORBA.portable.OutputStream _out, java.lang.Object obj) {
        throw new RuntimeException(" not implemented");
    }

    public java.lang.Object read_Object(org.omg.CORBA.portable.InputStream in) {
        throw new RuntimeException(" not implemented");
    }

    public String get_id() {
        return id();
    }

    public org.omg.CORBA.TypeCode get_type() {
        return type();
    }
}
