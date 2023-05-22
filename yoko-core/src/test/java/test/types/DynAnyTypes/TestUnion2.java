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
package test.types.DynAnyTypes;

//
// IDL:test/types/DynAnyTypes/TestUnion2:1.0
//
/***/

final public class TestUnion2 implements org.omg.CORBA.portable.IDLEntity
{
    java.lang.Object _ob_v_;
    boolean _ob_i_;
    int _ob_d_;

    static boolean
    _OB_check(int d0, int d1)
    {
        int d[] = new int[2];
        d[0] = d0;
        d[1] = d1;

        for(int i = 0; i < 2; i++)
        {
            switch(d[i])
            {
            case 1:
                break;

            default:
                d[i] = 0;
                break;
            }
        }

        return d[0] == d[1];
    }

    public
    TestUnion2()
    {
        _ob_i_ = false;
    }

    public boolean
    discriminator()
    {
        if(!_ob_i_)
            throw new org.omg.CORBA.BAD_OPERATION();

        return _ob_d_ == 0 ? false : true;
    }

    public int
    a()
    {
        if(!_ob_i_)
            throw new org.omg.CORBA.BAD_OPERATION();

        if(!_OB_check(_ob_d_, 1))
            throw new org.omg.CORBA.BAD_OPERATION();

        return ((org.omg.CORBA.IntHolder)_ob_v_).value;
    }

    public void
    a(int val)
    {
        _ob_i_ = true;
        _ob_d_ = 1;
        _ob_v_ = new org.omg.CORBA.IntHolder(val);
    }

    public void
    __default()
    {
        _ob_i_ = true;
        _ob_d_ = 0;
        _ob_v_ = null;
    }

    public void
    __default(boolean d)
    {
        if(!_OB_check(d ? 1 : 0, 0))
            throw new org.omg.CORBA.BAD_PARAM();

        _ob_i_ = true;
        _ob_d_ = d ? 1 : 0;
        _ob_v_ = null;
    }
}
