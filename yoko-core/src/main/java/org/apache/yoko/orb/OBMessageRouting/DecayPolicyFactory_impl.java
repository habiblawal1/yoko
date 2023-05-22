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
package org.apache.yoko.orb.OBMessageRouting;

public class DecayPolicyFactory_impl implements
        org.omg.CORBA.portable.ValueFactory {
    public DecayPolicyFactory_impl() {
    }

    public java.io.Serializable read_value(
            org.omg.CORBA_2_3.portable.InputStream in) {
        return in.read_value(new DecayPolicy_impl());
    }
}
