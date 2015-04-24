package org.uma.jmetal.qualityindicator.hypervolume.impl;

import org.uma.jmetal.qualityindicator.hypervolume.Hypervolume;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.criteria.Criteria;
import org.uma.jmetal.util.front.Front;
import org.uma.jmetal.util.naming.impl.SimpleDescribedEntity;

import java.util.List;

/**
 * Created by ajnebro on 19/4/15.
 */
public class PISAHypervolume extends SimpleDescribedEntity implements Hypervolume {
  public PISAHypervolume() {
    super("HV", "Hypervolume quality indicator. PISA based implementation") ;
  }

  @Override 
  public double[] computeHypervolumeContribution(List<Criteria> front) {
    return new double[0];
  }

  @Override public double execute(Front frontA, Front frontB) {
    return 0;
  }

  @Override
  public double execute(List<? extends Solution> frontA, List<? extends Solution> frontB) {
    return 0;
  }

  @Override public String getName() {
    return super.getName() ;
  }

  @Override public String getDescription() {
    return super.getDescription() ;
  }

}
