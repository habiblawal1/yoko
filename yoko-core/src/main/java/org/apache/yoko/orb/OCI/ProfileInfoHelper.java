/*
 * Copyright 2021 IBM Corporation and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an \"AS IS\" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package org.apache.yoko.orb.OCI;

import org.apache.yoko.util.MinorCodes;

//
// IDL:orb.yoko.apache.org/OCI/ProfileInfo:1.0
//
final public class ProfileInfoHelper
{
    public static void
    insert(org.omg.CORBA.Any any, ProfileInfo val)
    {
        org.omg.CORBA.portable.OutputStream out = any.create_output_stream();
        write(out, val);
        any.read_value(out.create_input_stream(), type());
    }

    public static ProfileInfo
    extract(org.omg.CORBA.Any any)
    {
        if(any.type().equivalent(type()))
            return read(any.create_input_stream());
        else

            throw new org.omg.CORBA.BAD_OPERATION(
                MinorCodes
                        .describeBadOperation(MinorCodes.MinorTypeMismatch),
                MinorCodes.MinorTypeMismatch, org.omg.CORBA.CompletionStatus.COMPLETED_NO);
    }

    private static org.omg.CORBA.TypeCode typeCode_;

    public static org.omg.CORBA.TypeCode
    type()
    {
        if(typeCode_ == null)
        {
            org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init();
            org.omg.CORBA.StructMember[] members = new org.omg.CORBA.StructMember[6];

            members[0] = new org.omg.CORBA.StructMember();
            members[0].name = "key";
            members[0].type = ObjectKeyHelper.type();

            members[1] = new org.omg.CORBA.StructMember();
            members[1].name = "major";
            members[1].type = orb.get_primitive_tc(org.omg.CORBA.TCKind.tk_octet);

            members[2] = new org.omg.CORBA.StructMember();
            members[2].name = "minor";
            members[2].type = orb.get_primitive_tc(org.omg.CORBA.TCKind.tk_octet);

            members[3] = new org.omg.CORBA.StructMember();
            members[3].name = "id";
            members[3].type = ProfileIdHelper.type();

            members[4] = new org.omg.CORBA.StructMember();
            members[4].name = "index";
            members[4].type = orb.get_primitive_tc(org.omg.CORBA.TCKind.tk_ulong);

            members[5] = new org.omg.CORBA.StructMember();
            members[5].name = "components";
            members[5].type = TaggedComponentSeqHelper.type();

            typeCode_ = orb.create_struct_tc(id(), "ProfileInfo", members);
        }

        return typeCode_;
    }

    public static String
    id()
    {
        return "IDL:orb.yoko.apache.org/OCI/ProfileInfo:1.0";
    }

    public static ProfileInfo
    read(org.omg.CORBA.portable.InputStream in)
    {
        ProfileInfo _ob_v = new ProfileInfo();
        _ob_v.key = ObjectKeyHelper.read(in);
        _ob_v.major = in.read_octet();
        _ob_v.minor = in.read_octet();
        _ob_v.id = ProfileIdHelper.read(in);
        _ob_v.index = in.read_ulong();
        _ob_v.components = TaggedComponentSeqHelper.read(in);
        return _ob_v;
    }

    public static void
    write(org.omg.CORBA.portable.OutputStream out, ProfileInfo val)
    {
        ObjectKeyHelper.write(out, val.key);
        out.write_octet(val.major);
        out.write_octet(val.minor);
        ProfileIdHelper.write(out, val.id);
        out.write_ulong(val.index);
        TaggedComponentSeqHelper.write(out, val.components);
    }
}
