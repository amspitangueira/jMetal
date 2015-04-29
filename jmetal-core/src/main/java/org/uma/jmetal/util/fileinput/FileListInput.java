package org.uma.jmetal.util.fileinput;

import java.io.FileNotFoundException;
import java.util.List;

public interface FileListInput<T> {
	List<T> load();
}
