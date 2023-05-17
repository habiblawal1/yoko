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
package org.apache.yoko.orb.CORBA;

final public class ExceptionList extends org.omg.CORBA.ExceptionList {
    java.util.Vector typeCodeVec_ = new java.util.Vector();

    // ------------------------------------------------------------------
    // Standard IDL to Java Mapping
    // ------------------------------------------------------------------

    public int count() {
        return typeCodeVec_.size();
    }

    public void add(org.omg.CORBA.TypeCode exc) {
        typeCodeVec_.addElement(exc);
    }

    public org.omg.CORBA.TypeCode item(int index) throws org.omg.CORBA.Bounds {
        try {
            return (org.omg.CORBA.TypeCode) typeCodeVec_.elementAt(index);
        } catch (ArrayIndexOutOfBoundsException ex) {
            throw new org.omg.CORBA.Bounds();
        }
    }

    public void remove(int index) throws org.omg.CORBA.Bounds {
        try {
            typeCodeVec_.removeElementAt(index);
        } catch (ArrayIndexOutOfBoundsException ex) {
            throw new org.omg.CORBA.Bounds();
        }
    }

    // ------------------------------------------------------------------
    // Yoko internal functions
    // Application programs must not use these functions directly
    // ------------------------------------------------------------------

    public ExceptionList() {
    }
}
