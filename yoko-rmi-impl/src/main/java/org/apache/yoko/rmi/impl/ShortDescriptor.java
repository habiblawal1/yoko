/*==============================================================================
 * Copyright 2021 IBM Corporation and others.
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
package org.apache.yoko.rmi.impl;

final class ShortDescriptor extends SimpleDescriptor {
    ShortDescriptor(TypeRepository repository) {
        super(Short.TYPE, repository, "short", org.omg.CORBA.TCKind.tk_short);
    }

    public Object read(org.omg.CORBA.portable.InputStream in) {
        return Short.valueOf(in.read_short());
    }

    public void write(org.omg.CORBA.portable.OutputStream out, Object val) {
        out.write_short(((Short) val).shortValue());
    }

}
