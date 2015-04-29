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

import org.junit.Before;
import org.junit.Test;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.criteria.Criteria;
import org.uma.jmetal.util.criteria.impl.ArrayCriteria;
import org.uma.jmetal.util.criteria.impl.LexicographicalPointComparator;

import static org.junit.Assert.assertEquals;

/**
 * @author Antonio J. Nebro
 * @version 1.0
 */
public class LexicographicalPointComparatorTest {
  private static final double EPSILON = 0.0000000000001 ;

  private Criteria point1 ;
  private Criteria point2 ;

  private LexicographicalPointComparator comparator ;

  @Before
  public void startup() {
    comparator = new LexicographicalPointComparator() ;
  }

  @Test(expected = JMetalException.class)
  public void shouldFirstPointToCompareEqualsToNullRaiseAnException() {
    point2 = new ArrayCriteria(2) ;

    comparator.compare(null, point2);
  }

  @Test (expected = JMetalException.class)
  public void shouldSecondPointToCompareEqualsToNullRaiseAnException() {
    point1 = new ArrayCriteria(2) ;

    comparator.compare(point1, null);
  }

  @Test
  public void shouldCompareIdenticalPointsReturnZero() {
    point1 = new ArrayCriteria(2) ;
    point1.setDimensionValue(0, 1.0);
    point1.setDimensionValue(1, 3.0);

    point2 = new ArrayCriteria(2) ;
    point2.setDimensionValue(0, 1.0);
    point2.setDimensionValue(1, 3.0);

    assertEquals(0, comparator.compare(point1, point2));
  }

  @Test
  public void shouldCompareIdenticalPointsButTheFirstValueReturnMinus1() {
    point1 = new ArrayCriteria(4) ;
    point1.setDimensionValue(0, 1.0);
    point1.setDimensionValue(1, 0.0);
    point1.setDimensionValue(2, 5.0);
    point1.setDimensionValue(3, 7.0);

    point2 = new ArrayCriteria(4) ;
    point2.setDimensionValue(0, -1.0);
    point2.setDimensionValue(0, 0.0);
    point2.setDimensionValue(0, 5.0);
    point2.setDimensionValue(0, 7.0);

    assertEquals(-1, comparator.compare(point1, point2));
  }

  @Test
  public void shouldCompareIdenticalPointsButTheFirstValueReturnPlus1() {
    point1 = new ArrayCriteria(4) ;
    point1.setDimensionValue(0, 1.0);
    point1.setDimensionValue(1, 0.0);
    point1.setDimensionValue(2, 5.0);
    point1.setDimensionValue(3, 7.0);

    point2 = new ArrayCriteria(4) ;
    point2.setDimensionValue(0, -1.0);
    point2.setDimensionValue(0, 0.0);
    point2.setDimensionValue(0, 5.0);
    point2.setDimensionValue(0, 7.0);

    assertEquals(1, comparator.compare(point2, point1));
  }

  @Test
  public void shouldCompareIdenticalPointsButTheLastValueReturnMinus1() {
    point1 = new ArrayCriteria(4) ;
    point1.setDimensionValue(0, 1.0);
    point1.setDimensionValue(1, 0.0);
    point1.setDimensionValue(2, 5.0);
    point1.setDimensionValue(3, 0.0);

    point2 = new ArrayCriteria(4) ;
    point2.setDimensionValue(0, 1.0);
    point2.setDimensionValue(0, 0.0);
    point2.setDimensionValue(0, 5.0);
    point2.setDimensionValue(0, 7.0);

    assertEquals(-1, comparator.compare(point1, point2));
  }

  @Test
  public void shouldCompareIdenticalPointsButTheLastValueReturnPlus1() {
    point1 = new ArrayCriteria(4) ;
    point1.setDimensionValue(0, 1.0);
    point1.setDimensionValue(1, 0.0);
    point1.setDimensionValue(2, 5.0);
    point1.setDimensionValue(3, 7.0);

    point2 = new ArrayCriteria(4) ;
    point2.setDimensionValue(0, 1.0);
    point2.setDimensionValue(0, 0.0);
    point2.setDimensionValue(0, 5.0);
    point2.setDimensionValue(0, 0.0);

    assertEquals(1, comparator.compare(point1, point2));
  }

  @Test
  public void shouldCompareEmptyPointsReturnZero() {
    point1 = new ArrayCriteria(0) ;
    point2 = new ArrayCriteria(0) ;

    assertEquals(0, comparator.compare(point1, point2));
  }

  @Test
  public void shouldCompareDifferentLengthPointsReturnTheCorrectValue() {
    point1 = new ArrayCriteria(4) ;
    point1.setDimensionValue(0, 1.0);
    point1.setDimensionValue(1, 0.0);
    point1.setDimensionValue(2, 5.0);
    point1.setDimensionValue(3, 7.0);

    point2 = new ArrayCriteria(3) ;
    point2.setDimensionValue(0, 1.0);
    point2.setDimensionValue(1, 0.0);
    point2.setDimensionValue(2, 5.0);

    assertEquals(0, comparator.compare(point1, point2));
  }
}
