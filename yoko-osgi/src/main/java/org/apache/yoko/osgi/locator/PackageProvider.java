/*
 * Copyright 2021 IBM Corporation and others.
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
package org.apache.yoko.osgi.locator;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PackageProvider {
    private static final Logger log = Logger.getLogger(PackageProvider.class.getName());
    private final LocalFactory localFactory;
    private final Set<String> packages;

    public PackageProvider(LocalFactory localFactory, String... pkgs) {
        this.localFactory = localFactory;
        this.packages = Collections.unmodifiableSet(new TreeSet<>(Arrays.asList(pkgs)));
    }

    public <T> Class<T> loadClass(String className) {
        if (fromUnregisteredPackage(className)) {
            if (log.isLoggable(Level.FINEST)) log.finest("WILL NOT LOAD class " + className + " from unregistered package");
            return null;
        }
        try {
            return generify(localFactory.forName(className));
        } catch (ClassNotFoundException e) {
            if (log.isLoggable(Level.FINE)) log.fine("CAN NOT LOAD class " + className + " from registered package");
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private static <T> Class<T> generify(Class<?> c) { return (Class<T>)c; }

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
        return "PackageProvider for " + packages;
    }
}
