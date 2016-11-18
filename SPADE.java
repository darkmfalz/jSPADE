package jspade;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeSet;

public class SPADE {

	private double minSup;
	//We need to store data such as sequence id, event id, items
	//First column is sequence id, second is event id, third is list size, everything else is items
	private int[][] transactionHistory;
	
	//insert already ingested transactionHistory
	public SPADE(double minSup, int[][] transactionHistory){
		
		this.minSup = minSup;
		this.transactionHistory = transactionHistory;
		
	}
	
	//Read in as the transactionHistory object in R
	public SPADE(double minSup, String fileName, String regexSep) throws IOException{
		
		// FileReader reads text files in the default encoding.
        FileReader fileReader = new FileReader(fileName);
        // Always wrap FileReader in BufferedReader.
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        
        //Count the number of lines
        int numLines = 0;
        String line = null;
        while((line = bufferedReader.readLine()) != null)
        	numLines++;
        transactionHistory = new int[numLines][];
        
        //put all the transactions into the transaction history
        fileReader = new FileReader(fileName);
        bufferedReader = new BufferedReader(fileReader);
        line = null;
        int i = 0;
        while((line = bufferedReader.readLine()) != null){
        	
        	String[] lineSep = line.split(regexSep);
        	transactionHistory[i] = new int[lineSep.length];
        	transactionHistory[i][0] = Integer.parseInt(lineSep[0]);
        	transactionHistory[i][1] = Integer.parseInt(lineSep[1]);
        	transactionHistory[i][2] = Integer.parseInt(lineSep[2]);
        	
        	TreeSet<Integer> sort = new TreeSet<Integer>();
        	for(int j = 3; j < lineSep.length; j++)
        		sort.add(Integer.parseInt(lineSep[j]));
        	
        	Integer[] sorted = sort.toArray(new Integer[0]);
        	for(int j = 0; j < sorted.length; j++)
        		transactionHistory[i][3+j] = sorted[j].intValue();
        	
        }
        
        bufferedReader.close();
		
	}
	
}
