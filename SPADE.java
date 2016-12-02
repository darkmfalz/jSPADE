package jspade;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.TreeSet;

public class SPADE {
	
	private static class DefaultPreprocesser implements Preprocesser{

		@Override
		public HashMap<Integer, String> retrieveIndices(double minSup, String fileName, String regexSep) throws IOException {
			
			//All lines are formatted as: integer Sequence ID, integer Event ID, Number of Items in Event, List of Items
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
			
			//Construct the reverse index hash
			HashMap<String, Integer> string2index = new HashMap<String, Integer>();
			Iterator<Integer> iterator = itemIndices.keySet().iterator();
			while(iterator.hasNext()){
				
				int nextVal = iterator.next();
				String nextKey = itemIndices.get(nextVal);
				string2index.put(nextKey, nextVal);
				
			}
			
			//All lines are formatted as: integer Sequence ID, integer Event ID, Number of Items in Event, List of Items
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			//TreeMap<Item Number, TreeMap<Household Number, TreeSet<Week Number>>>
			TreeMap<Integer, TreeMap<Integer, TreeSet<Integer>>> verticalIDList = new TreeMap<Integer, TreeMap<Integer, TreeSet<Integer>>>();
			
			String line = null;
			while((line = bufferedReader.readLine()) != null){
				
				//construct vertical id-lists;
				String[] lineSep = line.split(regexSep);
				for(int i = 3; i < lineSep.length; i++){
					
					if(string2index.containsKey(lineSep[i])){
						
						int item_nbr = string2index.get(lineSep[i]);
						int household_account_nbr = Integer.parseInt(lineSep[0]);
						
						if(!verticalIDList.containsKey(item_nbr))
							verticalIDList.put(item_nbr, new TreeMap<Integer, TreeSet<Integer>>());
						if(!verticalIDList.get(item_nbr).containsKey(household_account_nbr))
							verticalIDList.get(item_nbr).put(household_account_nbr , new TreeSet<Integer>());
						verticalIDList.get(item_nbr).get(Integer.parseInt(lineSep[0])).add(Integer.parseInt(lineSep[1]));
					
					}
					
				}
				
			}
			
			//convert into double array
			iterator = verticalIDList.keySet().iterator();
			//get the overall size of the entire affair, though
			int size = 0;
			while(iterator.hasNext()){
				
				int item_nbr = iterator.next();
				Iterator<Integer> iterator2 = verticalIDList.get(item_nbr).keySet().iterator();
				while(iterator2.hasNext()){
					
					size += verticalIDList.get(item_nbr).get(iterator2.next()).size();
					
				}
				
			}
			int[][] vid = new int[size][];
			//ACTUALLY construct the double array
			while(iterator.hasNext()){
				
				int item_nbr = iterator.next();
				Iterator<Integer> iterator2 = verticalIDList.get(item_nbr).keySet().iterator();
				while(iterator2.hasNext()){
					
					int household_account_nbr = iterator2.next();
					Iterator<Integer> iterator3 = verticalIDList.get(item_nbr).get(household_account_nbr).iterator();
					while(iterator3.hasNext()){
						
						
						
					}
					
				}
				
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
