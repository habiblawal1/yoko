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
package test.tnaming;

/**
* TestHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from Test.idl
* Wednesday, July 5, 2006 12:46:41 PM EDT
*/


//
public final class TestHolder implements org.omg.CORBA.portable.Streamable
{
  public Test value = null;

  public TestHolder ()
  {
  }

  public TestHolder (Test initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = TestHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    TestHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return TestHelper.type ();
  }

}
