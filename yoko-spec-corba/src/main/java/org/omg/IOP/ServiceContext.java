/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
*  contributor license agreements.  See the NOTICE file distributed with
*  this work for additional information regarding copyright ownership.
*  The ASF licenses this file to You under the Apache License, Version 2.0
*  (the "License"); you may not use this file except in compliance with
*  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.omg.IOP;

//
// IDL:omg.org/IOP/ServiceContext:1.0
//
/***/

final public class ServiceContext implements org.omg.CORBA.portable.IDLEntity
{
    private static final String _ob_id = "IDL:omg.org/IOP/ServiceContext:1.0";

    public
    ServiceContext()
    {
    }

    public
    ServiceContext(int context_id,
                   byte[] context_data)
    {
        this.context_id = context_id;
        this.context_data = context_data;
    }

    public int context_id;
    public byte[] context_data;

    @Override
    public String toString() {
        return String.format("%08x", context_id);
    }
}
