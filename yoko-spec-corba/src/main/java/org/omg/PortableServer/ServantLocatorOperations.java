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
package org.omg.PortableServer;

//
// IDL:omg.org/PortableServer/ServantLocator:2.3
//
/***/

public interface ServantLocatorOperations extends ServantManagerOperations
{
    //
    // IDL:omg.org/PortableServer/ServantLocator/preinvoke:1.0
    //
    /***/

    Servant
    preinvoke(byte[] oid,
              POA adapter,
              String operation,
              org.omg.PortableServer.ServantLocatorPackage.CookieHolder the_cookie)
        throws ForwardRequest;

    //
    // IDL:omg.org/PortableServer/ServantLocator/postinvoke:1.0
    //
    /***/

    void
    postinvoke(byte[] oid,
               POA adapter,
               String operation,
               java.lang.Object the_cookie,
               Servant the_servant);
}
