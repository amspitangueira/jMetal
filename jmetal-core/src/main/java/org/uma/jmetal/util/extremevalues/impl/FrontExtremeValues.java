package org.uma.jmetal.util.extremevalues.impl;

import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.front.Front;
import org.uma.jmetal.util.extremevalues.ExtremeValuesFinder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ajnebro on 23/4/15.
 */
public class FrontExtremeValues implements ExtremeValuesFinder <Front, List<Double>> {

  @Override public List<Double> findLowestValues(Front front) {
    List<Double> minimumValue = new ArrayList<>() ;

    if (front == null) {
      throw new JMetalException("The front is null") ;
    } else if (front.getNumberOfPoints() == 0) {
      throw new JMetalException("The front is empty") ;
    }

    int numberOfObjectives = front.getPoint(0).getNumberOfDimensions() ;

    for (int i = 0; i < numberOfObjectives; i++) {
      minimumValue.add(Double.MAX_VALUE);
    }

    for (int i = 0 ; i < front.getNumberOfPoints(); i++) {
      for (int j = 0; j < numberOfObjectives; j++) {
        if (front.getPoint(i).getDimensionValue(j) < minimumValue.get(j)) {
          minimumValue.set(j, front.getPoint(i).getDimensionValue(j));
        }
      }
    }

    return minimumValue;
  }

  @Override public List<Double> findHighestValues(Front front) {
    List<Double> maximumValue = new ArrayList<>() ;

    if (front == null) {
      throw new JMetalException("The front is null") ;
    } else if (front.getNumberOfPoints() == 0) {
      throw new JMetalException("The front is empty") ;
    }

    int numberOfObjectives = front.getPoint(0).getNumberOfDimensions() ;

    for (int i = 0; i < numberOfObjectives; i++) {
      maximumValue.add(Double.NEGATIVE_INFINITY);
    }

    for (int i = 0 ; i < front.getNumberOfPoints(); i++) {
      for (int j = 0; j < numberOfObjectives; j++) {
        if (front.getPoint(i).getDimensionValue(j) > maximumValue.get(j)) {
          maximumValue.set(j, front.getPoint(i).getDimensionValue(j));
        }
      }
    }

    return maximumValue;
  }
}
