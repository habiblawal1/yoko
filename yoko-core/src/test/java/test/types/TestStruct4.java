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
// IDL:TestStruct4:1.0
//
/***/

final public class TestStruct4 implements org.omg.CORBA.portable.IDLEntity
{
    private static final String _ob_id = "IDL:TestStruct4:1.0";

    public
    TestStruct4()
    {
    }

    public
    TestStruct4(TestStruct3 a,
                TestStruct3[] b)
    {
        this.a = a;
        this.b = b;
    }

    public TestStruct3 a;
    public TestStruct3[] b;
}
