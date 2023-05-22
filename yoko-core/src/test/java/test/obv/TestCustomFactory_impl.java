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
package test.obv;

public class TestCustomFactory_impl implements TestCustomValueFactory {
    public java.io.Serializable read_value(
            org.omg.CORBA_2_3.portable.InputStream in) {
        return in.read_value(new TestCustom_impl());
    }

    public TestCustom create(short s, int l, String str, double d) {
        TestCustom result = new TestCustom_impl();
        result.shortVal = s;
        result.longVal = l;
        result.stringVal = str;
        result.doubleVal = d;
        return result;
    }

    public static TestCustomValueFactory install(org.omg.CORBA.ORB orb) {
        org.omg.CORBA_2_3.ORB orb_2_3 = (org.omg.CORBA_2_3.ORB) orb;
        TestCustomValueFactory result = new TestCustomFactory_impl();
        orb_2_3.register_value_factory(TestCustomHelper.id(), result);
        return result;
    }
}
