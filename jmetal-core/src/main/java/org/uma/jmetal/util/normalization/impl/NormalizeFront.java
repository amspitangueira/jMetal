package org.uma.jmetal.util.normalization.impl;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.criteria.Criteria;
import org.uma.jmetal.util.criteria.impl.ArrayCriteria;
import org.uma.jmetal.util.normalization.Normalize;

public class NormalizeFront implements Normalize<Criteria>{

	@Override
	public List<Criteria> normalize(List<Criteria> target, List<Double> max, List<Double> min) {
		  if (target == null) {
	        throw new JMetalException("The front is null") ;
	      } else if (target.size() == 0) {
	        throw new JMetalException("The front is empty") ;
	      } else if (max == null) {
	        throw new JMetalException("The maximum values array is null") ;
	      } else if (min == null) {
	        throw new JMetalException("The minimum values array is null") ;
	      } else if (max.size() != min.size()) {
	        throw new JMetalException("The length of the maximum array (" + max.size() + ") "
	            + "is different from the length of the minimum array (" + min.size()+")") ;
	      } else if (target.get(0).getNumberOfDimensions() != max.size()) {
	        throw new JMetalException("The length of the point dimensions ("
	            + target.get(0).getNumberOfDimensions() + ") "
	            + "is different from the length of the maximum array (" + max.size()+")") ;
	      }

	      List<Criteria> normalizedFront = new ArrayList<Criteria>(target.size()) ;
	      for (Criteria c : target)
	    	  normalizedFront.add(new ArrayCriteria(c));
	      
	      int numberOfPointDimensions = target.get(0).getNumberOfDimensions() ;

	      for (int i = 0; i < target.size(); i++) {
	        for (int j = 0; j < numberOfPointDimensions; j++) {
	          if ((max.get(j) - min.get(j)) == 0) {
	            throw new JMetalException("Maximum and minimum values of index " + j + " "
	                + "are the same: " + max.get(j));
	          }

	          normalizedFront.get(i).setDimensionValue(j, (target.get(i).getDimensionValue(j)
	              - min.get(j)) / (max.get(j) - min.get(j)));
	        }
	      }
	      return normalizedFront;
	}

}
