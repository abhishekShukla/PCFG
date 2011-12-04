package com.myParser.Parser;
import java.io.*; 
import java.util.*; 


public class ExtractPairs {
	
	public static void main(String args[]){
		
		try{
			String sentence = null;
			String path = "/home/abhishek/Nlp/elections/cnn_elections_test/pair_test";
			File folder = new File(path);
	        File[] listOfFiles = folder.listFiles();
	        for (int j = 0; j < listOfFiles.length; j++) {
	        	FileInputStream fis = new FileInputStream
						(path + "/"+listOfFiles[j].getName());
	        	DataInputStream dis = new DataInputStream(fis);
	        	BufferedReader br = new BufferedReader(new InputStreamReader(dis));
	        	BufferedWriter result = new BufferedWriter(new FileWriter
						("/home/abhishek/Nlp/elections/cnn_pairs/cnn_test/"+listOfFiles[j].getName()+"_extracted", true));
	        	
	        	while((sentence = br.readLine())!= null){
	        		String[] split = sentence.split("[(.\\-.\\,\\s\\.\\-.)]");
	        		
	        		/*
	        		
	        		acomp: adjectival complement
	        		An adjectival complement of a VP is an adjectival phrase which functions as the complement
	        		(like an object of the verb); an adjectival complement of a clause is the adjectival complement
	        		of the VP which is the predicate of that clause.
	        		“She looks very beautiful”
	        		acomp(looks, beautiful) 
	        		
	        		*/
	        		if(split[0].equals("acomp")){
	        			result.write(split[1] + "," + split[4] + "\n");
	        		}
	        		/*
	        		amod : adjectival modifier
	        		An adjectival modifier of an NP is any adjectival phrase that serves to modify the meaning of
	        		the NP.
	        		“Sam eats red meat”
	        		amod (meat, red)
	        		 */
	        		else if(split[0].equals("amod")){
	        			result.write(split[1] + "," + split[4] + "\n");
	        		}
	        		/*
	        		ansubj : nominal subject
					A nominal subject is a noun phrase which is the syntactic subject of a clause. The governor of
					this relation might not always be a verb: when the verb is a copular verb, the root of the clause
					is the complement of the copular verb.
					“Clinton defeated Dole”
					“The baby is cute”
					nsubj (defeated, Clinton)
					nsubj (cute, baby)
	        		 */
	        		else if(split[0].equals("ansubj")){
	        			result.write(split[1] + "," + split[4] + "\n");
	        		}
	        		/*
	        		nsubjpass: passive nominal subject
					A passive nominal subject is a noun phrase which is the syntactic subject of a passive clause.
					“Dole was defeated by Clinton”
					nsubjpass(defeated, Dole)
	        		 */
	        		else if(split[0].equals("nsubjpass")){
	        			result.write(split[1] + "," + split[4] + "\n");
	        		}
	        		
	        	}
	        	fis.close();
	        	result.close();
	        }
		}
		catch (Exception e) {
			e.printStackTrace();
			System.err.println("ERROR: " + e.getMessage()); 
		}		
	}

}
