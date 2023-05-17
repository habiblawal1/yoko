/*==============================================================================
 * Copyright 2015 IBM Corporation and others.
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
package ORBTest;

import static org.junit.Assert.assertTrue;

import org.omg.CORBA.*;

public class TestObjectWChar extends test.common.TestBase implements TestObject {
    private ORB m_orb;

    ORBTest.Intf m_test_intf;

    public TestObjectWChar(ORB orb, ORBTest.Intf test_intf) {
        m_orb = orb;
        m_test_intf = test_intf;
    }

    public boolean is_supported(org.omg.CORBA.Object obj) {
        boolean is_supported = false;

        if (obj != null) {
            try {
                ORBTest_WChar.Intf ti = ORBTest_WChar.IntfHelper.narrow(obj);
                is_supported = true;
            } catch (BAD_PARAM e) {
                is_supported = false;
            }
        }

        return is_supported;
    }

    public void test_SII(org.omg.CORBA.Object obj) {
        ORBTest_WChar.Intf ti = ORBTest_WChar.IntfHelper.narrow(obj);

        {
            char ret;
            ti.attrWChar('a');
            ret = ti.attrWChar();
            assertTrue(ret == 'a');
            ti.attrWChar((char) 224);
            ret = ti.attrWChar();
            assertTrue(ret == (char) 224);
            ti.attrWChar((char) 0x20ac);
            ret = ti.attrWChar();
            assertTrue(ret == (char) 0x20ac);

            CharHolder inOut = new CharHolder((char) 1);
            CharHolder out = new CharHolder();
            ret = ti.opWChar('a', inOut, out);
            assertTrue(ret == 'b');
            assertTrue(inOut.value == 'b');
            assertTrue(out.value == 'b');
        }

        {
            String ret;

            ti.attrWString("");
            ret = ti.attrWString();
            assertTrue(ret.equals(""));

            ti.attrWString("Hello");
            ret = ti.attrWString();
            assertTrue(ret.equals("Hello"));

            StringHolder inOut = new StringHolder("world!");
            StringHolder out = new StringHolder();
            ret = ti.opWString("Hello, ", inOut, out);
            assertTrue(ret.equals("Hello, world!"));
            assertTrue(out.value.equals("Hello, world!"));
        }

        {
            CharHolder inOut = new CharHolder((char) 1);
            CharHolder out = new CharHolder();

            try {
                ti.opWCharEx('a', inOut, out);
                assertTrue(false);
            } catch (ORBTest_WChar.ExWChar ex) {
                assertTrue(ex.value == 'b');
            }
        }

        {
            StringHolder inOut = new StringHolder("world!");
            StringHolder out = new StringHolder();

            try {
                ti.opWStringEx("Hello, ", inOut, out);
                assertTrue(false);
            } catch (ORBTest_WChar.ExWString ex) {
                assertTrue(ex.value.equals("Hello, world!"));
            }
        }

    }

    public void test_DII(org.omg.CORBA.Object obj) {
        // REVISIT
    }
}
