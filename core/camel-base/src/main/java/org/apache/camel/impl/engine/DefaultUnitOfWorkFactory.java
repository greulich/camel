/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.impl.engine;

import org.apache.camel.Exchange;
import org.apache.camel.spi.UnitOfWork;
import org.apache.camel.spi.UnitOfWorkFactory;

/**
 * Default {@link org.apache.camel.spi.UnitOfWorkFactory}
 */
public class DefaultUnitOfWorkFactory implements UnitOfWorkFactory {

    @Override
    public UnitOfWork createUnitOfWork(Exchange exchange) {
        UnitOfWork answer;
        if (exchange.getContext().isUseMDCLogging()) {
            answer = new MDCUnitOfWork(exchange, exchange.getContext().getMDCLoggingKeysPattern());
        } else {
            answer = new DefaultUnitOfWork(exchange);
        }
        return answer;
    }

}
