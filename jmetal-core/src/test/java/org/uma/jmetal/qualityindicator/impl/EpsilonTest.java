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
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.criteria.Criteria;
import org.uma.jmetal.util.criteria.impl.ArrayCriteria;
import org.uma.jmetal.util.criteria.impl.PointSolution;
import org.uma.jmetal.util.front.Front;
import org.uma.jmetal.util.front.imp.ArrayFront;
import org.uma.jmetal.util.front.util.FrontUtils;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;

/**
 * @author Antonio J. Nebro
 * @version 1.0
 */
public class EpsilonTest {
  private static final double EPSILON = 0.0000000000001 ;

  @Rule
  public ExpectedException exception = ExpectedException.none();

  private Epsilon epsilon ;

  @Before public void setup() {
    epsilon = new Epsilon() ;
  }

  @Test
  public void shouldExecuteRaiseAnExceptionIfTheFrontApproximationIsNull() {
    exception.expect(JMetalException.class);
    exception.expectMessage(containsString("The pareto front approximation object is null"));

    Front front = new ArrayFront(0, 0) ;
    epsilon.execute(null, front) ;
  }

  @Test
  public void shouldExecuteRaiseAnExceptionIfTheParetoFrontIsNull() {
    exception.expect(JMetalException.class);
    exception.expectMessage(containsString("The pareto front object is null"));

    Front front = new ArrayFront(0, 0) ;

    epsilon.execute(front, null) ;
  }

  @Test
  public void shouldExecuteRaiseAnExceptionIfTheFrontApproximationListIsNull() {
    exception.expect(JMetalException.class);
    exception.expectMessage(containsString("The pareto front approximation list is null"));

    List<DoubleSolution> list = new ArrayList<>();
    epsilon.execute(null, list) ;
  }

  @Test
  public void shouldExecuteRaiseAnExceptionIfTheParetoFrontListIsNull() {
    exception.expect(JMetalException.class);
    exception.expectMessage(containsString("The pareto front list is null"));

    List<DoubleSolution> list = new ArrayList<>();
    epsilon.execute(list, null) ;
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

    assertEquals(0.0, epsilon.execute(frontApproximation, paretoFront), EPSILON);
  }

  /**
   * Given a front with point [2,3] and a Pareto front with point [1,2], the value of the
   * epsilon indicator is 1
   */
  @Test
  public void shouldExecuteReturnTheRightValueIfTheFrontsContainOnePointWhichIsNotTheSame() {
    int numberOfPoints = 1 ;
    int numberOfDimensions = 2 ;
    Front frontApproximation = new ArrayFront(numberOfPoints, numberOfDimensions);
    Front paretoFront = new ArrayFront(numberOfPoints, numberOfDimensions);

    Criteria point1 = new ArrayCriteria(numberOfDimensions) ;
    point1.setDimensionValue(0, 2.0);
    point1.setDimensionValue(1, 3.0);
    Criteria point2 = new ArrayCriteria(numberOfDimensions) ;
    point2.setDimensionValue(0, 1.0);
    point2.setDimensionValue(1, 2.0);

    frontApproximation.setPoint(0, point1);
    paretoFront.setPoint(0, point2);

    assertEquals(1.0, epsilon.execute(frontApproximation, paretoFront), EPSILON);
  }

  /**
   * Given a front with points [1.5,4.0], [2.0,3.0],[3.0,2.0] and a Pareto front with points
   * [1.0,3.0], [1.5,2.0], [2.0, 1.5], the value of the epsilon indicator is 1
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
    point2.setDimensionValue(0, 2.0);
    point2.setDimensionValue(1, 3.0);
    Criteria point3 = new ArrayCriteria(numberOfDimensions) ;
    point3.setDimensionValue(0, 3.0);
    point3.setDimensionValue(1, 2.0);

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

    assertEquals(1.0, epsilon.execute(frontApproximation, paretoFront), EPSILON);
  }

  /**
   * Given a front with points [1.5,4.0], [1.5,2.0],[2.0,1.5] and a Pareto front with points
   * [1.0,3.0], [1.5,2.0], [2.0, 1.5], the value of the epsilon indicator is 0.5
   */
  @Test
  public void shouldExecuteReturnTheCorrectValueCaseB() {
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

    assertEquals(0.5, epsilon.execute(frontApproximation, paretoFront), EPSILON);
  }

  /**
   * The same case as shouldExecuteReturnTheCorrectValueCaseB() but using list of solutions
   */
  @Test
  public void shouldExecuteReturnTheCorrectValueCaseC() {
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

    List<PointSolution> listA = FrontUtils.convertFrontToSolutionList(frontApproximation) ;
    List<PointSolution> listB = FrontUtils.convertFrontToSolutionList(paretoFront) ;

    assertEquals(0.5, epsilon.execute(listA, listB), EPSILON);
  }

  /**
   *  Given a front with points:
   0.1    1
   0.2    0.9
   0.3    0.8
   0.4    0.7
   0.5    0.6
   0.6    0.5
   0.7    0.4
   0.8    0.3
   0.9    0.2
   1    0.1
   *   and a Pareto front with points
   0.1    1.1
   0.2    1
   0.3    0.9
   0.4    0.8
   0.5    0.7
   0.6    0.6
   0.7    0.5
   0.8    0.4
   0.9    0.3
   1    0.2

   * the value the epsilon indicator should be 0.1
   */
  @Test
  public void shouldExecuteReturnTheCorrectValueCaseD() {
    int numberOfPoints = 10 ;
    int numberOfDimensions = 2 ;
    Front frontApproximation = new ArrayFront(numberOfPoints, numberOfDimensions);
    Front paretoFront = new ArrayFront(numberOfPoints, numberOfDimensions);

    Criteria point1 = new ArrayCriteria(new double[] {0.1, 1.0}) ;
    Criteria point2 = new ArrayCriteria(new double[] {0.2, 0.9}) ;
    Criteria point3 = new ArrayCriteria(new double[] {0.3, 0.8}) ;
    Criteria point4 = new ArrayCriteria(new double[] {0.4, 0.7}) ;
    Criteria point5 = new ArrayCriteria(new double[] {0.5, 0.6}) ;
    Criteria point6 = new ArrayCriteria(new double[] {0.6, 0.5}) ;
    Criteria point7 = new ArrayCriteria(new double[] {0.7, 0.5}) ;
    Criteria point8 = new ArrayCriteria(new double[] {0.8, 0.3}) ;
    Criteria point9 = new ArrayCriteria(new double[] {0.9, 0.2}) ;
    Criteria point10 = new ArrayCriteria(new double[] {1.0, 0.1}) ;

    paretoFront.setPoint(0, point1);
    paretoFront.setPoint(1, point2);
    paretoFront.setPoint(2, point3);
    paretoFront.setPoint(3, point4);
    paretoFront.setPoint(4, point5);
    paretoFront.setPoint(5, point6);
    paretoFront.setPoint(6, point7);
    paretoFront.setPoint(7, point8);
    paretoFront.setPoint(8, point9);
    paretoFront.setPoint(9, point10);

    Criteria point11 = new ArrayCriteria(new double[] {0.1, 1.1}) ;
    Criteria point12 = new ArrayCriteria(new double[] {0.2, 1.0}) ;
    Criteria point13 = new ArrayCriteria(new double[] {0.3, 0.9}) ;
    Criteria point14 = new ArrayCriteria(new double[] {0.4, 0.8}) ;
    Criteria point15 = new ArrayCriteria(new double[] {0.5, 0.7}) ;
    Criteria point16 = new ArrayCriteria(new double[] {0.6, 0.6}) ;
    Criteria point17 = new ArrayCriteria(new double[] {0.7, 0.5}) ;
    Criteria point18 = new ArrayCriteria(new double[] {0.8, 0.4}) ;
    Criteria point19 = new ArrayCriteria(new double[] {0.9, 0.3}) ;
    Criteria point20 = new ArrayCriteria(new double[] {1.0, 0.2}) ;

    frontApproximation.setPoint(0, point11);
    frontApproximation.setPoint(1, point12);
    frontApproximation.setPoint(2, point13);
    frontApproximation.setPoint(3, point14);
    frontApproximation.setPoint(4, point15);
    frontApproximation.setPoint(5, point16);
    frontApproximation.setPoint(6, point17);
    frontApproximation.setPoint(7, point18);
    frontApproximation.setPoint(8, point19);
    frontApproximation.setPoint(9, point20);

    List<PointSolution> listA = FrontUtils.convertFrontToSolutionList(frontApproximation) ;
    List<PointSolution> listB = FrontUtils.convertFrontToSolutionList(paretoFront) ;

    assertEquals(0.1, epsilon.execute(listA, listB), EPSILON);
  }

  @Test
  public void shouldGetNameReturnTheCorrectValue() {
    assertEquals("EPSILON", epsilon.getName());
  }
}
