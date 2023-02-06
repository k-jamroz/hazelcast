/*
 * Copyright 2023 Hazelcast Inc.
 *
 * Licensed under the Hazelcast Community License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://hazelcast.com/hazelcast-community-license
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.jet.sql.impl.opt.physical;

import com.google.common.collect.ImmutableList;
import com.hazelcast.jet.sql.impl.opt.logical.GetDdlRel;
import com.hazelcast.sql.impl.QueryParameterMetadata;
import com.hazelcast.sql.impl.plan.node.PlanNodeSchema;
import com.hazelcast.sql.impl.type.QueryDataType;
import org.apache.calcite.plan.RelOptCluster;
import org.apache.calcite.plan.RelTraitSet;

import java.util.List;

public class GetDdlPhysicalRel extends GetDdlRel implements PhysicalRel {
    GetDdlPhysicalRel(
            RelOptCluster cluster,
            RelTraitSet traits,
            List<String> operands) {
        super(cluster, traits, operands);
    }

    @Override
    public PlanNodeSchema schema(QueryParameterMetadata parameterMetadata) {
        return new PlanNodeSchema(ImmutableList.of(QueryDataType.VARCHAR));
    }

    @Override
    public <V> V accept(CreateDagVisitor<V> visitor) {
        return null;
    }
}
