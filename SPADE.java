package jspade;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

public class SPADE {
	
	private static class DefaultPreprocesser implements Preprocesser{

		@Override
		public HashMap<Integer, String> retrieveIndices(double minSup, String fileName, String regexSep) throws IOException {
			
			//All lines are formatted as: Sequence ID, Event ID, Number of Items in Event, List of Items
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			HashMap<String, Integer> items = new HashMap<String, Integer>();
			int size = 0;
			String line = null;
			//Obtain all distinct items and their supports
			while((line = bufferedReader.readLine()) != null){
				
				size++;
				String[] lineSep = line.split(regexSep);
				//We start at 3, because 0 is sequenceID, 1 is eventID, 2 is size of basket
				for(int i = 3; i < lineSep.length; i++){
					
					if(items.containsKey(lineSep[i]))
						items.put(lineSep[i], items.get(lineSep[i]) + 1);
					else
						items.put(lineSep[i], 1);
					
				}
				
			}
			//Retain only frequent 1-sequences
			HashMap<Integer, String> itemIndices = new HashMap<Integer, String>();
			Iterator<String> iterator = items.keySet().iterator();
			int index = 0;
			while(iterator.hasNext()){
				
				String next = iterator.next();
				if(((double) size)*minSup > items.get(next))
					iterator.remove();
				else
					itemIndices.put(index, next);
			}
			bufferedReader.close();
			return itemIndices;
		}

		@Override
		public int[][] preprocess(double minSup, String fileName, String regexSep, HashMap<Integer, String> itemIndices) throws IOException {
			
			//All lines are formatted as: Sequence ID, Event ID, Number of Items in Event, List of Items
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			String line = null;
			while((line = bufferedReader.readLine()) != null){
				
				System.out.println(line);
				//construct vertical id-lists;
				
			}
			bufferedReader.close();
			
			return null;
		}		
	
	}
	
	public static void spade(double minSup, String fileName, String regexSep) throws IOException{
		
		Preprocesser preprocesser = new DefaultPreprocesser();
		spade(minSup, preprocesser, fileName, regexSep);
		
	}
	
	public static void spade(double minSup, Preprocesser preprocesser, String fileName, String regexSep) throws IOException{
		
		HashMap<Integer, String> itemIndices = preprocesser.retrieveIndices(minSup, fileName, regexSep);
		int[][] idList1 = preprocesser.preprocess(minSup, fileName, regexSep, itemIndices);
		
	}
	
}
