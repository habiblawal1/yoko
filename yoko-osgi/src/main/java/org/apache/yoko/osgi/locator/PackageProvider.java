/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.yoko.osgi.locator;

import org.osgi.framework.Bundle;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PackageProvider {
    private static final Logger log = Logger.getLogger(PackageProvider.class.getName());
    final Bundle bundle;
    final Set<String> packages;

    public PackageProvider(Bundle bundle, String... pkgs) {
        this.bundle = bundle;
        this.packages = Collections.unmodifiableSet(new TreeSet<>(Arrays.asList(pkgs)));
    }

    public Class<?> loadClass(String className) {
        if (fromUnregisteredPackage(className)) {
            if (log.isLoggable(Level.FINEST)) log.finest("WILL NOT LOAD class " + className + " from unregistered package");
            return null;
        }
        try {
            return bundle.loadClass(className);
        } catch (ClassNotFoundException e) {
            if (log.isLoggable(Level.FINE)) log.fine("CAN NOT LOAD class " + className + " from registered package");
        }
        return null;
    }

    boolean fromUnregisteredPackage(String className) {
        return !!!packages.contains(packageName(className));
    }

    public static String packageName(String className) {
        return className.replaceFirst("\\.?[^.]+$", "");
    }

    public Set<String> getRegisteredPackageNames() {
        return packages;
    }

    @Override
    public String toString() {
        return "PackageProvider for bundle " + bundle.getSymbolicName() + ": " + packages;
    }
}
