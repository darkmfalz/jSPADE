package jspade;

import java.io.IOException;
import java.util.HashMap;

public interface Preprocesser {

	public HashMap<Integer, String> retrieveIndices(double minSup, String fileName, String regexSep) throws IOException;
	public int[][] preprocess(double minSup, String fileName, String regexSep, HashMap<Integer, String> itemIndices) throws IOException;

}
