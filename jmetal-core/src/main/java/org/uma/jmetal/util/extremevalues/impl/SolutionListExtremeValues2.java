package org.uma.jmetal.util.extremevalues.impl;

import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.extremevalues.ExtremeValuesFinder;
import org.uma.jmetal.util.front.Front;
import org.uma.jmetal.util.front.imp.ArrayFront;

import java.util.Collections;
import java.util.List;

/**
 * Created by ajnebro on 23/4/15.
 */
public class SolutionListExtremeValues2 implements ExtremeValuesFinder <List<Solution>, List<Double>> {

  @Override public List<Double> findLowestValues(List<Solution> solutionList) {
    List<Double> minimumValue ;
    if ((solutionList == null) || (solutionList.size() == 0)) {
      minimumValue = Collections.EMPTY_LIST ;
    } else {
      Front front = new ArrayFront(solutionList);
      minimumValue = new FrontExtremeValues().findLowestValues(front);
    }

    return minimumValue;
  }

  @Override public List<Double> findHighestValues(List<Solution> solutionList) {
    List<Double> maximumValue ;
    if ((solutionList == null) || (solutionList.size() == 0)) {
      maximumValue = Collections.EMPTY_LIST ;
    } else {
      int numberOfObjectives = solutionList.get(0).getNumberOfObjectives();
      maximumValue = new FrontExtremeValues().findLowestValues(new ArrayFront(solutionList));
    }

    return maximumValue;
  }
}
