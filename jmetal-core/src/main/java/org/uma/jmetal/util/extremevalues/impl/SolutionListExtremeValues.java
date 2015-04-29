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
	  return new FrontExtremeValues().findLowestValues(new SolutionListToFront().extract(solutionList));
  }

  @Override public List<Double> findHighestValues(List<Solution> solutionList) {
	  return new FrontExtremeValues().findHighestValues(new SolutionListToFront().extract(solutionList));
  }
}
