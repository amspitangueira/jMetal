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

package org.uma.jmetal.util.criteria.impl;

import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.criteria.Criteria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Class representing a point (i.e, an array of double values)
 *
 * @author Antonio J. Nebro
 * @author Juan J. Durillo (modifications to Criteria)
 * @version 1.0
 */
public class ArrayCriteria implements Criteria {
  private ArrayList<Double> criteria;
  private int dimensions;

  /**
   * Constructor
   *
   * @param dimensions Dimensions of the point

   */
  public ArrayCriteria(int dimensions) {
	this.dimensions = dimensions;
    criteria = new ArrayList<Double>(dimensions);
  
    //Initially contains NaN values
    for (int i = 0; i < this.dimensions; i++)
    	criteria.add(Double.NaN);
  
  }


  /**
   * Copy constructor
   *
   * @param point
   */
  public ArrayCriteria(Criteria point) {
    if (point == null) {
      throw new JMetalException("The point is null") ;
    }

    this.criteria = new ArrayList<Double>(point.getNumberOfDimensions());
    this.dimensions  = point.getNumberOfDimensions();
    
    for (Double d : point) 
    	this.add(d);
  }

  /**
   * Constructor from a solution
   *
   * @param solution
   */
  public ArrayCriteria(Solution solution) {
    if (solution == null) {
      throw new JMetalException("The solution is null") ;
    }

    this.dimensions = solution.getNumberOfObjectives();
    criteria = new ArrayList<Double>(this.dimensions);

    for (int i = 0; i < dimensions; i++) {
      this.add(solution.getObjective(i));
    }
  }

  /**
   * Constructor from an array of double values
   *
   * @param point
   */
  public ArrayCriteria(double[] point) {
    if (point == null) {
      throw new JMetalException("The array of values is null") ;
    }

    this.dimensions = point.length;
    this.criteria = new ArrayList<Double>(this.dimensions);
    for (double d : point)
    	this.add(d);
  }

  @Override
  public int getNumberOfDimensions() {
    return this.dimensions;
  }

  @Override
  public List<Double> getValues() {
    return criteria;
  }

  @Override
  public double getDimensionValue(int index) {
    if ((index < 0) || (index >= this.dimensions)) {
      throw new JMetalException("Index value invalid: " + index +
          ". The point length is: " + this.dimensions) ;
    }
    return this.get(index) ;
  }

  @Override
  public void setDimensionValue(int index, double value) {
    if ((index < 0) || (index >= this.dimensions)) {
      throw new JMetalException("Index value invalid: " + index +
          ". The point length is: " + this.dimensions) ;
    }
    this.set(index,value) ;
  }

  @Override
  public String toString() {
    String result = "";
    for (double anObjectives_ : criteria) {
      result += anObjectives_ + " ";
    }

    return result;
  }

  @Override public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    ArrayCriteria that = (ArrayCriteria) o;

    if (!criteria.equals(that.criteria))
      return false;

    return true;
  }

  @Override public int hashCode() {
    return criteria.hashCode();
  }


@Override
public boolean add(Double arg0) {
	return this.criteria.add(arg0);
}


@Override
public void add(int arg0, Double arg1) {
	this.criteria.add(arg0,arg1);
	
}


@Override
public boolean addAll(Collection<? extends Double> arg0) {
	return this.criteria.addAll(arg0);
}


@Override
public boolean addAll(int arg0, Collection<? extends Double> arg1) {
	return this.criteria.addAll(arg0,arg1);
}


@Override
public void clear() {
	this.criteria.clear();
}


@Override
public boolean contains(Object arg0) {
	return this.criteria.contains(arg0);
}


@Override
public boolean containsAll(Collection<?> arg0) {
	return criteria.containsAll(arg0);
}


@Override
public Double get(int arg0) {
	return this.criteria.get(arg0);
}


@Override
public int indexOf(Object arg0) {
	return this.criteria.indexOf(arg0);
}
@Override
public boolean isEmpty() {
	return this.criteria.isEmpty();
}


@Override
public Iterator<Double> iterator() {
	return this.criteria.iterator();
}


@Override
public int lastIndexOf(Object arg0) {
	return this.criteria.lastIndexOf(arg0);
}


@Override
public ListIterator<Double> listIterator() {
	return this.criteria.listIterator();
}


@Override
public ListIterator<Double> listIterator(int arg0) {
	return this.listIterator(arg0);
}


@Override
public boolean remove(Object arg0) {
	return this.criteria.remove(arg0);
}


@Override
public Double remove(int arg0) {
	return this.criteria.remove(arg0);
}


@Override
public boolean removeAll(Collection<?> arg0) {
	return this.criteria.removeAll(arg0);
}


@Override
public boolean retainAll(Collection<?> arg0) {
	return this.criteria.retainAll(arg0);
}


@Override
public Double set(int arg0, Double arg1) {
	return this.criteria.set(arg0,arg1);
}


@Override
public int size() {
	return this.criteria.size();
}


@Override
public List<Double> subList(int arg0, int arg1) {
	return this.criteria.subList(arg0,arg1);
}


@Override
public Object[] toArray() {
	return this.criteria.toArray();
}


@Override
public <T> T[] toArray(T[] arg0) {
	return this.criteria.toArray(arg0);
}
}
