package org.uma.jmetal.util.extremevalues.impl;

import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.criteria.Criteria;
import org.uma.jmetal.util.extremevalues.ExtremeValuesFinder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ajnebro on 23/4/15.
 */
public class FrontExtremeValues implements ExtremeValuesFinder <List<Criteria>, List<Double>> {

  @Override public List<Double> findLowestValues(List<Criteria> front) {
    List<Double> minimumValue = new ArrayList<>() ;

    if (front == null) {
      throw new JMetalException("The front is null") ;
    } else if (front.size() == 0) {
      throw new JMetalException("The front is empty") ;
    }

    int numberOfObjectives = front.get(0).getNumberOfDimensions() ;

    for (int i = 0; i < numberOfObjectives; i++) {
      minimumValue.add(Double.POSITIVE_INFINITY);
    }

    for (int i = 0 ; i < front.size(); i++) {
      for (int j = 0; j < numberOfObjectives; j++) {
        if (front.get(i).getDimensionValue(j) < minimumValue.get(j)) {
          minimumValue.set(j, front.get(i).getDimensionValue(j));
        }
      }
    }

    return minimumValue;
  }

  @Override public List<Double> findHighestValues(List<Criteria> front) {
    List<Double> maximumValue = new ArrayList<>() ;

    if (front == null) {
      throw new JMetalException("The front is null") ;
    } else if (front.size() == 0) {
      throw new JMetalException("The front is empty") ;
    }

    int numberOfObjectives = front.get(0).getNumberOfDimensions() ;

    for (int i = 0; i < numberOfObjectives; i++) {
      maximumValue.add(Double.NEGATIVE_INFINITY);
    }

    for (int i = 0 ; i < front.size(); i++) {
      for (int j = 0; j < numberOfObjectives; j++) {
        if (front.get(i).getDimensionValue(j) > maximumValue.get(j)) {
          maximumValue.set(j, front.get(i).getDimensionValue(j));
        }
      }
    }

    return maximumValue;
  }
}
