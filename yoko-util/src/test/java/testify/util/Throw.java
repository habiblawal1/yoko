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
package testify.util;

public enum Throw {
    ;

    /**
     * Throw absolutely any throwable as if it were a RuntimeException
     * @param t the throwable to be rethrown
     * @throws <code>t</code>
     * @return declares a return type so that callers can use <code>throw Throw.andThrowAgain(t)</code>
     */
    public static RuntimeException andThrowAgain(Throwable t) throws RuntimeException {
        throw Throw.<RuntimeException>useTypeErasureMadnessToThrowAnyCheckedException(t);
    }

    @SuppressWarnings("unchecked")
    private static <T extends Throwable> T useTypeErasureMadnessToThrowAnyCheckedException(Throwable t) throws T {
        throw (T)t;
    }
}
