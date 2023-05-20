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
// IDL:TestUnion1:1.0
//
/***/

final public class TestUnion1 implements org.omg.CORBA.portable.IDLEntity
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
            case -1:
                break;

            case -2:
                break;

            case -3:
                break;

            case 0:
            case 1:
            case 2:
            case 3:
            case -4:
            case -5:
                d[i] = 0;
                break;

            default:
                d[i] = 4;
                break;
            }
        }

        return d[0] == d[1];
    }

    public
    TestUnion1()
    {
        _ob_i_ = false;
    }

    public int
    discriminator()
    {
        if(!_ob_i_)
            throw new org.omg.CORBA.BAD_OPERATION();

        return _ob_d_;
    }

    public int
    l()
    {
        if(!_ob_i_)
            throw new org.omg.CORBA.BAD_OPERATION();

        if(!_OB_check(_ob_d_, -1))
            throw new org.omg.CORBA.BAD_OPERATION();

        return ((org.omg.CORBA.IntHolder)_ob_v_).value;
    }

    public void
    l(int val)
    {
        _ob_i_ = true;
        _ob_d_ = -1;
        _ob_v_ = new org.omg.CORBA.IntHolder(val);
    }

    public double[][][]
    a()
    {
        if(!_ob_i_)
            throw new org.omg.CORBA.BAD_OPERATION();

        if(!_OB_check(_ob_d_, -2))
            throw new org.omg.CORBA.BAD_OPERATION();

        return (double[][][])_ob_v_;
    }

    public void
    a(double[][][] val)
    {
        _ob_i_ = true;
        _ob_d_ = -2;
        _ob_v_ = val;
    }

    public String
    s()
    {
        if(!_ob_i_)
            throw new org.omg.CORBA.BAD_OPERATION();

        if(!_OB_check(_ob_d_, -3))
            throw new org.omg.CORBA.BAD_OPERATION();

        return (String)_ob_v_;
    }

    public void
    s(String val)
    {
        _ob_i_ = true;
        _ob_d_ = -3;
        _ob_v_ = val;
    }

    public TestStruct2
    str()
    {
        if(!_ob_i_)
            throw new org.omg.CORBA.BAD_OPERATION();

        if(!_OB_check(_ob_d_, 0))
            throw new org.omg.CORBA.BAD_OPERATION();

        return (TestStruct2)_ob_v_;
    }

    public void
    str(TestStruct2 val)
    {
        _ob_i_ = true;
        _ob_d_ = 0;
        _ob_v_ = val;
    }

    public void
    str(int d, TestStruct2 val)
    {
        if(!_OB_check(d, 0))
            throw new org.omg.CORBA.BAD_PARAM();

        _ob_i_ = true;
        _ob_d_ = d;
        _ob_v_ = val;
    }

    public org.omg.CORBA.TypeCode
    tc()
    {
        if(!_ob_i_)
            throw new org.omg.CORBA.BAD_OPERATION();

        if(!_OB_check(_ob_d_, 4))
            throw new org.omg.CORBA.BAD_OPERATION();

        return (org.omg.CORBA.TypeCode)_ob_v_;
    }

    public void
    tc(org.omg.CORBA.TypeCode val)
    {
        _ob_i_ = true;
        _ob_d_ = 4;
        _ob_v_ = val;
    }

    public void
    tc(int d, org.omg.CORBA.TypeCode val)
    {
        if(!_OB_check(d, 4))
            throw new org.omg.CORBA.BAD_PARAM();

        _ob_i_ = true;
        _ob_d_ = d;
        _ob_v_ = val;
    }
}
