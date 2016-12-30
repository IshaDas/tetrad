///////////////////////////////////////////////////////////////////////////////
// For information as to what this class does, see the Javadoc, below.       //
// Copyright (C) 1998, 1999, 2000, 2001, 2002, 2003, 2004, 2005, 2006,       //
// 2007, 2008, 2009, 2010, 2014, 2015 by Peter Spirtes, Richard Scheines, Joseph   //
// Ramsey, and Clark Glymour.                                                //
//                                                                           //
// This program is free software; you can redistribute it and/or modify      //
// it under the terms of the GNU General Public License as published by      //
// the Free Software Foundation; either version 2 of the License, or         //
// (at your option) any later version.                                       //
//                                                                           //
// This program is distributed in the hope that it will be useful,           //
// but WITHOUT ANY WARRANTY; without even the implied warranty of            //
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the             //
// GNU General Public License for more details.                              //
//                                                                           //
// You should have received a copy of the GNU General Public License         //
// along with this program; if not, write to the Free Software               //
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA //
///////////////////////////////////////////////////////////////////////////////

package edu.cmu.tetrad.test;

import edu.cmu.tetrad.algcomparison.Comparison;
import edu.cmu.tetrad.algcomparison.algorithm.Algorithms;
import edu.cmu.tetrad.algcomparison.algorithm.oracle.pattern.*;
import edu.cmu.tetrad.algcomparison.graph.RandomForward;
import edu.cmu.tetrad.algcomparison.independence.ConditionalGaussianLRT;
import edu.cmu.tetrad.algcomparison.independence.FisherZ;
import edu.cmu.tetrad.algcomparison.independence.SemBicTest;
import edu.cmu.tetrad.algcomparison.independence.SemBicTest2;
import edu.cmu.tetrad.algcomparison.score.ConditionalGaussianBicScore;
import edu.cmu.tetrad.algcomparison.score.SemBicScore;
import edu.cmu.tetrad.algcomparison.score.SemBicScore2;
import edu.cmu.tetrad.algcomparison.score.SemBicScore3;
import edu.cmu.tetrad.algcomparison.simulation.*;
import edu.cmu.tetrad.algcomparison.statistic.*;
import edu.cmu.tetrad.util.Parameters;

/**
 * An example script to simulate data and run a comparison analysis on it.
 *
 * @author jdramsey
 */
public class TestConditionalGaussianSimulation {

    public void test1() {
        Parameters parameters = new Parameters();

        parameters.set("numRuns", 1);

        parameters.set("numMeasures", 1000);
        parameters.set("avgDegree", 2, 4);

        parameters.set("sampleSize", 1000);
        parameters.set("depth", -1);

        parameters.set("penaltyDiscount", 1);
        parameters.set("minScoreDifference", 0);//2 * Math.log(1000));
        parameters.set("maxDegree", 1000);
        parameters.set("maxIndegree", 1000);
        parameters.set("maxOutdegree", 1000);

        parameters.set("useMaxPOrientationHeuristic", false);
        parameters.set("maxPOrientationMaxPathLength", 3);

        parameters.set("symmetricFirstStep", true);

        parameters.set("minCategories", 2);
        parameters.set("maxCategories", 5);
        parameters.set("percentDiscrete", 0);

        parameters.set("numCategoriesToDiscretize", 8);

        parameters.set("intervalBetweenRecordings", 20);
        parameters.set("intervalBetweenShocks", 20);

        parameters.set("varLow", 1.);
        parameters.set("varHigh", 2.);
        parameters.set("coefLow", .1);
        parameters.set("coefHigh", 1.1);
        parameters.set("coefSymmetric", false);
        parameters.set("meanLow", 0);
        parameters.set("meanHigh", 0);

//        parameters.set("scaleFreeAlpha", .9);
//        parameters.set("scaleFreeBeta", .05);
//        parameters.set("scaleFreeDeltaIn", 3);
//        parameters.set("scaleFreeDeltaOut", .1);

        Statistics statistics = new Statistics();

        statistics.add(new ParameterColumn("avgDegree"));
        statistics.add(new ParameterColumn("penaltyDiscount"));
        statistics.add(new ParameterColumn("minScoreDifference"));
        statistics.add(new NumberOfEdgesTrue());
        statistics.add(new AdjacencyPrecision());
        statistics.add(new AdjacencyRecall());
        statistics.add(new ArrowheadPrecision());
        statistics.add(new ArrowheadRecall());
        statistics.add(new ElapsedTime());

        statistics.setWeight("AP", 1.0);
        statistics.setWeight("AR", 0.2);
        statistics.setWeight("AHP", 1.0);
        statistics.setWeight("AHR", 0.2);

        Simulations simulations = new Simulations();

//        simulations.add(new ConditionalGaussianSimulation2(new RandomForward()));
        simulations.add(new LinearFisherModel(new RandomForward()));

        Algorithms algorithms = new Algorithms();

        algorithms.add(new Fges(new SemBicScore()));
        algorithms.add(new Fges(new SemBicScore3()));
//        algorithms.add(new Fges(new SemBicScore2()));
//        algorithms.add(new PcMax(new SemBicTest()));
//        algorithms.add(new PcMax(new SemBicTest2()));
//        algorithms.add(new Fgs(new ConditionalGaussianBicScore()));
//        algorithms.add(new PcMax(new ConditionalGaussianLRT()));

        Comparison comparison = new Comparison();

        comparison.setShowAlgorithmIndices(true);
        comparison.setShowSimulationIndices(false);
        comparison.setSortByUtility(false);
        comparison.setShowUtilities(false);
        comparison.setParallelized(false);
        comparison.setSaveGraphs(true);

        comparison.setTabDelimitedTables(false);

        comparison.compareFromSimulations("comparison", simulations, algorithms, statistics, parameters);
//        comparison.saveToFiles("comparison", new LinearFisherModel(new RandomForward()), parameters);
    }

    public static void main(String... args) {
        new TestConditionalGaussianSimulation().test1();
    }
}




