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
package test.types.DynAnyTypes;

//
// IDL:test/types/DynAnyTypes/TestValue1:1.0
//
final public class TestValue1Holder implements org.omg.CORBA.portable.Streamable
{
    public TestValue1 value;

    public
    TestValue1Holder()
    {
    }

    public
    TestValue1Holder(TestValue1 initial)
    {
        value = initial;
    }

    public void
    _read(org.omg.CORBA.portable.InputStream in)
    {
        value = TestValue1Helper.read(in);
    }

    public void
    _write(org.omg.CORBA.portable.OutputStream out)
    {
        TestValue1Helper.write(out, value);
    }

    public org.omg.CORBA.TypeCode
    _type()
    {
        return TestValue1Helper.type();
    }
}
