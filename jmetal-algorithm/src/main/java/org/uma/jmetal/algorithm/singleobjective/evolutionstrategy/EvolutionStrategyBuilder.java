package org.uma.jmetal.algorithm.singleobjective.evolutionstrategy;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.AlgorithmBuilder;
import org.uma.jmetal.util.JMetalException;

/**
 * Created by ajnebro on 10/3/15.
 */
public class EvolutionStrategyBuilder<S extends Solution> implements AlgorithmBuilder {
  public enum EvolutionStrategyVariant {ELITIST, NON_ELITIST}

  private Problem<S> problem;
  private int mu;
  private int lambda;
  private int maxEvaluations;
  private MutationOperator<S> mutation;
  private EvolutionStrategyVariant variant ;

  public EvolutionStrategyBuilder(Problem<S> problem, MutationOperator<S> mutationOperator,
      EvolutionStrategyVariant variant) {
    this.problem = problem;
    this.mu = 1;
    this.lambda = 10;
    this.maxEvaluations = 250000;
    this.mutation = mutationOperator;
    this.variant = variant ;
  }

  public EvolutionStrategyBuilder setMu(int mu) {
    this.mu = mu;

    return this;
  }

  public EvolutionStrategyBuilder setLambda(int lambda) {
    this.lambda = lambda;

    return this;
  }

  public EvolutionStrategyBuilder setMaxEvaluations(int maxEvaluations) {
    this.maxEvaluations = maxEvaluations;

    return this;
  }

  @Override public Algorithm<S> build() {
    if (variant == EvolutionStrategyVariant.ELITIST) {
      return new ElitistEvolutionStrategy<S>(problem, mu, lambda, maxEvaluations, mutation);
    } else if (variant == EvolutionStrategyVariant.NON_ELITIST) {
      return new NonElitistEvolutionStrategy<S>(problem, mu, lambda, maxEvaluations, mutation);
    } else {
      throw new JMetalException("Unknown variant: " + variant) ;
    }
  }

  /* Getters */
  public int getMu() {
    return mu;
  }

  public int getLambda() {
    return lambda;
  }

  public int getMaxEvaluations() {
    return maxEvaluations;
  }

  public MutationOperator getMutation() {
    return mutation;
  }
}
