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
package test.obv;

//
// IDL:TestValueSub:1.0
//
/***/

public abstract class TestValueSub extends TestValue implements TestAbsValue2
{
    //
    // IDL:TestValueSub/name:1.0
    //
    /***/

    public String name;

    private static String[] _OB_truncatableIds_ =
    {
        TestValueSubHelper.id()
    };

    public String[]
    _truncatable_ids()
    {
        return _OB_truncatableIds_;
    }

    public void
    _read(org.omg.CORBA.portable.InputStream in)
    {
        super._read(in);
        name = in.read_string();
    }

    public void
    _write(org.omg.CORBA.portable.OutputStream out)
    {
        super._write(out);
        out.write_string(name);
    }

    public org.omg.CORBA.TypeCode
    _type()
    {
        return TestValueSubHelper.type();
    }
}
