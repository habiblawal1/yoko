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
package org.omg.GIOP;

//
// IDL:omg.org/GIOP/MessageHeader_1_1:1.0
//
/***/

final public class MessageHeader_1_1 implements org.omg.CORBA.portable.IDLEntity
{
    private static final String _ob_id = "IDL:omg.org/GIOP/MessageHeader_1_1:1.0";

    public
    MessageHeader_1_1()
    {
    }

    public
    MessageHeader_1_1(char[] magic,
                      Version GIOP_version,
                      byte flags,
                      byte message_type,
                      int message_size)
    {
        this.magic = magic;
        this.GIOP_version = GIOP_version;
        this.flags = flags;
        this.message_type = message_type;
        this.message_size = message_size;
    }

    public char[] magic;
    public Version GIOP_version;
    public byte flags;
    public byte message_type;
    public int message_size;
}
