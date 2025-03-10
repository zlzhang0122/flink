/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.runtime.asyncprocessing.operators.windowing.functions;

import org.apache.flink.annotation.Internal;
import org.apache.flink.api.common.state.KeyedStateStore;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.Window;
import org.apache.flink.util.OutputTag;

/**
 * Internal context wrapper for async window operator. Note: do not reusable. Each invocation on
 * userFunction need an exclusive context.
 *
 * @param <IN> The type of the input value.
 * @param <OUT> The type of the output value.
 * @param <KEY> The type of the key.
 * @param <W> The type of the window.
 */
@Internal
public class InternalProcessWindowContext<IN, OUT, KEY, W extends Window>
        extends ProcessWindowFunction<IN, OUT, KEY, W>.Context {

    W window;
    InternalAsyncWindowFunction.InternalWindowContext internalContext;

    InternalProcessWindowContext(ProcessWindowFunction<IN, OUT, KEY, W> function) {
        function.super();
    }

    @Override
    public W window() {
        return window;
    }

    @Override
    public long currentProcessingTime() {
        return internalContext.currentProcessingTime();
    }

    @Override
    public long currentWatermark() {
        return internalContext.currentWatermark();
    }

    @Override
    public KeyedStateStore windowState() {
        return internalContext.windowState();
    }

    @Override
    public KeyedStateStore globalState() {
        return internalContext.globalState();
    }

    @Override
    public <X> void output(OutputTag<X> outputTag, X value) {
        internalContext.output(outputTag, value);
    }
}
