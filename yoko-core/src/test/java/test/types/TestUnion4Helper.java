/*
 * Copyright 2010 IBM Corporation and others.
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
package test.types;

//
// IDL:TestUnion4:1.0
//
final public class TestUnion4Helper
{
    public static void
    insert(org.omg.CORBA.Any any, TestUnion4 val)
    {
        org.omg.CORBA.portable.OutputStream out = any.create_output_stream();
        write(out, val);
        any.read_value(out.create_input_stream(), type());
    }

    public static TestUnion4
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
            org.omg.CORBA.UnionMember[] members = new org.omg.CORBA.UnionMember[2];

            members[0] = new org.omg.CORBA.UnionMember();
            members[0].name = "seq";
            org.omg.CORBA.TypeCode content0;
            content0 = orb.create_recursive_tc(id());
            members[0].type = orb.create_sequence_tc(0, content0);
            members[0].label = orb.create_any();
            members[0].label.insert_short((short)(0L));

            members[1] = new org.omg.CORBA.UnionMember();
            members[1].name = "c";
            members[1].type = orb.get_primitive_tc(org.omg.CORBA.TCKind.tk_char);
            members[1].label = orb.create_any();
            members[1].label.insert_short((short)(1L));

            org.omg.CORBA.TypeCode discType = orb.get_primitive_tc(org.omg.CORBA.TCKind.tk_short);
            typeCode_ = orb.create_union_tc(id(), "TestUnion4", discType, members);
        }

        return typeCode_;
    }

    public static String
    id()
    {
        return "IDL:TestUnion4:1.0";
    }

    public static TestUnion4
    read(org.omg.CORBA.portable.InputStream in)
    {
        TestUnion4 _ob_v = new TestUnion4();
        short _ob_d;
        _ob_d = in.read_short();

        switch(_ob_d)
        {
        case (short)0:
        {
            TestUnion4[] _ob_m;
            int len0 = in.read_ulong();
            _ob_m = new TestUnion4[len0];
            for(int i0 = 0; i0 < len0; i0++)
                _ob_m[i0] = TestUnion4Helper.read(in);
            _ob_v.seq(_ob_m);
            break;
        }

        case (short)1:
        {
            char _ob_m;
            _ob_m = in.read_char();
            _ob_v.c(_ob_m);
            break;
        }

        default:
            _ob_v.__default(_ob_d);
            break;
        }

        return _ob_v;
    }

    public static void
    write(org.omg.CORBA.portable.OutputStream out, TestUnion4 val)
    {
        short _ob_d = val.discriminator();
        out.write_short(_ob_d);

        switch(_ob_d)
        {
        case (short)0:
        {
            TestUnion4[] _ob_m = val.seq();
            int len0 = _ob_m.length;
            out.write_ulong(len0);
            for(int i0 = 0; i0 < len0; i0++)
                TestUnion4Helper.write(out, _ob_m[i0]);
            break;
        }

        case (short)1:
        {
            char _ob_m = val.c();
            out.write_char(_ob_m);
            break;
        }

        default:
            break;
        }
    }
}
