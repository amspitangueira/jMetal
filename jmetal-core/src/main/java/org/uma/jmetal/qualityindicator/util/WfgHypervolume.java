package org.uma.jmetal.qualityindicator.util;

import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.JMetalLogger;
import org.uma.jmetal.util.criteria.Criteria;
import org.uma.jmetal.util.criteria.impl.ArrayCriteria;
import org.uma.jmetal.util.criteria.impl.PointComparator;
import org.uma.jmetal.util.fileinput.impl.FileInputCriteria;
import org.uma.jmetal.util.front.Front;
import org.uma.jmetal.util.solutionattribute.impl.HypervolumeContribution;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Created by ajnebro on 2/2/15.
 */
public class WfgHypervolume {
  static final int OPT = 2;
  List<Criteria> fs[];
  private Criteria referencePoint;
  boolean maximizing;
  private int currentDeep;
  private int currentDimension;
  private int maxNumberOfPoints;
  private int maxNumberOfObjectives;
  private Comparator<Criteria> pointComparator;

  public WfgHypervolume(int dimension, int maxNumberOfPoints) {
    referencePoint = new ArrayCriteria(dimension);
    for (int i = 0; i < dimension; i++) {
      referencePoint.setDimensionValue(i, 0.0);
    }

    maximizing = false;
    currentDeep = 0;
    currentDimension = dimension;
    this.maxNumberOfPoints = maxNumberOfPoints;
    maxNumberOfObjectives = dimension;
    pointComparator = new PointComparator(true);

    int maxd = this.maxNumberOfPoints - (OPT / 2 + 1);    
    fs = new List[maxd];
    
    for (int i = 0; i < maxd; i++) {
      fs[i] = new ArrayList<Criteria>(maxNumberOfPoints);
    }
  }

  public WfgHypervolume(int dimension, int maxNumberOfPoints, Criteria referencePoint) {
    this.referencePoint = new ArrayCriteria(referencePoint);
    maximizing = false;
    currentDeep = 0;
    currentDimension = dimension;
    this.maxNumberOfPoints = maxNumberOfPoints;
    maxNumberOfObjectives = dimension;
    pointComparator = new PointComparator(true);

    int maxd = this.maxNumberOfPoints - (OPT / 2 + 1);
    fs = new List[maxd];
    for (int i = 0; i < maxd; i++) {
      fs[i] = new ArrayList<Criteria>(maxNumberOfPoints);
    }
  }

  public double get2DHV(List<Criteria> front) {
    double hv = 0.0;

    hv = Math.abs((front.get(0).getDimensionValue(0) - referencePoint.getDimensionValue(0)) *
        (front.get(0).getDimensionValue(1) - referencePoint.getDimensionValue(1))) ;

    int v = front.size() ;
    for (int i = 1; i < front.size(); i++) {
      hv += Math.abs((front.get(i).getDimensionValue(0) - referencePoint.getDimensionValue(0)) *
          (front.get(i).getDimensionValue(1) - front.get(i - 1).getDimensionValue(1)));

    }

    return hv;
  }

  public double getInclusiveHV(Criteria point) {
    double volume = 1;
    for (int i = 0; i < currentDimension; i++) {
      volume *= Math.abs(point.getDimensionValue(i) - referencePoint.getDimensionValue(i));
    }

    return volume;
  }

  public double getExclusiveHV(List<Criteria> front, int point) {
    double volume;

    volume = getInclusiveHV(front.get(point));
    if (front.size() > point + 1) {
      makeDominatedBit(front, point);
      double v = getHV(fs[currentDeep - 1]);
      volume -= v;
      currentDeep--;
    }

    return volume;
  }

  public double getHV(List<Criteria> front) {
    double volume ;
    front.sort(pointComparator);

    if (currentDimension == 2) {
      volume = get2DHV(front);
    } else {
      volume = 0.0;

      currentDimension--;
      int numberOfPoints = front.size() ;
      for (int i = numberOfPoints - 1; i >= 0; i--) {
        volume += Math.abs(front.get(i).getDimensionValue(currentDimension) -
            referencePoint.getDimensionValue(currentDimension)) *
            this.getExclusiveHV(front, i);
      }
      currentDimension++;
    }

    return volume;
  }


  public void makeDominatedBit(List<Criteria> front, int p) {
    int z = front.size() - 1 - p;

    for (int i = 0; i < z; i++) {
      for (int j = 0; j < currentDimension; j++) {
        Criteria point1 = front.get(p) ;
        Criteria point2 = front.get(p + 1 + i) ;
        double worseValue = worse(point1.getDimensionValue(j), point2.getDimensionValue(j), false) ;
        int cd = currentDeep ;
        Criteria point3 = fs[currentDeep].get(i) ;
        point3.setDimensionValue(j, worseValue);
      }
    }

    Criteria t;
    int numberOfPoints = 1;
    
    for (int i = 1; i < z; i++) {
      int j = 0;
      boolean keep = true;
      while (j < numberOfPoints && keep) {
        switch (dominates2way(fs[currentDeep].get(i), fs[currentDeep].get(j))) {
          case -1:
            t = fs[currentDeep].get(j);
            numberOfPoints= numberOfPoints-1;
            fs[currentDeep].set(j,fs[currentDeep].get(numberOfPoints));
            fs[currentDeep].set(numberOfPoints, t);
            break;
          case 0:
            j++;
            break;
          default:
            keep = false;
            break;
        }
      }
      if (keep) {
        t = fs[currentDeep].get(numberOfPoints);
        fs[currentDeep].set(numberOfPoints, fs[currentDeep].get(i));
        fs[currentDeep].set(i, t);
        numberOfPoints = numberOfPoints+1;
      }
    }

    currentDeep++;
  }

  public int getLessContributorHV(List<Solution> solutionList) {
    List<Criteria> wholeFront = loadFront(solutionList, -1) ;

    int index = 0;
    double contribution = Double.POSITIVE_INFINITY;

    for (int i = 0; i < solutionList.size(); i++) {
      double[] v = new double[solutionList.get(i).getNumberOfObjectives()];
      for (int j = 0; j < v.length; j++) {
        v[j] = solutionList.get(i).getObjective(j);
      }

      double aux = this.getExclusiveHV(wholeFront, i);
      if ((aux) < contribution) {
        index = i;
        contribution = aux;
      }

      HypervolumeContribution hvc = new HypervolumeContribution() ;
      hvc.setAttribute(solutionList.get(i), aux);
      //solutionList.get(i).setCrowdingDistance(aux);
    }

    return index;
  }

  private List<Criteria> loadFront(List<Solution> solutionSet, int notLoadingIndex) {
    int numberOfPoints ;
    if (notLoadingIndex >= 0 && notLoadingIndex < solutionSet.size()) {
      numberOfPoints = solutionSet.size() - 1;
    } else {
      numberOfPoints = solutionSet.size();
    }

    int dimensions = solutionSet.get(0).getNumberOfObjectives();

    List<Criteria> front = new ArrayList<Criteria>(numberOfPoints) ;

    int index = 0;
    for (int i = 0; i < solutionSet.size(); i++) {
      if (i != notLoadingIndex) {
        Criteria point = new ArrayCriteria(dimensions) ;
        for (int j = 0; j < dimensions; j++) {
          point.setDimensionValue(j, solutionSet.get(i).getObjective(j));
        }
        front.set(index++, point);
      }
    }

    return front ;
  }

  private double worse(double x, double y, boolean maximizing) {
    double result;
    if (maximizing) {
      if (x > y) {
        result = y;
      } else {
        result = x;
      }
    } else {
      if (x > y) {
        result = x;
      } else {
        result = y;
      }
    }
    return result;
  }

  int dominates2way(Criteria p, Criteria q) {
    // returns -1 if p dominates q, 1 if q dominates p, 2 if p == q, 0 otherwise
    // ASSUMING MINIMIZATION

    // domination could be checked in either order

    for (int i = currentDimension - 1; i >= 0; i--) {
      if (p.getDimensionValue(i) < q.getDimensionValue(i)) {
        for (int j = i - 1; j >= 0; j--) {
          if (q.getDimensionValue(j) < p.getDimensionValue(j)) {
            return 0;
          }
        }
        return -1;
      } else if (q.getDimensionValue(i) < p.getDimensionValue(i)) {
        for (int j = i - 1; j >= 0; j--) {
          if (p.getDimensionValue(j) < q.getDimensionValue(j)) {
            return 0;
          }
        }
        return 1;
      }
    }
    return 2;
  }

  public static void main(String args[]) throws IOException, JMetalException {
    List<Criteria> front = new ArrayList<Criteria>();

    if (args.length == 0) {
      throw new JMetalException("Usage: WFGHV front [reference point]");
    }

    //if (args.length > 0) {
    front = new FileInputCriteria(args[0]).load();
    //}

    if (front.size()>0) {
    	int dimensions = front.get(0).getNumberOfDimensions();
    	Criteria referencePoint;
    	double[] points = new double[dimensions];

    	if (args.length == (dimensions + 1)) {
    		for (int i = 1; i <= dimensions; i++) {
    			points[i - 1] = Double.parseDouble(args[i]);
    		}
    	} else {
    		for (int i = 1; i <= dimensions; i++) {
    			points[i - 1] = 0.0;
    		}
    	}

    	referencePoint = new ArrayCriteria(points);
    	JMetalLogger.logger.info("Using reference point: " + referencePoint);

    	WfgHypervolume wfghv =
    			new WfgHypervolume(referencePoint.getNumberOfDimensions(), front.size(), referencePoint);

    	System.out.println("HV: " + wfghv.getHV(front)) ;
    } else {
    	System.out.println("The front is empty");
    }
  }
}
