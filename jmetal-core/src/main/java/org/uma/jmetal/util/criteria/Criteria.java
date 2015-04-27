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
//

package org.uma.jmetal.util.criteria;

import java.util.List;

/**
 * Interface representing a point
 *
 * @author Antonio J. Nebro
 * @author Juan J. Durillo (Modification from Point to Criteria)
 * @version 1.0
 */
public interface Criteria extends List<Double> {
  public int getNumberOfDimensions();
  public List<Double> getValues() ;
  public double getDimensionValue(int index) ;
  public void setDimensionValue(int index, double value) ;
}
