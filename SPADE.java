package jspade;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

public class SPADE {

	private static HashMap<Integer, String> itemHash;
	
	private static void preprocessR(double minSup, String fileName, String regexSep) throws IOException{
		
		FileReader fileReader = new FileReader(fileName);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		HashMap<String, Integer> items = new HashMap<String, Integer>();
		int size = 0;
		String line = null;
		//Obtain all distinct items and their supports
		while((line = bufferedReader.readLine()) != null){
			
			size++;
			String[] lineSep = line.split(regexSep);
			for(int i = 3; i < lineSep.length; i++){
				
				if(items.containsKey(lineSep[i]))
					items.put(lineSep[i], items.get(lineSep[i]) + 1);
				else
					items.put(lineSep[i], 1);
				
			}
			
		}
		//Retain only frequent 1-sequences
		Iterator<String> iterator = items.keySet().iterator();
		while(iterator.hasNext()){
			
			if(((double) size)*minSup > items.get(iterator.next()))
				iterator.remove();
			else{
				
				
				
			}
			
		}
		//Develop Zobrist Hash
		bufferedReader.close();
		
	}
	
	public static void spade(double minSup, String fileName, String regexSep) throws IOException{
		
		itemHash = new HashMap<Integer, String>();
		preprocessR(minSup, fileName, regexSep);
		
	}
	
}
