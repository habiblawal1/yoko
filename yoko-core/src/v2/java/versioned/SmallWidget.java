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
package versioned;

import acme.Widget;

import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@SuppressWarnings("unused")
public class SmallWidget extends NonSerializableSuper implements Widget {
    private static final long serialVersionUID = 1L;
    private String a = "a_v2";
    private transient String b = "b_v2";
    private String d = "d_v2";

    @Override
    public Widget validateAndReplace() {
        validateInV2Context();
        return new SmallWidget();
    }

    private void validateInV2Context() {
        // check the v1 was transmitted correctly
        assertThat(a, endsWith("v1"));
        assertThat(b, nullValue());
        assertThat(d, nullValue());
    }

    @Override
    public String toString() {
        return "WidgetImpl{" +
                "a='" + a + '\'' +
                ", b='" + b + '\'' +
                ", d='" + d + '\'' +
                '}';
    }
}
