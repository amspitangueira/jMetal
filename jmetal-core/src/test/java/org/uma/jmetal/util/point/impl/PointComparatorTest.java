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

package org.uma.jmetal.util.point.impl;

import org.junit.Test;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.criteria.Criteria;
import org.uma.jmetal.util.criteria.impl.ArrayCriteria;
import org.uma.jmetal.util.criteria.impl.PointComparator;

import static org.junit.Assert.assertEquals;

/**
 * @author Antonio J. Nebro
 * @version 1.0
 */
public class PointComparatorTest {
  private Criteria point1 ;
  private Criteria point2 ;

  private PointComparator comparator ;

  @Test(expected = JMetalException.class)
  public void shouldFirstPointToCompareEqualsToNullRaiseAnException() throws Exception {
    comparator = new PointComparator(true) ;

    point2 = new ArrayCriteria(4) ;
    comparator.compare(null, point2);
  }

  @Test (expected = JMetalException.class)
  public void shouldSecondPointToCompareEqualsToNullRaiseAnException() throws Exception {
    comparator = new PointComparator(true) ;

    point1 = new ArrayCriteria(4) ;
    comparator.compare(point1, null);
  }

  @Test (expected = JMetalException.class)
  public void shouldComparingDifferentLengthPointsRaiseAnException() throws Exception {
    point1 = new ArrayCriteria(2) ;
    point2 = new ArrayCriteria(3) ;

    comparator = new PointComparator(true) ;
    comparator.compare(point1, point2);
  }

  @Test
  public void shouldCompareReturnMinusOneIfTheFirstPointIsBetterThanTheSecondOneWhenMaximizing() {
    point1 = new ArrayCriteria(2) ;
    point1.setDimensionValue(0, 1.0);
    point1.setDimensionValue(1, 3.0);

    point2 = new ArrayCriteria(2) ;
    point2.setDimensionValue(0, 1.0);
    point2.setDimensionValue(1, 2.0);

    boolean maximization = true ;
    comparator = new PointComparator(maximization) ;

    assertEquals(-1, comparator.compare(point1, point2)) ;
  }

  @Test
  public void shouldCompareReturnOneIfTheSecondPointIsBetterThanTheFirstOneWhenMaximizing() {
    point1 = new ArrayCriteria(2) ;
    point1.setDimensionValue(0, 1.0);
    point1.setDimensionValue(1, 3.0);

    point2 = new ArrayCriteria(2) ;
    point2.setDimensionValue(0, 1.0);
    point2.setDimensionValue(1, 5.0);

    boolean maximization = true ;
    comparator = new PointComparator(maximization) ;

    assertEquals(1, comparator.compare(point1, point2)) ;
  }

  @Test
  public void shouldCompareBetterReturnZeroIfBothPointsAreEqualWhenMaximizing() {
    point1 = new ArrayCriteria(2) ;
    point1.setDimensionValue(0, 1.0);
    point1.setDimensionValue(1, 3.0);

    point2 = new ArrayCriteria(2) ;
    point2.setDimensionValue(0, 1.0);
    point2.setDimensionValue(1, 3.0);

    boolean maximization = true ;
    comparator = new PointComparator(maximization) ;

    assertEquals(0, comparator.compare(point1, point2)) ;
  }

  @Test
  public void shouldCompareBetterReturnZeroIfBothPointsAreEqualWhenMinimizing() {
    point1 = new ArrayCriteria(2) ;
    point1.setDimensionValue(0, 1.0);
    point1.setDimensionValue(1, 3.0);

    point2 = new ArrayCriteria(2) ;
    point2.setDimensionValue(0, 1.0);
    point2.setDimensionValue(1, 3.0);

    boolean maximization = false ;
    comparator = new PointComparator(maximization) ;

    assertEquals(0, comparator.compare(point1, point2)) ;
  }
}
