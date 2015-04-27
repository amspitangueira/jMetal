package org.uma.jmetal.util.extremevalues.impl;

import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.extremevalues.ExtremeValuesFinder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ajnebro on 23/4/15.
 */
public class SolutionListExtremeValues implements ExtremeValuesFinder <List<Solution>, List<Double>> {

  @Override public List<Double> findLowestValues(List<Solution> solutionList) {
    List<Double> minimumValue ;
    if ((solutionList == null) || (solutionList.size() == 0)) {
      minimumValue = Collections.EMPTY_LIST ;
    } else {
      int numberOfObjectives = solutionList.get(0).getNumberOfObjectives() ;
      minimumValue = new ArrayList<>(numberOfObjectives) ;

      for (int i = 0; i < numberOfObjectives; i++) {
        minimumValue.set(i, Double.POSITIVE_INFINITY) ;
      }

      for (Solution solution : solutionList) {
        for (int j = 0; j < solution.getNumberOfObjectives(); j++) {
          if (solution.getObjective(j) < minimumValue.get(j)) {
            minimumValue.set(j, solution.getObjective(j));
          }
        }
      }
    }

    return minimumValue;
  }

  @Override public List<Double> findHighestValues(List<Solution> solutionList) {
    List<Double> maximumValue ;
    if ((solutionList == null) || (solutionList.size() == 0)) {
      maximumValue = Collections.EMPTY_LIST ;
    } else {
      int numberOfObjectives = solutionList.get(0).getNumberOfObjectives() ;
      maximumValue = new ArrayList<>(numberOfObjectives) ;

      for (int i = 0; i < numberOfObjectives; i++) {
        maximumValue.set(i, Double.NEGATIVE_INFINITY) ;
      }

      for (Solution solution : solutionList) {
        for (int j = 0; j < solution.getNumberOfObjectives(); j++) {
          if (solution.getObjective(j) > maximumValue.get(j)) {
            maximumValue.set(j, solution.getObjective(j));
          }
        }
      }
    }

    return maximumValue;
  }
}
