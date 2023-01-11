/*
 * Copyright 2021 Hazelcast Inc.
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

package com.hazelcast.jet.sql.impl.opt.logical;

import com.hazelcast.jet.sql.impl.opt.OptUtils;
import org.apache.calcite.plan.Convention;
import org.apache.calcite.plan.RelOptRule;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.convert.ConverterRule;
import org.apache.calcite.rel.core.JoinRelType;
import org.apache.calcite.rel.logical.LogicalCorrelate;

import static com.hazelcast.jet.sql.impl.opt.Conventions.LOGICAL;

public final class CorrelateLogicalRule extends ConverterRule {

    public static final RelOptRule INSTANCE = new CorrelateLogicalRule();

    private CorrelateLogicalRule() {
        super(
                LogicalCorrelate.class, Convention.NONE, LOGICAL,
                CorrelateLogicalRule.class.getSimpleName()
        );
    }

    @Override
    public RelNode convert(RelNode rel) {
        LogicalCorrelate join = (LogicalCorrelate) rel;

        // We convert every RIGHT JOIN to LEFT JOIN to use already-implemented LEFT JOIN operators.
        if (OptUtils.isBounded(join) && join.getJoinType() == JoinRelType.RIGHT) {
            return null;
        }

        return new CorrelateLogicalRel(
                join.getCluster(),
                OptUtils.toLogicalConvention(join.getTraitSet()),
                OptUtils.toLogicalInput(join.getLeft()),
                join.getCorrelationId(),
                OptUtils.toLogicalInput(join.getRight()),
                join.getJoinType()
        );
    }
}
