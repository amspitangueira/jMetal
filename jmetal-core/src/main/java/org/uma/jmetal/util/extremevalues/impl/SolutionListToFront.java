package org.uma.jmetal.util.extremevalues.impl;

import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.criteria.Criteria;
import org.uma.jmetal.util.criteria.impl.ArrayCriteria;

import java.util.ArrayList;
import java.util.List;

public class SolutionListToFront {
	List<Criteria> extract(List<? extends Solution> list) {
		List<Criteria> front = new ArrayList<Criteria>();
		for (Solution s : list)
			front.add(new ArrayCriteria(s));
		return front;
	}
}
