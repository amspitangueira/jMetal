//  ExperimentData.java 
//
//  Authors:
//       Antonio J. Nebro <antonio@lcc.uma.es>
//       Juan J. Durillo <durillo@lcc.uma.es>
//
//  Copyright (c) 2011 Antonio J. Nebro, Juan J. Durillo
//
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

package org.uma.jmetal.experiment;

import java.util.Arrays;

/**
 * Created by Antonio J. Nebro on 17/07/14.
 * 
 * Class for describing the configuration of a jMetal experiment
 */
public class ExperimentData {
	private String experimentName;
	private String[] algorithmNameList;
	private String[] problemList;
	private String[] paretoFrontFileList;
	private String[] indicatorList;
	private String experimentBaseDirectory;

	private String latexDirectory;
	private String paretoFrontDirectory;
	private String outputParetoFrontFileName;
	private String outputParetoSetFileName;
	private int independentRuns;

	/** Constructor */
	private ExperimentData(Builder builder) {
		experimentName = builder.experimentName ;
	}

	/* Getters */
	public String getExperimentName() {
		return experimentName;
	}

	public String[] getAlgorithmNameList() {
		return algorithmNameList;
	}

	public String[] getProblemList() {
		return problemList;
	}

	public String[] getParetoFrontFileList() {
		return paretoFrontFileList;
	}

	public String[] getIndicatorList() {
		return indicatorList;
	}

	public String getExperimentBaseDirectory() {
		return experimentBaseDirectory;
	}

	public String getLatexDirectory() {
		return latexDirectory;
	}

	public String getParetoFrontDirectory() {
		return paretoFrontDirectory;
	}

	public String getOutputParetoFrontFileName() {
		return outputParetoFrontFileName;
	}

	public String getOutputParetoSetFileName() {
		return outputParetoSetFileName;
	}

	public int getIndependentRuns() {
		return independentRuns;
	}
	
	/** Builder */
	public static class Builder {
		private final String experimentName ;
		private String[] algorithmNameList;
		private String[] problemList;
		private String[] paretoFrontFileList;
		private String[] indicatorList;
		private String experimentBaseDirectory;

		private String latexDirectory;
		private String paretoFrontDirectory;
		private String outputParetoFrontFileName;
		private String outputParetoSetFileName;
		private int independentRuns;

		public Builder(String experimentName) {
			this.experimentName = experimentName ;
		}

		public Builder algorithmNameList(String[] algorithmNameList) {
			this.algorithmNameList = new String[algorithmNameList.length] ;
			this.algorithmNameList = Arrays.copyOf(algorithmNameList, algorithmNameList.length) ;

			return this ;
		}

		public Builder problemList(String[] problemList) {
			this.problemList = new String[problemList.length] ;
			this.problemList = Arrays.copyOf(problemList, algorithmNameList.length) ;

			return this ;
		}

		public Builder paretoFrontFileList(String[] paretoFrontFileList) {
			this.paretoFrontFileList = new String[paretoFrontFileList.length] ;
			this.paretoFrontFileList = Arrays.copyOf(paretoFrontFileList, paretoFrontFileList.length) ;

			return this ;
		}

		public Builder indicatorList(String[] indicatorList) {
			this.indicatorList = new String[indicatorList.length] ;
			this.indicatorList = Arrays.copyOf(indicatorList, indicatorList.length) ;

			return this ;
		}

		public Builder experimentBaseDirectory(String experimentBaseDirectory) {
			this.experimentBaseDirectory = experimentBaseDirectory ;

			return this ;
		}
		
		public Builder paretoFrontDirectory(String paretoFrontDirectory) {
			this.paretoFrontDirectory = paretoFrontDirectory ;

			return this ;
		}
		
		public Builder latexDirectory(String latexDirectory) {
			this.latexDirectory = latexDirectory ;

			return this ;
		}

		public Builder outputParetoFrontFileName(String outputParetoFrontFileName) {
			this.outputParetoFrontFileName = outputParetoFrontFileName ;

			return this ;
		}
		
		public Builder outputParetoSetFileName(String outputParetoSetFileName) {
			this.outputParetoSetFileName = outputParetoSetFileName ;

			return this ;
		}
		
		public Builder independentRuns(String independentRuns) {
			this.independentRuns = Integer.parseInt(independentRuns) ;

			return this ;
		}

		public ExperimentData build() {
			return new ExperimentData(this) ;
		}
	}
}