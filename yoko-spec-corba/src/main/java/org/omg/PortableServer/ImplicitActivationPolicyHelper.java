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
package org.omg.PortableServer;

//
// IDL:omg.org/PortableServer/ImplicitActivationPolicy:1.0
//
public class ImplicitActivationPolicyHelper
{
    public static void
    insert(org.omg.CORBA.Any any, ImplicitActivationPolicy val)
    {
        any.insert_Object(val, type());
    }

    public static ImplicitActivationPolicy
    extract(org.omg.CORBA.Any any)
    {
        if(any.type().equivalent(type()))
            return narrow(any.extract_Object());

        throw new org.omg.CORBA.BAD_OPERATION();
    }

    private static org.omg.CORBA.TypeCode typeCode_;

    public static org.omg.CORBA.TypeCode
    type()
    {
        if(typeCode_ == null)
        {
            org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init();
            typeCode_ = ((org.omg.CORBA_2_4.ORB)orb).create_local_interface_tc(id(), "ImplicitActivationPolicy");
        }

        return typeCode_;
    }

    public static String
    id()
    {
        return "IDL:omg.org/PortableServer/ImplicitActivationPolicy:1.0";
    }

    public static ImplicitActivationPolicy
    read(org.omg.CORBA.portable.InputStream in)
    {
        throw new org.omg.CORBA.MARSHAL();
    }

    public static void
    write(org.omg.CORBA.portable.OutputStream out, ImplicitActivationPolicy val)
    {
        throw new org.omg.CORBA.MARSHAL();
    }

    public static ImplicitActivationPolicy
    narrow(org.omg.CORBA.Object val)
    {
        try
        {
            return (ImplicitActivationPolicy)val;
        }
        catch(ClassCastException ex)
        {
        }

        throw new org.omg.CORBA.BAD_PARAM();
    }
}
