//  GenerationalDistance.java
//
//  Author:
//       Antonio J. Nebro <antonio@lcc.uma.es>
//       Juan J. Durillo <durillo@lcc.uma.es>
//
//  Copyright (c) 2011 Antonio J. Nebro, Juan J. Durillo
//
//  This program is free software: you can redistribute it and/or modify
//  it under the terms of the GNU Lesser General Public License as published by
//  the Free Software Foundation, either version 3 of the License, or
//  (at your option) any later version.
//
//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU Lesser General Public License for more details.
// 
//  You should have received a copy of the GNU Lesser General Public License
//  along with this program.  If not, see <http://www.gnu.org/licenses/>.

package org.uma.jmetal.qualityindicator.impl;

import org.uma.jmetal.qualityindicator.QualityIndicator;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.criteria.Criteria;
import org.uma.jmetal.util.extremevalues.impl.FrontExtremeValues;
import org.uma.jmetal.util.front.Front;
import org.uma.jmetal.util.front.imp.ArrayFront;
import org.uma.jmetal.util.front.util.FrontUtils;
import org.uma.jmetal.util.naming.impl.SimpleDescribedEntity;
import org.uma.jmetal.util.normalization.impl.NormalizeFront;

import java.util.List;

/**
 * This class implements the generational distance indicator.
 * Reference: Van Veldhuizen, D.A., Lamont, G.B.: Multiobjective Evolutionary
 * Algorithm Research: A History and Analysis.
 * Technical Report TR-98-03, Dept. Elec. Comput. Eng., Air Force
 * Inst. Technol. (1998)
 */
public class GenerationalDistance extends SimpleDescribedEntity implements QualityIndicator {

  private static final double POW = 2.0;

  public GenerationalDistance() {
    super("GD", "Generational distance quality indicator") ;
  }

  @Override
  public double execute(List<Criteria> paretoFrontApproximation, List<Criteria> trueParetoFront) {
    if (paretoFrontApproximation == null) {
      throw new JMetalException("The pareto front approximation object is null") ;
    } else if (trueParetoFront == null) {
      throw new JMetalException("The pareto front object is null");
    }

    return generationalDistance(paretoFrontApproximation, trueParetoFront) ;
  }


  /**
   * Returns the generational distance value for a given front
   *
   * @param front           The front
   * @param trueParetoFront The true pareto front
   */
  public double generationalDistance(List<Criteria> front, List<Criteria> trueParetoFront) {
    List<Double> maximumValue;
    List<Double> minimumValue;
    List<Criteria> normalizedFront;
    List<Criteria> normalizedParetoFront;

    // STEP 1. Obtain the maximum and minimum values of the Pareto front
    maximumValue = new FrontExtremeValues().findHighestValues(trueParetoFront);
    minimumValue = new FrontExtremeValues().findLowestValues(trueParetoFront);

    // STEP 2. Get the normalized front and true Pareto fronts
    normalizedFront = new NormalizeFront().normalize(front, maximumValue, minimumValue);
    normalizedParetoFront = new NormalizeFront().normalize(trueParetoFront,maximumValue,minimumValue);

    // STEP 3. Sum the distances between each point of the front and the
    // nearest point in the true Pareto front
    double sum = 0.0;
    for (int i = 0; i < front.size(); i++) {
      sum += Math.pow(FrontUtils.distanceToClosestPoint(normalizedFront.getPoint(i),
              normalizedParetoFront), POW);
    }

    // STEP 4. Obtain the sqrt of the sum
    sum = Math.pow(sum, 1.0 / POW);

    // STEP 5. Divide the sum by the maximum number of points of the front
    double generationalDistance = sum / normalizedFront.getNumberOfPoints();

    return generationalDistance;
  }

  @Override
  public String getName() {
    return super.getName();
  }
}
