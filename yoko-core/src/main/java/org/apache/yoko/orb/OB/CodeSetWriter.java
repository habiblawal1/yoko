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
package org.apache.yoko.orb.OB;

import org.apache.yoko.io.WriteBuffer;
import org.omg.CORBA.DATA_CONVERSION;

public abstract class CodeSetWriter {
    public final static int FIRST_CHAR = 22;

    abstract void write_char(WriteBuffer writeBuffer, char v) throws DATA_CONVERSION;

    abstract void write_wchar(WriteBuffer writeBuffer, char v) throws DATA_CONVERSION;

    abstract int count_wchar(char v);

    abstract void set_flags(int flags);
}
