/*
 * Copyright 2023 IBM Corporation and others.
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
package testify.bus.key;

import java.util.stream.Stream;

/**
 * A specialised type spec that handles {@link Enum} objects.
 */
public interface EnumSpec<E extends Enum<E>> extends TypeSpec<E> {
    @Override
    default String stringify(E e) {
        return e.getDeclaringClass().getName() + "#" + e.name();
    }

    @Override
    default E unstringify(String s) {
        String[] parts = s.split("#");
        Class<E> declaringClass = (Class<E>)findClass(parts[0]);
        String memberName = parts[1];
        return Stream.of(declaringClass.getEnumConstants())
                .filter(mem -> mem.name().equals(memberName))
                .findFirst()
                .orElseThrow(Error::new);
    }

    default Class<?> findClass(String type) {
        try {
            return  Class.forName(type);
        } catch (ClassNotFoundException e) {
            throw (Error)new NoClassDefFoundError(e.getMessage()).initCause(e);
        }
    }
}
