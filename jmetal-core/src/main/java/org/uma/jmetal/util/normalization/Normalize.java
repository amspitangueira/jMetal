package org.uma.jmetal.util.normalization;

import java.util.List;

public interface Normalize<T> {
	List<T>	normalize(List<T> target, List<Double> max, List<Double> min);
}
