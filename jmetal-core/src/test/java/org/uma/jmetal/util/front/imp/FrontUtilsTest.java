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

package org.uma.jmetal.util.front.imp;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.front.Front;
import org.uma.jmetal.util.front.util.FrontUtils;
import org.uma.jmetal.util.point.Point;
import org.uma.jmetal.util.point.impl.ArrayPoint;
import org.uma.jmetal.util.point.impl.PointSolution;

import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * @author Antonio J. Nebro
 * @version 1.0
 */
public class FrontUtilsTest {
  private static final double EPSILON = 0.0000000000001 ;

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void shouldGetMaximumValuesRaiseAnExceptionIfTheFrontIsNull() {
    exception.expect(JMetalException.class);
    exception.expectMessage(containsString("The front is null"));

    FrontUtils.getMaximumValues(null) ;
  }

  @Test
  public void shouldGetMaximumValuesRaiseAnExceptionIfTheFrontIsEmpty() {
    exception.expect(JMetalException.class);
    exception.expectMessage(containsString("The front is empty"));

    Front front = new ArrayFront(0, 0);

    FrontUtils.getMaximumValues(front) ;
  }

  @Test
  public void shouldGetMaximumValuesWithAFrontWithOnePointReturnTheCorrectValue() {
    int numberOfPoints = 1 ;
    int numberOfDimensions = 2 ;
    Front front = new ArrayFront(numberOfPoints, numberOfDimensions);

    Point point = new ArrayPoint(numberOfDimensions) ;
    point.setDimensionValue(0, 10.0);
    point.setDimensionValue(1, 12.0);

    front.setPoint(0, point);

    double[] expectedResult = {10.0, 12.0} ;

    assertArrayEquals(expectedResult, FrontUtils.getMaximumValues(front), EPSILON) ;
  }

  @Test
  public void shouldGetMaximumValuesWithAFrontWithThreePointReturnTheCorrectValue() {
    int numberOfPoints = 3 ;
    int numberOfDimensions = 3 ;
    Front front = new ArrayFront(numberOfPoints, numberOfDimensions);

    Point point1 = new ArrayPoint(numberOfDimensions) ;
    point1.setDimensionValue(0, 10.0);
    point1.setDimensionValue(1, 12.0);
    point1.setDimensionValue(2, -1.0);
    Point point2 = new ArrayPoint(numberOfDimensions) ;
    point2.setDimensionValue(0, 8.0);
    point2.setDimensionValue(1, 80.0);
    point2.setDimensionValue(2, 0.32);
    Point point3 = new ArrayPoint(numberOfDimensions) ;
    point3.setDimensionValue(0, 5.0);
    point3.setDimensionValue(1, 50.0);
    point3.setDimensionValue(2, 3.0);

    front.setPoint(0, point1);
    front.setPoint(1, point2);
    front.setPoint(2, point3);

    double[] expectedResult = {10.0, 80.0, 3.0} ;

    assertArrayEquals(expectedResult, FrontUtils.getMaximumValues(front), EPSILON) ;
  }

  @Test
  public void shouldGetMinimumValuesRaiseAnExceptionIfTheFrontIsNull() {
    exception.expect(JMetalException.class);
    exception.expectMessage(containsString("The front is null"));

    FrontUtils.getMinimumValues(null) ;
  }

  @Test
  public void shouldGetMinimumValuesRaiseAnExceptionIfTheFrontIsEmpty() {
    exception.expect(JMetalException.class);
    exception.expectMessage(containsString("The front is empty"));

    Front front = new ArrayFront(0, 0);

    FrontUtils.getMinimumValues(front) ;
  }

  @Test
  public void shouldGetMinimumValuesWithAFrontWithOnePointReturnTheCorrectValue() {
    int numberOfPoints = 1 ;
    int numberOfDimensions = 2 ;
    Front front = new ArrayFront(numberOfPoints, numberOfDimensions);

    Point point = new ArrayPoint(numberOfDimensions) ;
    point.setDimensionValue(0, 10.0);
    point.setDimensionValue(1, 12.0);

    front.setPoint(0, point);

    double[] expectedResult = {10.0, 12.0} ;

    assertArrayEquals(expectedResult, FrontUtils.getMaximumValues(front), EPSILON) ;
  }

  @Test
  public void shouldGetMinimumValuesWithAFrontWithThreePointReturnTheCorrectValue() {
    int numberOfPoints = 3 ;
    int numberOfDimensions = 3 ;
    Front front = new ArrayFront(numberOfPoints, numberOfDimensions);

    Point point1 = new ArrayPoint(numberOfDimensions) ;
    point1.setDimensionValue(0, 10.0);
    point1.setDimensionValue(1, 12.0);
    point1.setDimensionValue(2, -1.0);
    Point point2 = new ArrayPoint(numberOfDimensions) ;
    point2.setDimensionValue(0, 8.0);
    point2.setDimensionValue(1, 80.0);
    point2.setDimensionValue(2, 0.32);
    Point point3 = new ArrayPoint(numberOfDimensions) ;
    point3.setDimensionValue(0, 5.0);
    point3.setDimensionValue(1, 50.0);
    point3.setDimensionValue(2, 3.0);

    front.setPoint(0, point1);
    front.setPoint(1, point2);
    front.setPoint(2, point3);

    double[] expectedResult = {5.0, 12.0, -1.0} ;

    assertArrayEquals(expectedResult, FrontUtils.getMinimumValues(front), EPSILON) ;
  }

  @Test
  public void shouldGetNormalizedFrontRaiseAnExceptionIfTheFrontIsNull() {
    exception.expect(JMetalException.class);
    exception.expectMessage(containsString("The front is null"));

    FrontUtils.getNormalizedFront(null, new double[0], new double[0]) ;
  }

  @Test
  public void shouldGetNormalizedFrontRaiseAnExceptionIfTheFrontIsEmpty() {
    exception.expect(JMetalException.class);
    exception.expectMessage(containsString("The front is empty"));

    Front front = new ArrayFront(0, 0);

    FrontUtils.getNormalizedFront(front, new double[0], new double[0]) ;
  }

  @Test
  public void shouldGetNormalizedFrontRaiseAnExceptionIfMaximumArrayIsNull() {
    exception.expect(JMetalException.class);
    exception.expectMessage(containsString("The maximum values array is null"));

    Front front = new ArrayFront(1, 2);

    FrontUtils.getNormalizedFront(front, null, new double[0]) ;
  }

  @Test
  public void shouldGetNormalizedFrontRaiseAnExceptionIfMinimumArrayIsNull() {
    exception.expect(JMetalException.class);
    exception.expectMessage(containsString("The minimum values array is null"));

    Front front = new ArrayFront(1, 2);

    FrontUtils.getNormalizedFront(front, new double[0], null) ;
  }

  @Test
  public void shouldGetNormalizedFrontRaiseAnExceptionTheDimensionOfTheMaximumAndMinimumArrayIsNotEqual() {
    exception.expect(JMetalException.class);
    exception.expectMessage(containsString("The length of the maximum array (4) "
        + "is different from the length of the minimum array (2)"));

    int numberOfPointDimensions = 2 ;

    Front front = new ArrayFront(1, numberOfPointDimensions);

    FrontUtils.getNormalizedFront(front, new double[4], new double[2]) ;
  }

  @Test
  public void shouldGetNormalizedFrontRaiseAnExceptionTheDimensionOfTheMaximumArrayPointsIsNotCorrect() {
    exception.expect(JMetalException.class);
    exception.expectMessage(containsString("The length of the point dimensions (2) " +
       "is different from the length of the maximum array (4)"));

    int numberOfPointDimensions = 2 ;

    Front front = new ArrayFront(1, numberOfPointDimensions);

    FrontUtils.getNormalizedFront(front, new double[4], new double[4]) ;
  }

  /**
   * Point: [2,4]
   * Maximum values: [4, 4]
   * Minimum values: [0, 0]
   * Result: [0.5, 1.0]
   */
  @Test
  public void shouldGetNormalizedFrontReturnTheCorrectFrontIfThisContainsOnePoint() {
    int numberOfPoints = 1 ;
    int numberOfDimensions = 2 ;
    Front front = new ArrayFront(numberOfPoints, numberOfDimensions);

    Point point = new ArrayPoint(numberOfDimensions) ;
    point.setDimensionValue(0, 2);
    point.setDimensionValue(1, 4);

    front.setPoint(0, point);

    double[] minimum = {0, 0} ;
    double[] maximum = {4, 4} ;

    Front normalizedFront = FrontUtils.getNormalizedFront(front, maximum, minimum) ;
    assertEquals(0.5, normalizedFront.getPoint(0).getDimensionValue(0), EPSILON) ;
    assertEquals(1.0, normalizedFront.getPoint(0).getDimensionValue(1), EPSILON) ;
  }

  /**
   * Point: [2,4]
   * Maximum values: [2, 4]
   * Minimum values: [2, 4]
   * Result: [0.5, 1.0]
   */
  @Test
  public void shouldGetNormalizedFrontRaiseAnExceptionIfTheMaxAndMinValuesAreTheSame() {
    exception.expect(JMetalException.class);
    exception.expectMessage(containsString("Maximum and minimum values of index 0 are the same: 2"));

    int numberOfPoints = 1 ;
    int numberOfDimensions = 2 ;
    Front front = new ArrayFront(numberOfPoints, numberOfDimensions);

    Point point = new ArrayPoint(numberOfDimensions) ;
    point.setDimensionValue(0, 2);
    point.setDimensionValue(1, 4);

    front.setPoint(0, point);

    double[] minimum = {2, 4} ;
    double[] maximum = {2, 4} ;

    FrontUtils.getNormalizedFront(front, maximum, minimum) ;
  }

  /**
   * Points: [2,4], [-2, 3]
   * Maximum values: [6, 8]
   * Minimum values: [-10, 1]
   * Result: [0.5, 1.0], []
   */
  @Test
  public void shouldGetNormalizedFrontReturnTheCorrectFrontIfThisContainsTwoPoints() {
    int numberOfPoints = 2 ;
    int numberOfDimensions = 2 ;
    Front front = new ArrayFront(numberOfPoints, numberOfDimensions);

    Point point1 = new ArrayPoint(numberOfDimensions) ;
    point1.setDimensionValue(0, 2);
    point1.setDimensionValue(1, 4);
    Point point2 = new ArrayPoint(numberOfDimensions) ;
    point2.setDimensionValue(0, -2);
    point2.setDimensionValue(1, 3);

    front.setPoint(0, point1);
    front.setPoint(1, point2);

    double[] minimum = {-10, 1} ;
    double[] maximum = {6, 8} ;

    Front normalizedFront = FrontUtils.getNormalizedFront(front, maximum, minimum) ;
    assertEquals(0.75, normalizedFront.getPoint(0).getDimensionValue(0), EPSILON) ;
    assertEquals(3.0/7.0, normalizedFront.getPoint(0).getDimensionValue(1), EPSILON) ;
    assertEquals(0.5, normalizedFront.getPoint(1).getDimensionValue(0), EPSILON) ;
    assertEquals(2.0/7.0, normalizedFront.getPoint(1).getDimensionValue(1), EPSILON) ;
  }

  @Test
  public void shouldDistanceToNearestPointRaiseAnExceptionIfTheFrontIsNull() {
    exception.expect(JMetalException.class);
    exception.expectMessage(containsString("The front is null"));

    Point point = new ArrayPoint(1) ;

    FrontUtils.distanceToNearestPoint(point, null) ;
  }

  @Test
  public void shouldDistanceToNearestPointRaiseAnExceptionIfThePointIsNull() {
    exception.expect(JMetalException.class);
    exception.expectMessage(containsString("The point is null"));

    Front front = new ArrayFront(1, 1) ;

    FrontUtils.distanceToNearestPoint(null, front) ;
  }

  @Test
  public void shouldDistanceToNearestPointRaiseAnExceptionIfTheFrontIsEmpty() {
    exception.expect(JMetalException.class);
    exception.expectMessage(containsString("The front is empty"));

    Point point = new ArrayPoint(1) ;
    Front front = new ArrayFront(0, 1) ;

    FrontUtils.distanceToNearestPoint(point, front) ;
  }

  @Test
  public void shouldDistanceToNearestPointReturnMaxDoubleIfThePointIsTheOnlyPointInTheFront() {
    int numberOfDimensions = 2 ;
    int numberOfPoints = 1 ;

    Point point = new ArrayPoint(numberOfDimensions) ;
    point.setDimensionValue(0, 2);
    point.setDimensionValue(1, 4);

    Front front = new ArrayFront(numberOfPoints, numberOfDimensions) ;
    front.setPoint(0, point);

    assertEquals(Double.MAX_VALUE, FrontUtils.distanceToNearestPoint(point, front), EPSILON);
  }

  @Test
  public void shouldDistanceToNearestPointReturnTheCorrectValueIfTheFrontHasHasOnePoint() {
    int numberOfDimensions = 2 ;
    int numberOfPoints = 1 ;

    Point point1 = new ArrayPoint(numberOfDimensions) ;
    point1.setDimensionValue(0, 2);
    point1.setDimensionValue(1, 4);

    Point point2 = new ArrayPoint(numberOfDimensions) ;
    point2.setDimensionValue(0, 6);
    point2.setDimensionValue(1, 7);

    Front front = new ArrayFront(numberOfPoints, numberOfDimensions) ;
    front.setPoint(0, point1);

    assertEquals(5.0, FrontUtils.distanceToNearestPoint(point2, front), EPSILON);
  }

  /**
   * Case A: the front has two points and one of them is the point passed as a parameter
   */
  @Test
  public void shouldDistanceToNearestPointReturnTheCorrectValueIfTheFrontHasTwoPointsCaseA() {
    int numberOfDimensions = 2 ;
    int numberOfPoints = 2 ;

    Point point1 = new ArrayPoint(numberOfDimensions) ;
    point1.setDimensionValue(0, 2);
    point1.setDimensionValue(1, 4);

    Point point2 = new ArrayPoint(numberOfDimensions) ;
    point2.setDimensionValue(0, 6);
    point2.setDimensionValue(1, 7);

    Front front = new ArrayFront(numberOfPoints, numberOfDimensions) ;
    front.setPoint(0, point1);
    front.setPoint(1, point2);

    assertEquals(5.0, FrontUtils.distanceToNearestPoint(point1, front), EPSILON);
  }

  @Test
  public void shouldDistanceToClosestPointRaiseAnExceptionIfTheFrontIsNull() {
    exception.expect(JMetalException.class);
    exception.expectMessage(containsString("The front is null"));

    Point point = new ArrayPoint(1) ;

    FrontUtils.distanceToClosestPoint(point, null) ;
  }

  @Test
  public void shouldDistanceToClosestPointRaiseAnExceptionIfThePointIsNull() {
    exception.expect(JMetalException.class);
    exception.expectMessage(containsString("The point is null"));

    Front front = new ArrayFront(1, 1) ;

    FrontUtils.distanceToClosestPoint(null, front) ;
  }

  @Test
  public void shouldDistanceToClosestPointRaiseAnExceptionIfTheFrontIsEmpty() {
    exception.expect(JMetalException.class);
    exception.expectMessage(containsString("The front is empty"));

    Point point = new ArrayPoint(1) ;
    Front front = new ArrayFront(0, 1) ;

    FrontUtils.distanceToClosestPoint(point, front) ;
  }

  @Test
  public void shouldDistanceToClosestPointReturnMaxZeroIfThePointIsTheOnlyPointInTheFront() {
    int numberOfDimensions = 2 ;
    int numberOfPoints = 1 ;

    Point point = new ArrayPoint(numberOfDimensions) ;
    point.setDimensionValue(0, 2);
    point.setDimensionValue(1, 4);

    Front front = new ArrayFront(numberOfPoints, numberOfDimensions) ;
    front.setPoint(0, point);

    assertEquals(0.0, FrontUtils.distanceToClosestPoint(point, front), EPSILON);
  }

  @Test
  public void shouldDistanceToClosestPointReturnTheCorrectValueIfTheFrontHasHasOnePoint() {
    int numberOfDimensions = 2 ;
    int numberOfPoints = 1 ;

    Point point1 = new ArrayPoint(numberOfDimensions) ;
    point1.setDimensionValue(0, 2);
    point1.setDimensionValue(1, 4);

    Point point2 = new ArrayPoint(numberOfDimensions) ;
    point2.setDimensionValue(0, 6);
    point2.setDimensionValue(1, 7);

    Front front = new ArrayFront(numberOfPoints, numberOfDimensions) ;
    front.setPoint(0, point1);

    assertEquals(5.0, FrontUtils.distanceToClosestPoint(point2, front), EPSILON);
  }

  /**
   * Case A: the front has two points and one of them is the point passed as a parameter
   */
  @Test
  public void shouldDistanceToNearestPointClosestTheCorrectValueIfTheFrontHasTwoPointsCaseA() {
    int numberOfDimensions = 2 ;
    int numberOfPoints = 2 ;

    Point point1 = new ArrayPoint(numberOfDimensions) ;
    point1.setDimensionValue(0, 2);
    point1.setDimensionValue(1, 4);

    Point point2 = new ArrayPoint(numberOfDimensions) ;
    point2.setDimensionValue(0, 6);
    point2.setDimensionValue(1, 7);

    Front front = new ArrayFront(numberOfPoints, numberOfDimensions) ;
    front.setPoint(0, point1);
    front.setPoint(1, point2);

    assertEquals(0.0, FrontUtils.distanceToClosestPoint(point1, front), EPSILON);
  }

  /**
   * Case B: the front has two points and none of them is the point passed as a parameter. The
   * dimensions of the points are ordered
   */
  @Test
  public void shouldDistanceToNearestPointClosestTheCorrectValueIfTheFrontHasTwoPointsCaseB() {
    int numberOfDimensions = 2 ;
    int numberOfPoints = 2 ;

    Point point1 = new ArrayPoint(numberOfDimensions) ;
    point1.setDimensionValue(0, 2);
    point1.setDimensionValue(1, 4);

    Point point2 = new ArrayPoint(numberOfDimensions) ;
    point2.setDimensionValue(0, 6);
    point2.setDimensionValue(1, 7);

    Front front = new ArrayFront(numberOfPoints, numberOfDimensions) ;
    front.setPoint(0, point1);
    front.setPoint(1, point2);

    Point point3 = new ArrayPoint(numberOfDimensions) ;
    point3.setDimensionValue(0, 5);
    point3.setDimensionValue(1, 1);

    assertEquals(Math.sqrt(18), FrontUtils.distanceToClosestPoint(point3, front), EPSILON);
  }

  /**
   * Case B: the front has two points and none of them is the point passed as a parameter. The
   * dimensions of the points are not ordered
   */
  @Test
  public void shouldDistanceToNearestPointClosestTheCorrectValueIfTheFrontHasTwoPointsCaseC() {
    int numberOfDimensions = 2 ;
    int numberOfPoints = 2 ;

    Point point1 = new ArrayPoint(numberOfDimensions) ;
    point1.setDimensionValue(0, 6);
    point1.setDimensionValue(1, 7);

    Point point2 = new ArrayPoint(numberOfDimensions) ;
    point2.setDimensionValue(0, 2);
    point2.setDimensionValue(1, 4);

    Front front = new ArrayFront(numberOfPoints, numberOfDimensions) ;
    front.setPoint(0, point1);
    front.setPoint(1, point2);

    Point point3 = new ArrayPoint(numberOfDimensions) ;
    point3.setDimensionValue(0, 5);
    point3.setDimensionValue(1, 1);

    assertEquals(Math.sqrt(18), FrontUtils.distanceToClosestPoint(point3, front), EPSILON);
  }

  @Test
  public void shouldGetInvertedFrontRaiseAnExceptionIfTheFrontIsNull() {
    exception.expect(JMetalException.class);
    exception.expectMessage(containsString("The front is null"));

    FrontUtils.getInvertedFront(null) ;
  }

  @Test
  public void shouldGetInvertedFrontRaiseAnExceptionIfTheFrontIsEmpty() {
    exception.expect(JMetalException.class);
    exception.expectMessage(containsString("The front is empty"));

    Front front = new ArrayFront(0, 1) ;

    FrontUtils.getInvertedFront(front) ;
  }

  /**
   * Case A: the front has the point [0.5, 0.5]. The inverted front is the same
   */
  @Test
  public void shouldGetInvertedFrontReturnTheCorrectFrontIfItComposedOfOnePointCaseA() {
    int numberOfDimensions = 2 ;
    int numberOfPoints = 1 ;

    Point point1 = new ArrayPoint(numberOfDimensions) ;
    point1.setDimensionValue(0, 0.5);
    point1.setDimensionValue(1, 0.5);

    Front front = new ArrayFront(numberOfPoints, numberOfDimensions) ;
    front.setPoint(0, point1);

    Front newFront = FrontUtils.getInvertedFront(front) ;

    assertEquals(0.5, newFront.getPoint(0).getDimensionValue(0), EPSILON);
    assertEquals(0.5, newFront.getPoint(0).getDimensionValue(1), EPSILON);
  }

  /**
   * Case B: the front has the point [0.0, 1.0]. The inverted front is [1.0, 0.0]
   */
  @Test
  public void shouldGetInvertedFrontReturnTheCorrectFrontIfItComposedOfOnePointCaseB() {
    int numberOfDimensions = 2 ;
    int numberOfPoints = 1 ;

    Point point1 = new ArrayPoint(numberOfDimensions) ;
    point1.setDimensionValue(0, 0.0);
    point1.setDimensionValue(1, 1.0);

    Front front = new ArrayFront(numberOfPoints, numberOfDimensions) ;
    front.setPoint(0, point1);

    Front newFront = FrontUtils.getInvertedFront(front) ;

    assertEquals(1.0, newFront.getPoint(0).getDimensionValue(0), EPSILON);
    assertEquals(0.0, newFront.getPoint(0).getDimensionValue(1), EPSILON);
  }

  /**
   * Case C: the front has the point [3.0, -2.0]. The inverted front is [0.0, 1.0]
   */
  @Test
  public void shouldGetInvertedFrontReturnTheCorrectFrontIfItComposedOfOnePointCaseC() {
    int numberOfDimensions = 2 ;
    int numberOfPoints = 1 ;

    Point point1 = new ArrayPoint(numberOfDimensions) ;
    point1.setDimensionValue(0, 3.0);
    point1.setDimensionValue(1, -2.0);

    Front front = new ArrayFront(numberOfPoints, numberOfDimensions) ;
    front.setPoint(0, point1);

    Front newFront = FrontUtils.getInvertedFront(front) ;

    assertEquals(0.0, newFront.getPoint(0).getDimensionValue(0), EPSILON);
    assertEquals(1.0, newFront.getPoint(0).getDimensionValue(1), EPSILON);
  }

  /**
   * The front has the points [0.1, 0.9], [0.2, 0.8], [0.3, 0.7], [0.4, 0.6].
   * The inverted front is [0.9, 0.1], [0.8, 0.2], [0.7, 0.3], [0.6, 0.4]
   */
  @Test
  public void shouldGetInvertedFrontReturnTheCorrectFrontIfItComposedOfFourPoints() {
    int numberOfDimensions = 2 ;
    int numberOfPoints = 4 ;

    Point point1 = new ArrayPoint(numberOfDimensions) ;
    point1.setDimensionValue(0, 0.1);
    point1.setDimensionValue(1, 0.9);
    Point point2 = new ArrayPoint(numberOfDimensions) ;
    point2.setDimensionValue(0, 0.2);
    point2.setDimensionValue(1, 0.8);
    Point point3 = new ArrayPoint(numberOfDimensions) ;
    point3.setDimensionValue(0, 0.3);
    point3.setDimensionValue(1, 0.7);
    Point point4 = new ArrayPoint(numberOfDimensions) ;
    point4.setDimensionValue(0, 0.4);
    point4.setDimensionValue(1, 0.6);

    Front front = new ArrayFront(numberOfPoints, numberOfDimensions) ;
    front.setPoint(0, point1);
    front.setPoint(1, point2);
    front.setPoint(2, point3);
    front.setPoint(3, point4);

    Front newFront = FrontUtils.getInvertedFront(front) ;

    assertEquals(0.9, newFront.getPoint(0).getDimensionValue(0), EPSILON);
    assertEquals(0.1, newFront.getPoint(0).getDimensionValue(1), EPSILON);
    assertEquals(0.8, newFront.getPoint(1).getDimensionValue(0), EPSILON);
    assertEquals(0.2, newFront.getPoint(1).getDimensionValue(1), EPSILON);
    assertEquals(0.7, newFront.getPoint(2).getDimensionValue(0), EPSILON);
    assertEquals(0.3, newFront.getPoint(2).getDimensionValue(1), EPSILON);
    assertEquals(0.6, newFront.getPoint(3).getDimensionValue(0), EPSILON);
    assertEquals(0.4, newFront.getPoint(3).getDimensionValue(1), EPSILON);
  }

  @Test
  public void shouldConvertFrontToArrayRaiseAnExceptionIfTheFrontIsNull() {
    exception.expect(JMetalException.class);
    exception.expectMessage(containsString("The front is null"));

    FrontUtils.convertFrontToArray(null) ;
  }

  @Test
  public void shouldConvertFrontToArrayReturnAnEmptyArrayIfTheFrontIsEmpty() {
    int numberOfDimensions = 2 ;
    int numberOfPoints = 0 ;

    Front front = new ArrayFront(numberOfPoints, numberOfDimensions) ;

    double[][] doubleFront ;
    doubleFront = FrontUtils.convertFrontToArray(front) ;

    assertEquals(0, doubleFront.length);
  }

  /**
   * Case A: The front has one point
   */
  @Test
  public void shouldConvertFrontToArrayReturnTheCorrectArrayCaseA() {
    int numberOfDimensions = 2 ;
    int numberOfPoints = 1 ;

    Front front = new ArrayFront(numberOfPoints, numberOfDimensions) ;

    Point point1 = new ArrayPoint(numberOfDimensions) ;
    point1.setDimensionValue(0, 0.1);
    point1.setDimensionValue(1, 0.9);

    front.setPoint(0, point1);

    double[][] doubleFront ;
    doubleFront = FrontUtils.convertFrontToArray(front) ;

    assertEquals(1, doubleFront.length);
    assertEquals(0.1, doubleFront[0][0], EPSILON);
    assertEquals(0.9, doubleFront[0][1], EPSILON);
  }

  /**
   * Case A: The front has one three points
   */
  @Test
  public void shouldConvertFrontToArrayReturnTheCorrectArrayCaseB() {
    int numberOfDimensions = 4 ;
    int numberOfPoints = 3 ;

    Front front = new ArrayFront(numberOfPoints, numberOfDimensions) ;

    Point point1 = new ArrayPoint(numberOfDimensions) ;
    point1.setDimensionValue(0, 0.1);
    point1.setDimensionValue(1, 0.9);
    point1.setDimensionValue(2, -2.23);
    point1.setDimensionValue(3, 0.0);
    Point point2 = new ArrayPoint(numberOfDimensions) ;
    point2.setDimensionValue(0, 0.2);
    point2.setDimensionValue(1, 0.8);
    point2.setDimensionValue(2, 25.08);
    point2.setDimensionValue(3, -232420.8);
    Point point3 = new ArrayPoint(numberOfDimensions) ;
    point3.setDimensionValue(0, 0.3);
    point3.setDimensionValue(1, 0.7);
    point3.setDimensionValue(2, 32342);
    point3.setDimensionValue(3, 0.4E+23);

    front.setPoint(0, point1);
    front.setPoint(1, point2);
    front.setPoint(2, point3);

    double[][] doubleFront ;
    doubleFront = FrontUtils.convertFrontToArray(front) ;

    assertEquals(3, doubleFront.length);
    assertEquals(0.1, doubleFront[0][0], EPSILON);
    assertEquals(0.8, doubleFront[1][1], EPSILON);
    assertEquals(32342, doubleFront[2][2], EPSILON);
    assertEquals(.4E+23, doubleFront[2][3], EPSILON);
  }

  @Test
  public void shouldConvertFrontToSolutionListRaiseAnExceptionIfTheFrontIsNull() {
    exception.expect(JMetalException.class);
    exception.expectMessage(containsString("The front is null"));

    FrontUtils.convertFrontToArray(null) ;
  }

  @Test
  public void shouldConvertFrontToSolutionListReturnAnEmptyListIfTheFrontIsEmpty() {
    int numberOfDimensions = 2 ;
    int numberOfPoints = 0 ;

    Front front = new ArrayFront(numberOfPoints, numberOfDimensions) ;

    List<PointSolution> list ;
    list = FrontUtils.convertFrontToSolutionList(front) ;

    assertEquals(0, list.size());
  }

  /**
   * Case A: The front has one point
   */
  @Test
  public void shouldConvertFrontToSolutionListReturnTheCorrectListCaseA() {
    int numberOfDimensions = 2 ;
    int numberOfPoints = 1 ;

    Front front = new ArrayFront(numberOfPoints, numberOfDimensions) ;

    Point point1 = new ArrayPoint(numberOfDimensions) ;
    point1.setDimensionValue(0, 0.1);
    point1.setDimensionValue(1, 0.9);

    front.setPoint(0, point1);

    List<PointSolution> list ;
    list = FrontUtils.convertFrontToSolutionList(front) ;

    assertEquals(1, list.size());
    assertEquals(0.1, list.get(0).getObjective(0), EPSILON);
    assertEquals(0.9, list.get(0).getObjective(1), EPSILON);
  }

  /**
   * Case A: The front has one three points
   */
  @Test
  public void shouldConvertFrontToSolutionListReturnTheCorrectListCaseB() {
    int numberOfDimensions = 4 ;
    int numberOfPoints = 3 ;

    Front front = new ArrayFront(numberOfPoints, numberOfDimensions) ;

    Point point1 = new ArrayPoint(numberOfDimensions) ;
    point1.setDimensionValue(0, 0.1);
    point1.setDimensionValue(1, 0.9);
    point1.setDimensionValue(2, -2.23);
    point1.setDimensionValue(3, 0.0);
    Point point2 = new ArrayPoint(numberOfDimensions) ;
    point2.setDimensionValue(0, 0.2);
    point2.setDimensionValue(1, 0.8);
    point2.setDimensionValue(2, 25.08);
    point2.setDimensionValue(3, -232420.8);
    Point point3 = new ArrayPoint(numberOfDimensions) ;
    point3.setDimensionValue(0, 0.3);
    point3.setDimensionValue(1, 0.7);
    point3.setDimensionValue(2, 32342);
    point3.setDimensionValue(3, 0.4E+23);

    front.setPoint(0, point1);
    front.setPoint(1, point2);
    front.setPoint(2, point3);

    List<PointSolution> list ;
    list = FrontUtils.convertFrontToSolutionList(front) ;

    assertEquals(3, list.size());
    assertEquals(0.1, list.get(0).getObjective(0), EPSILON);
    assertEquals(0.8, list.get(1).getObjective(1), EPSILON);
    assertEquals(32342, list.get(2).getObjective(2), EPSILON);
    assertEquals(.4E+23, list.get(2).getObjective(3), EPSILON);
  }
}
