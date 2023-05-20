/*
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
package org.omg.CORBA;

//
// IDL:omg.org/CORBA/AttributeDescription:1.0
//
final public class AttributeDescriptionHelper
{
    public static void
    insert(org.omg.CORBA.Any any, AttributeDescription val)
    {
        org.omg.CORBA.portable.OutputStream out = any.create_output_stream();
        write(out, val);
        any.read_value(out.create_input_stream(), type());
    }

    public static AttributeDescription
    extract(org.omg.CORBA.Any any)
    {
        if(any.type().equivalent(type()))
            return read(any.create_input_stream());
        else
            throw new org.omg.CORBA.BAD_OPERATION();
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
            members[0].name = "name";
            members[0].type = IdentifierHelper.type();

            members[1] = new org.omg.CORBA.StructMember();
            members[1].name = "id";
            members[1].type = RepositoryIdHelper.type();

            members[2] = new org.omg.CORBA.StructMember();
            members[2].name = "defined_in";
            members[2].type = RepositoryIdHelper.type();

            members[3] = new org.omg.CORBA.StructMember();
            members[3].name = "version";
            members[3].type = VersionSpecHelper.type();

            members[4] = new org.omg.CORBA.StructMember();
            members[4].name = "type";
            members[4].type = orb.get_primitive_tc(org.omg.CORBA.TCKind.tk_TypeCode);

            members[5] = new org.omg.CORBA.StructMember();
            members[5].name = "mode";
            members[5].type = AttributeModeHelper.type();

            typeCode_ = orb.create_struct_tc(id(), "AttributeDescription", members);
        }

        return typeCode_;
    }

    public static String
    id()
    {
        return "IDL:omg.org/CORBA/AttributeDescription:1.0";
    }

    public static AttributeDescription
    read(org.omg.CORBA.portable.InputStream in)
    {
        AttributeDescription _ob_v = new AttributeDescription();
        _ob_v.name = IdentifierHelper.read(in);
        _ob_v.id = RepositoryIdHelper.read(in);
        _ob_v.defined_in = RepositoryIdHelper.read(in);
        _ob_v.version = VersionSpecHelper.read(in);
        _ob_v.type = in.read_TypeCode();
        _ob_v.mode = AttributeModeHelper.read(in);
        return _ob_v;
    }

    public static void
    write(org.omg.CORBA.portable.OutputStream out, AttributeDescription val)
    {
        IdentifierHelper.write(out, val.name);
        RepositoryIdHelper.write(out, val.id);
        RepositoryIdHelper.write(out, val.defined_in);
        VersionSpecHelper.write(out, val.version);
        out.write_TypeCode(val.type);
        AttributeModeHelper.write(out, val.mode);
    }
}
