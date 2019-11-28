/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.drill.exec.physical.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.apache.drill.exec.physical.base.PhysicalOperator;
import org.apache.drill.exec.metastore.analyze.MetadataAggregateContext;

import java.util.Collections;

@JsonTypeName("metadataAggregate")
public class MetadataAggPOP extends StreamingAggregate {
  private final MetadataAggregateContext context;

  @JsonCreator
  public MetadataAggPOP(@JsonProperty("child") PhysicalOperator child,
      @JsonProperty("context") MetadataAggregateContext context) {
    super(child, context.groupByExpressions(), Collections.emptyList());
    this.context = context;
  }

  @Override
  protected PhysicalOperator getNewWithChild(PhysicalOperator child) {
    return new MetadataAggPOP(child, context);
  }

  @JsonProperty
  public MetadataAggregateContext getContext() {
    return context;
  }
}