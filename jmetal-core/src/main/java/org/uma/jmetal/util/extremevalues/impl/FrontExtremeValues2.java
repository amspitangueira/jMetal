package org.uma.jmetal.util.extremevalues.impl;

import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.extremevalues.ExtremeValuesFinder;
import org.uma.jmetal.util.front.Front;

/**
 * Created by ajnebro on 23/4/15.
 */
public class FrontExtremeValues2 implements ExtremeValuesFinder <Front, double[]> {

  @Override public double[] findLowestValues(Front front) {
    if (front == null) {
      throw new JMetalException("The front is null") ;
    } else if (front.getNumberOfPoints() == 0) {
      throw new JMetalException("The front is empty") ;
    }

    double[] minimumValue = new double[front.getNumberOfPoints()] ;
    int numberOfObjectives = front.getPoint(0).getNumberOfDimensions() ;

    for (int i = 0; i < numberOfObjectives; i++) {
      minimumValue[i] = Double.POSITIVE_INFINITY;
    }

    for (int i = 0 ; i < front.getNumberOfPoints(); i++) {
      for (int j = 0; j < numberOfObjectives; j++) {
        if (front.getPoint(i).getDimensionValue(j) < minimumValue[j]) {
          minimumValue[j] = front.getPoint(i).getDimensionValue(j);
        }
      }
    }

    return minimumValue;
  }

  @Override public double[] findHighestValues(Front front) {
    if (front == null) {
      throw new JMetalException("The front is null") ;
    } else if (front.getNumberOfPoints() == 0) {
      throw new JMetalException("The front is empty") ;
    }

    int numberOfObjectives = front.getPoint(0).getNumberOfDimensions() ;
    double[] maximumValue = new double[front.getNumberOfPoints()] ;

    for (int i = 0; i < numberOfObjectives; i++) {
      maximumValue[i] = Double.NEGATIVE_INFINITY;
    }

    for (int i = 0 ; i < front.getNumberOfPoints(); i++) {
      for (int j = 0; j < numberOfObjectives; j++) {
        if (front.getPoint(i).getDimensionValue(j) > maximumValue[j]) {
          maximumValue[j] = front.getPoint(i).getDimensionValue(j);
        }
      }
    }

    return maximumValue;
  }
}
