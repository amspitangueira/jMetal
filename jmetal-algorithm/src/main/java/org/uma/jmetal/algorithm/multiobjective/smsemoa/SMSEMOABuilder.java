package org.uma.jmetal.algorithm.multiobjective.smsemoa;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.operator.Operator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.util.AlgorithmBuilder;

/**
 * Created by ajnebro on 17/4/15.
 */
public class SMSEMOABuilder implements AlgorithmBuilder {
  private static final double DEFAULT_OFFSET = 100.0 ;

  protected Problem problem;

  protected int populationSize;
  protected int maxEvaluations;

  protected Operator mutationOperator;
  protected Operator crossoverOperator;
  protected Operator selectionOperator;

  protected double offset ;

  public SMSEMOABuilder(Problem problem) {
    this.problem = problem ;
    this.offset = DEFAULT_OFFSET ;
  }

  public SMSEMOABuilder setPopulationSize(int populationSize) {
    this.populationSize = populationSize ;

    return this ;
  }

  public SMSEMOABuilder setMaxEvaluations(int maxEvaluations) {
    this.maxEvaluations = maxEvaluations ;

    return this ;
  }

  public SMSEMOABuilder setCrossover(Operator crossover) {
    crossoverOperator = crossover ;

    return this ;
  }

  public SMSEMOABuilder setMutation(Operator mutation) {
    mutationOperator = mutation ;

    return this ;
  }

  public SMSEMOABuilder setSelection(Operator selection) {
    selectionOperator = selection ;

    return this ;
  }

  public SMSEMOABuilder setOffset(double offset) {
    this.offset = offset ;

    return this ;
  }
/*
  public Algorithm build(String smsemoaVariant) {
    /*
    SMSEMOATemplate algorithm  ;
    if ("SMSEMOA".equals(smsemoaVariant)) {
      algorithm = new SMSEMOA(this);
    } else if ("FastSMSEMOA".equals(smsemoaVariant)) {
      algorithm =  new FastSMSEMOA(this) ;
    } else {
      throw new JMetalException(smsemoaVariant + " variant unknown") ;
    }

    return null ;
  }
*/
  @Override public Algorithm build() {
    return null;
  }
}
