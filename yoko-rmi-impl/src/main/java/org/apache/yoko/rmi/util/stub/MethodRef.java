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
package org.apache.yoko.rmi.util.stub;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

/**
 * Describes the interface of a method to be generated by the stub utility
 */
public class MethodRef {
    String name;

    Class<?> declaringClass;

    Class<?>[] parameterTypes;

    Class<?> returnType;

    Class<?>[] exceptionTypes;

    int modifiers;

    Method method;

    String signature;

    public MethodRef(Method method) {
        this.method = method;
        name = method.getName();
        setDeclaringClass(method.getDeclaringClass());
        setParameterTypes(method.getParameterTypes());
        setReturnType(method.getReturnType());
        setExceptionTypes(method.getExceptionTypes());
        setModifiers(method.getModifiers());
    }

    static String getKey(Method m) { return m.getName() + getSignature(m.getReturnType(), m.getParameterTypes()); }
    String getKey() { return getName() + getSignature(); }

    public Method getMethod() {
        return method;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDeclaringClass(Class<?> declaringClass) {
        this.declaringClass = declaringClass;
    }

    public Class<?> getDeclaringClass() {
        return declaringClass;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setReturnType(Class<?> returnType) {
        this.returnType = returnType;
    }

    public Class<?> getReturnType() {
        return returnType;
    }

    public void setExceptionTypes(Class<?>[] exceptionTypes) {
        this.exceptionTypes = exceptionTypes;
    }

    public Class<?>[] getExceptionTypes() {
        return exceptionTypes;
    }

    public void setModifiers(int modifiers) {
        this.modifiers = modifiers;
    }

    public int getModifiers() {
        return modifiers;
    }

    public String getSignature() {
        if (null == signature) signature = getSignature(returnType, parameterTypes);
        return signature;
    }

    private static String getSignature(Class<?> rType, Class<?>...pTypes) {
        return Stream.of(pTypes)
                .map(MethodRef::getSignature)
                .collect(joining("", "(", ")" + getSignature(rType)));
    }

    private static final Map<Class<?>, String> KNOWN_TYPE_MAP = Collections.unmodifiableMap(
            new HashMap<Class<?>, String>(){{
                put(int.class, "I");
                put(boolean.class, "Z");
                put(byte.class, "B");
                put(short.class, "S");
                put(long.class, "J");
                put(double.class, "D");
                put(float.class, "F");
                put(void.class, "V");
                put(char.class, "C");
            }});

    static String getSignature(Class<?> clz) {
        if (clz.isPrimitive()) return Optional.ofNullable(KNOWN_TYPE_MAP.get(clz))
                .orElseThrow(() -> new InternalError("cannot handle " + clz));
        return clz.isArray() ?
                "[" + getSignature(clz.getComponentType()) :
                "L" + clz.getName().replace('.', '/') + ";";
    }
}
