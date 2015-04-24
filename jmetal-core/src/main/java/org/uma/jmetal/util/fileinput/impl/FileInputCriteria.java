package org.uma.jmetal.util.fileinput.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.uma.jmetal.util.JMetalException;
import org.uma.jmetal.util.criteria.Criteria;
import org.uma.jmetal.util.criteria.impl.ArrayCriteria;
import org.uma.jmetal.util.fileinput.FileListInput;

public class FileInputCriteria implements FileListInput<Criteria> {
	private String fileName;
	
	public FileInputCriteria(String file) {
		this.fileName = file;
	}
	
	  public InputStream createInputStream(String fileName) throws FileNotFoundException {
		    InputStream inputStream = getClass().getResourceAsStream(fileName);
		    if (inputStream == null) {
		      inputStream = new FileInputStream(fileName) ;
		    }

		    return inputStream ;
	  }
	
	@Override
	public List<Criteria> load() {
	    InputStream inputStream = null;
	    
	    try {
	    	inputStream = createInputStream(this.fileName) ;
	    } catch (FileNotFoundException e) {
	    	throw new JMetalException("File not found " + fileName, e);
	    }
	    InputStreamReader isr = new InputStreamReader(inputStream);
	    BufferedReader br = new BufferedReader(isr);

	    List<Criteria> list = new ArrayList<>();
	    int numberOfObjectives = 0;
	    String aux ;
	    try {
	      aux = br.readLine();

	      while (aux != null) {
	        StringTokenizer tokenizer = new StringTokenizer(aux);
	        int i = 0;
	        if (numberOfObjectives == 0) {
	          numberOfObjectives = tokenizer.countTokens();
	        } else if (numberOfObjectives != tokenizer.countTokens()) {
	          throw new JMetalException("Invalid number of points read. "
	              + "Expected: " + numberOfObjectives + ", received: " + tokenizer.countTokens()) ;
	        }

	        Criteria point = new ArrayCriteria(numberOfObjectives) ;
	        while (tokenizer.hasMoreTokens()) {
	          double value = new Double(tokenizer.nextToken());
	          point.setDimensionValue(i, value);
	          i++;
	        }
	        list.add(point);
	        aux = br.readLine();
	      }
	      br.close();
	    } catch (IOException e) {
	      throw new JMetalException("Error reading file", e);
	    } catch (NumberFormatException e) {
	      throw new JMetalException("Format number exception when reading file", e);
	    }
	    return list;
	    

	}

}
