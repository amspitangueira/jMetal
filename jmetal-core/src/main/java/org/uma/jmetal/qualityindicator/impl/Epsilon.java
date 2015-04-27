//  Epsilon.java
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
import org.uma.jmetal.util.naming.impl.SimpleDescribedEntity;
import org.uma.jmetal.util.normalization.impl.NormalizeFront;

import java.util.List;

/**
 * This class implements the unary epsilon additive indicator as proposed in E.
 * Zitzler, E. Thiele, L. Laummanns, M., Fonseca, C., and Grunert da Fonseca. V
 * (2003): Performance Assessment of Multiobjective Optimizers: An Analysis and
 * Review. The code is the a Java version of the original metric implementation
 * by Eckart Zitzler.
 *
 * Original code: http://www.tik.ee.ethz.ch/sop/pisa/
 */

public class Epsilon extends SimpleDescribedEntity implements QualityIndicator {
  private boolean normalizeFrontBeforeApplyingTheIndicator = false ;

  public Epsilon() {
    super ("EPSILON", "Additive Epsilon indicator") ;
  }

  @Override
  public double execute(List<Criteria> paretoFrontApproximation, List<Criteria> trueParetoFront) {
    if (paretoFrontApproximation == null) {
      throw new JMetalException("The pareto front approximation object is null") ;
    } else if (trueParetoFront == null) {
      throw new JMetalException("The pareto front object is null");
    }

    return epsilon(paretoFrontApproximation, trueParetoFront) ;
  }


  @Override public String getName() {
    return super.getName() ;
  }

  public void normalizeFronts() {
    normalizeFrontBeforeApplyingTheIndicator = true ;
  }

  /**
   * Returns the value of the epsilon indicator.
   *
   * @param front Solution front
   * @param referenceFront True Pareto front
   * @return the value of the epsilon indicator
   * @throws org.uma.jmetal.util.JMetalException
   */
  private double epsilon(List<Criteria> front, List<Criteria> paretoFront) throws JMetalException {
    int i, j, k;
    double eps, epsJ = 0.0, epsK = 0.0, epsTemp;

    int numberOfObjectives = front.size() ;

    eps = Double.MIN_VALUE;

    List<Criteria> approximatedFront ;
    List<Criteria> referenceParetoFront ;

    if (normalizeFrontBeforeApplyingTheIndicator) {
      List<Double> maximumValue;
      List<Double> minimumValue;

      maximumValue = new FrontExtremeValues().findHighestValues(paretoFront);
      minimumValue = new FrontExtremeValues().findLowestValues(paretoFront);
      
      approximatedFront = (new NormalizeFront()).normalize(front, maximumValue, minimumValue);
      referenceParetoFront = (new NormalizeFront()).normalize(paretoFront, maximumValue, minimumValue);
    } else {
      approximatedFront = front ;
      referenceParetoFront = paretoFront ;
    }

    for (i = 0; i < referenceParetoFront.size(); i++) {
      for (j = 0; j < approximatedFront.size(); j++) {
        for (k = 0; k < numberOfObjectives; k++) {
          epsTemp = approximatedFront.get(j).getDimensionValue(k)
              - referenceParetoFront.get(i).getDimensionValue(k);
          if (k == 0) {
            epsK = epsTemp;
          } else if (epsK < epsTemp) {
            epsK = epsTemp;
          }
        }
        if (j == 0) {
          epsJ = epsK;
        } else if (epsJ > epsK) {
          epsJ = epsK;
        }
      }
      if (i == 0) {
        eps = epsJ;
      } else if (eps < epsJ) {
        eps = epsJ;
      }
    }
    return eps;
  }
}
