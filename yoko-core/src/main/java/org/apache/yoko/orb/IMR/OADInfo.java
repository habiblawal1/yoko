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
package org.apache.yoko.orb.IMR;

//
// IDL:orb.yoko.apache.org/IMR/OADInfo:1.0
//
/** Information on an OAD. */

final public class OADInfo implements org.omg.CORBA.portable.IDLEntity
{
    private static final String _ob_id = "IDL:orb.yoko.apache.org/IMR/OADInfo:1.0";

    public
    OADInfo()
    {
    }

    public
    OADInfo(String host,
            boolean up)
    {
        this.host = host;
        this.up = up;
    }

    public String host;
    public boolean up;
}
