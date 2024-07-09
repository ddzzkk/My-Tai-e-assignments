/*
 * Tai-e: A Static Analysis Framework for Java
 *
 * Copyright (C) 2022 Tian Tan <tiantan@nju.edu.cn>
 * Copyright (C) 2022 Yue Li <yueli@nju.edu.cn>
 *
 * This file is part of Tai-e.
 *
 * Tai-e is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 *
 * Tai-e is distributed in the hope that it will be useful,but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General
 * Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Tai-e. If not, see <https://www.gnu.org/licenses/>.
 */

package pascal.taie.analysis.dataflow.solver;

import pascal.taie.analysis.dataflow.analysis.DataflowAnalysis;
import pascal.taie.analysis.dataflow.fact.DataflowResult;
import pascal.taie.analysis.graph.cfg.CFG;

class IterativeSolver<Node, Fact> extends Solver<Node, Fact> {

    public IterativeSolver(DataflowAnalysis<Node, Fact> analysis) {
        super(analysis);
    }

    @Override
    protected void doSolveForward(CFG<Node> cfg, DataflowResult<Node, Fact> result) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void doSolveBackward(CFG<Node> cfg, DataflowResult<Node, Fact> result) {
        // TODO - finish me
        System.out.println("Do solve backward_1");
        // Fact boundaryFact = analysis.newBoundaryFact(cfg);
        // analysis.newInitialFact();
        Node exit = cfg.getExit();
        boolean change = true;
        do {
            change = false;
            for (Node node : cfg) {
                // for all node except exit
                if (node.equals(exit))
                    continue;
                // for all succ of node
                for (Node succ : cfg.getSuccsOf(node)) {
                    Fact succ_inFact = result.getInFact(succ);
                    Fact node_outfact = result.getOutFact(node);
                    analysis.meetInto(succ_inFact, node_outfact);
                }
                // transfer node
                Fact node_infact = result.getInFact(node);
                Fact node_outfact = result.getOutFact(node);
                if (analysis.transferNode(node, node_infact, node_outfact))
                    change = true;
            }
        } while (change);
    }
}
