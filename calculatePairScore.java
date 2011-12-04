package com.myParser.Parser;

import java.util.*; 
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

public class calculatePairScore {
	
	public static void main(String args[]){
		
		try{
			String sentence = null;
			FileInputStream cnnFis = new FileInputStream
									  ("/home/abhishek/Nlp/elections/cnn_pairs/resultCnnExtracted.txt");
			DataInputStream cnnDis = new DataInputStream(cnnFis);
	    		BufferedReader cnnBr = new BufferedReader(new InputStreamReader(cnnDis));
			FileInputStream foxFis = new FileInputStream
									  ("/home/abhishek/Nlp/elections/fox_pairs/resultFoxExtracted.txt");
			DataInputStream foxDis = new DataInputStream(foxFis);
	    		BufferedReader foxBr = new BufferedReader(new InputStreamReader(foxDis));
	    		HashMap cnnPairsFrequency = new HashMap(); 
	    		while((sentence = cnnBr.readLine())!= null){
		    		Integer oldValue = (Integer)cnnPairsFrequency.get(sentence);
		    		if(oldValue != null){
		    			cnnPairsFrequency.put(sentence, oldValue+1);
		    		}
		    		else
		    		{
		    			cnnPairsFrequency.put(sentence, 1);
		    		}
	    		}
	    		HashMap foxPairsFrequency = new HashMap();
	    		while((sentence = foxBr.readLine())!= null){
	    			Integer oldValue = (Integer)foxPairsFrequency.get(sentence);
		    		if(oldValue != null){
		    			foxPairsFrequency.put(sentence, oldValue+1);
		    		}
		    		else
		    		{
		    			foxPairsFrequency.put(sentence, 1);
		    		}
	    		}
	    		cnnFis.close();
	    		foxFis.close();
	    	String path = "/home/abhishek/Nlp/elections/cnn_pairs/cnn_test";
    		File folder = new File(path);
            File[] listOfFiles = folder.listFiles();	
            for (int j = 0; j < listOfFiles.length; j++) {
            	FileInputStream fis = new FileInputStream
						  (path + "/"+listOfFiles[j].getName());
            	DataInputStream dis = new DataInputStream(fis);
    	    	BufferedReader br = new BufferedReader(new InputStreamReader(dis));
    	    	int cnnTotalScore = 0;
    			int foxTotalScore = 0;
    	    	while((sentence = br.readLine())!= null){
    	    		Integer cnnValue = (Integer)cnnPairsFrequency.get(sentence);
    	    		if(cnnValue != null){
    	    			 cnnTotalScore = cnnTotalScore + cnnValue; 
    	    		}
    	    		Integer foxValue = (Integer)foxPairsFrequency.get(sentence);
    	    		if(foxValue != null){
    	    			 foxTotalScore = foxTotalScore + foxValue; 
    	    		}
    	    	}
    	    	System.out.println(listOfFiles[j].getName() + " " +"Cnn: " + cnnTotalScore 
    	    						+ " " + "Fox: " + foxTotalScore);
            }
	    	
		}
		catch (Exception e) {
			e.printStackTrace();
			System.err.println("ERROR: " + e.getMessage());
		}
		
	}	
}