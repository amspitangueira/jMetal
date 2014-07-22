//  JMetalException.java
//
//  Author:
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

package org.uma.jmetal.util;

import java.io.Serializable;
import java.util.logging.Level;


/**
 * jMetal exception class
 */
public class JMetalException extends RuntimeException implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = 4288481737365385068L;

  public JMetalException(String message) {
    super(message);
  }
  public JMetalException(Exception e) {
    Configuration.logger.log(Level.SEVERE, "Error", e);
  }
  public JMetalException(String message, Exception e) {
    Configuration.logger.log(Level.SEVERE, message, e);
  }

}
