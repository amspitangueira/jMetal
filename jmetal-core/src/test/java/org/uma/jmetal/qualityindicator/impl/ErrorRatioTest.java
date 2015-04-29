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

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.uma.jmetal.problem.DoubleProblem;
import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.criteria.Criteria;
import org.uma.jmetal.util.criteria.impl.ArrayCriteria;
import org.uma.jmetal.util.front.Front;
import org.uma.jmetal.util.front.imp.ArrayFront;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;

/**
 * @author Antonio J. Nebro
 * @version 1.0
 */
public class ErrorRatioTest {
  private static final double EPSILON = 0.0000000000001 ;

  @Rule
  public ExpectedException exception = ExpectedException.none();

  private ErrorRatio errorRatio ;

  @Before public void setup() {
    errorRatio = new ErrorRatio() ;
  }

  @Test
  public void shouldExecuteRaiseAnExceptionIfTheFrontApproximationIsNull() {
    exception.expect(JMetalException.class);
    exception.expectMessage(containsString("The pareto front approximation object is null"));

    Front front = new ArrayFront(0, 0) ;
    errorRatio.execute(null, front) ;
  }

  @Test
  public void shouldExecuteRaiseAnExceptionIfTheParetoFrontIsNull() {
    exception.expect(JMetalException.class);
    exception.expectMessage(containsString("The pareto front object is null"));

    Front front = new ArrayFront(0, 0) ;

    errorRatio.execute(front, null) ;
  }

  @Test
  public void shouldExecuteRaiseAnExceptionIfTheFrontApproximationListIsNull() {
    exception.expect(JMetalException.class);
    exception.expectMessage(containsString("The pareto front approximation list is null"));

    List<DoubleSolution> list = new ArrayList<>();
    errorRatio.execute(null, list) ;
  }

  @Test
  public void shouldExecuteRaiseAnExceptionIfTheParetoFrontListIsNull() {
    exception.expect(JMetalException.class);
    exception.expectMessage(containsString("The pareto front list is null"));

    List<DoubleSolution> list = new ArrayList<>();
    errorRatio.execute(list, null) ;
  }

  @Test
  public void shouldExecuteReturnZeroIfTheFrontsContainOnePointWhichIsTheSame() {
    int numberOfPoints = 1 ;
    int numberOfDimensions = 3 ;
    Front frontApproximation = new ArrayFront(numberOfPoints, numberOfDimensions);
    Front paretoFront = new ArrayFront(numberOfPoints, numberOfDimensions);

    Criteria point1 = new ArrayCriteria(numberOfDimensions) ;
    point1.setDimensionValue(0, 10.0);
    point1.setDimensionValue(1, 12.0);
    point1.setDimensionValue(2, -1.0);

    frontApproximation.setPoint(0, point1);
    paretoFront.setPoint(0, point1);

    assertEquals(0.0, errorRatio.execute(frontApproximation, paretoFront), EPSILON);
  }

  @Test
  public void shouldExecuteReturnOneIfTheFrontsContainADifferentPoint() {
    int numberOfPoints = 1 ;
    int numberOfDimensions = 3 ;
    Front frontApproximation = new ArrayFront(numberOfPoints, numberOfDimensions);
    Front paretoFront = new ArrayFront(numberOfPoints, numberOfDimensions);

    Criteria point1 = new ArrayCriteria(numberOfDimensions) ;
    point1.setDimensionValue(0, 10.0);
    point1.setDimensionValue(1, 12.0);
    point1.setDimensionValue(2, -1.0);

    Criteria point2 = new ArrayCriteria(numberOfDimensions) ;
    point2.setDimensionValue(0, 3.0);
    point2.setDimensionValue(1, 5.0);
    point2.setDimensionValue(2, -2.0);

    frontApproximation.setPoint(0, point1);
    paretoFront.setPoint(0, point2);

    assertEquals(1.0, errorRatio.execute(frontApproximation, paretoFront), EPSILON);
  }

  /**
   * Given a front with points [1.5,4.0], [1.5,2.0],[2.0,1.5] and a Pareto front with points
   * [1.0,3.0], [1.5,2.0], [2.0, 1.5], the value of the epsilon indicator is 2/3
   */
  @Test
  public void shouldExecuteReturnTheCorrectValueCaseA() {
    int numberOfPoints = 3 ;
    int numberOfDimensions = 2 ;
    Front frontApproximation = new ArrayFront(numberOfPoints, numberOfDimensions);
    Front paretoFront = new ArrayFront(numberOfPoints, numberOfDimensions);

    Criteria point1 = new ArrayCriteria(numberOfDimensions) ;
    point1.setDimensionValue(0, 1.5);
    point1.setDimensionValue(1, 4.0);
    Criteria point2 = new ArrayCriteria(numberOfDimensions) ;
    point2.setDimensionValue(0, 1.5);
    point2.setDimensionValue(1, 2.0);
    Criteria point3 = new ArrayCriteria(numberOfDimensions) ;
    point3.setDimensionValue(0, 2.0);
    point3.setDimensionValue(1, 1.5);

    frontApproximation.setPoint(0, point1);
    frontApproximation.setPoint(1, point2);
    frontApproximation.setPoint(2, point3);

    Criteria point4 = new ArrayCriteria(numberOfDimensions) ;
    point4.setDimensionValue(0, 1.0);
    point4.setDimensionValue(1, 3.0);
    Criteria point5 = new ArrayCriteria(numberOfDimensions) ;
    point5.setDimensionValue(0, 1.5);
    point5.setDimensionValue(1, 2.0);
    Criteria point6 = new ArrayCriteria(numberOfDimensions) ;
    point6.setDimensionValue(0, 2.0);
    point6.setDimensionValue(1, 1.5);

    paretoFront.setPoint(0, point4);
    paretoFront.setPoint(1, point5);
    paretoFront.setPoint(2, point6);
    assertEquals(1.0/numberOfPoints, errorRatio.execute(frontApproximation, paretoFront), EPSILON);
  }

  /**
   * Given a list with solutions [1.5,3.0], [4.0,2.0] and another lists with solutions
   * [-1.0,-1.0], [0.0,0.0], the value of the epsilon indicator is 1
   */
  @Test
  public void shouldExecuteReturnTheCorrectValueCaseB() {
    DoubleProblem problem = new MockDoubleProblem() ;

    List<DoubleSolution> frontApproximation = Arrays.asList(problem.createSolution(),
        problem.createSolution()) ;

    frontApproximation.get(0).setObjective(0, 1.5);
    frontApproximation.get(0).setObjective(1, 3.0);
    frontApproximation.get(1).setObjective(0, 4.0);
    frontApproximation.get(1).setObjective(1, 2.0);

    List<DoubleSolution> paretoFront = Arrays.asList(problem.createSolution(),
        problem.createSolution()) ;

    paretoFront.get(0).setObjective(0, -1.0);
    paretoFront.get(0).setObjective(1, -1.0);
    paretoFront.get(1).setObjective(0, 0.0);
    paretoFront.get(1).setObjective(1, 0.0);

    assertEquals(1.0, errorRatio.execute(frontApproximation, paretoFront), EPSILON);
  }


  @Test
  public void shouldGetNameReturnTheCorrectValue() {
    assertEquals("ER", errorRatio.getName());
  }

  /**
   * Mock class representing a double problem
   */
  private class MockDoubleProblem extends AbstractDoubleProblem {

    /** Constructor */
    public MockDoubleProblem() {
      setNumberOfVariables(2);
      setNumberOfObjectives(2);

      List<Double> lowerLimit = new ArrayList<>(getNumberOfVariables()) ;
      List<Double> upperLimit = new ArrayList<>(getNumberOfVariables()) ;

      for (int i = 0; i < getNumberOfVariables(); i++) {
        lowerLimit.add(-4.0);
        upperLimit.add(4.0);
      }

      setLowerLimit(lowerLimit);
      setUpperLimit(upperLimit);
    }

    /** Evaluate() method */
    @Override
    public void evaluate(DoubleSolution solution) {
      solution.setObjective(0, 0.0);
      solution.setObjective(1, 1.0);
    }
  }
}
