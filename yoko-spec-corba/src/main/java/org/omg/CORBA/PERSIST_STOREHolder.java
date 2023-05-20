/*
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
package org.omg.CORBA;

final public class PERSIST_STOREHolder implements
        org.omg.CORBA.portable.Streamable {
    public PERSIST_STORE value;

    public PERSIST_STOREHolder() {
    }

    public PERSIST_STOREHolder(PERSIST_STORE initial) {
        value = initial;
    }

    public void _read(org.omg.CORBA.portable.InputStream in) {
        value = PERSIST_STOREHelper.read(in);
    }

    public void _write(org.omg.CORBA.portable.OutputStream out) {
        PERSIST_STOREHelper.write(out, value);
    }

    public TypeCode _type() {
        return PERSIST_STOREHelper.type();
    }
}
