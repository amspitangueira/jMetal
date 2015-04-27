package org.uma.jmetal.qualityindicator.hypervolume.impl;

import org.uma.jmetal.qualityindicator.hypervolume.Hypervolume;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.criteria.Criteria;
import org.uma.jmetal.util.front.Front;
import org.uma.jmetal.util.front.imp.ArrayFront;
import org.uma.jmetal.util.front.util.FrontUtils;
import org.uma.jmetal.util.naming.impl.SimpleDescribedEntity;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ajnebro on 19/4/15.
 *
 * This class implements the hypervolume indicator. The code is a Java version of the original
 * metric implementation by Eckart Zitzler.
 * Reference: E. Zitzler and L. Thiele. Multiobjective Evolutionary Algorithms: A Comparative Case
 * Study and the Strength Pareto Approach,
 * IEEE Transactions on Evolutionary Computation, vol. 3, no. 4,
 * pp. 257-271, 1999.
 *
 * Original code: http://www.tik.ee.ethz.ch/sop/pisa/
 */

public class PISAHypervolume extends SimpleDescribedEntity implements Hypervolume {
  public PISAHypervolume() {
    super("HV", "Hypervolume quality indicator. PISA based implementation") ;
  }

  @Override
  public double[] computeHypervolumeContribution(List<Criteria> front) {
    return new double[0];
  }

    return hypervolume(paretoFrontApproximation, trueParetoFront) ;
  }

  @Override
  public double execute(List<? extends Solution> paretoFrontApproximation,
      List<? extends Solution> trueParetoFront) {

    if (paretoFrontApproximation == null) {
      throw new JMetalException("The pareto front approximation object is null") ;
    } else if (trueParetoFront == null) {
      throw new JMetalException("The pareto front object is null");
    }

    return hypervolume(new ArrayFront(paretoFrontApproximation),
        new ArrayFront(trueParetoFront)) ;
  }

  /*
 returns true if 'point1' dominates 'points2' with respect to the
 to the first 'noObjectives' objectives
 */
  boolean dominates(double point1[], double point2[], int noObjectives) {
    int i;
    int betterInAnyObjective;

    betterInAnyObjective = 0;
    for (i = 0; i < noObjectives && point1[i] >= point2[i]; i++) {
      if (point1[i] > point2[i]) {
        betterInAnyObjective = 1;
      }
    }

    return ((i >= noObjectives) && (betterInAnyObjective > 0));
  }

  void swap(double[][] front, int i, int j) {
    double[] temp;

    temp = front[i];
    front[i] = front[j];
    front[j] = temp;
  }

  /* all nondominated points regarding the first 'noObjectives' dimensions
  are collected; the points referenced by 'front[0..noPoints-1]' are
  considered; 'front' is resorted, such that 'front[0..n-1]' contains
  the nondominated points; n is returned */
  int filterNondominatedSet(double[][] front, int noPoints, int noObjectives) {
    int i, j;
    int n;

    n = noPoints;
    i = 0;
    while (i < n) {
      j = i + 1;
      while (j < n) {
        if (dominates(front[i], front[j], noObjectives)) {
  /* remove point 'j' */
          n--;
          swap(front, j, n);
        } else if (dominates(front[j], front[i], noObjectives)) {
	/* remove point 'i'; ensure that the point copied to index 'i'
	   is considered in the next outer loop (thus, decrement i) */
          n--;
          swap(front, i, n);
          i--;
          break;
        } else {
          j++;
        }
      }
      i++;
    }
    return n;
  }

  /* calculate next value regarding dimension 'objective'; consider
     points referenced in 'front[0..noPoints-1]' */
  double surfaceUnchangedTo(double[][] front, int noPoints, int objective) {
    int i;
    double minValue, value;

    if (noPoints < 1) {
      new JMetalException("run-time error");
    }

    minValue = front[0][objective];
    for (i = 1; i < noPoints; i++) {
      value = front[i][objective];
      if (value < minValue) {
        minValue = value;
      }
    }
    return minValue;
  }

  /* remove all points which have a value <= 'threshold' regarding the
     dimension 'objective'; the points referenced by
     'front[0..noPoints-1]' are considered; 'front' is resorted, such that
     'front[0..n-1]' contains the remaining points; 'n' is returned */
  int reduceNondominatedSet(double[][] front, int noPoints, int objective,
      double threshold) {
    int n;
    int i;

    n = noPoints;
    for (i = 0; i < n; i++) {
      if (front[i][objective] <= threshold) {
        n--;
        swap(front, i, n);
      }
    }

    return n;
  }

  public double calculateHypervolume(double[][] front, int noPoints, int noObjectives) {
    int n;
    double volume, distance;

    volume = 0;
    distance = 0;
    n = noPoints;
    while (n > 0) {
      int nonDominatedPoints;
      double tempVolume, tempDistance;

      nonDominatedPoints = filterNondominatedSet(front, n, noObjectives - 1);
      if (noObjectives < 3) {
        if (nonDominatedPoints < 1) {
          new JMetalException("run-time error");
        }

        tempVolume = front[0][0];
      } else {
        tempVolume = calculateHypervolume(front, nonDominatedPoints, noObjectives - 1);
      }

      tempDistance = surfaceUnchangedTo(front, n, noObjectives - 1);
      volume += tempVolume * (tempDistance - distance);
      distance = tempDistance;
      n = reduceNondominatedSet(front, n, noObjectives - 1, distance);
    }
    return volume;
  }

  /**
   * Returns the hypervolume value of a front of points
   *
   * @param front The front
   * @param trueParetoFront The true pareto front
   */
  public double hypervolume(Front front, Front trueParetoFront) {

    double[] maximumValues;
    double[] minimumValues;
    Front normalizedFront;
    Front invertedFront;

    int numberOfObjectives = trueParetoFront.getPoint(0).getNumberOfDimensions() ;

    // STEP 1. Obtain the maximum and minimum values of the Pareto front
    maximumValues = FrontUtils.getMaximumValues(trueParetoFront);
    minimumValues = FrontUtils.getMinimumValues(trueParetoFront);

    // STEP 2. Get the normalized front
    normalizedFront = FrontUtils.getNormalizedFront(front, maximumValues, minimumValues);

    // STEP 3. Inverse the pareto front. This is needed because of the original
    //metric by Zitzler is for maximization problem
    invertedFront = FrontUtils.getInvertedFront(normalizedFront);

    // STEP4. The hypervolume (control is passed to the Java version of Zitzler code)
    return this.calculateHypervolume(FrontUtils.convertFrontToArray(invertedFront),
        invertedFront.getNumberOfPoints(), numberOfObjectives);
  }

  @Override public String getName() {
    return super.getName() ;
  }

  @Override public String getDescription() {
    return super.getDescription() ;
  }
}
