package com.myParser.Parser;
//import some libraries:
import java.io.*; 
import java.util.*; 
import edu.stanford.nlp.trees.*; 
import edu.stanford.nlp.process.*;
import edu.stanford.nlp.objectbank.TokenizerFactory;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;

class PersonalParser {
	
	public double parser(String splitSentence, String path){
		
		double score = 0;
		try{
			LexicalizedParser parser = new LexicalizedParser(path);
			TokenizerFactory tf = PTBTokenizer.factory(false, new WordTokenFactory());
			TreePrint tp = new TreePrint("penn,typedDependenciesCollapsed");
			List tokens = tf.getTokenizer(new StringReader(splitSentence)).tokenize();
			parser.parse(tokens); // parse the tokens
			Tree tree = parser.getBestParse(); // get the best parse tree
			score = parser.getPCFGScore();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.err.println("ERROR: " + e.getMessage()); // print error message
		}
		return score;
		
	}
}

class Parser // start of the Parser class
{
	@SuppressWarnings("deprecation")
	
	public static void resultWrite(String file, double cnnTotalPCFGScore, double foxTotalPCFGScore,BufferedWriter result){
		
		try{
			if(cnnTotalPCFGScore != foxTotalPCFGScore){
				result.write("\nFile " + file);
				result.write(" LIBERAL PCFG : " 
							+ cnnTotalPCFGScore + 
							" CONSERVATIVE PCFG : " 
							+ foxTotalPCFGScore);
				if(foxTotalPCFGScore > cnnTotalPCFGScore){
					result.write(" CONSERVATIVE");
				}
				else if(cnnTotalPCFGScore > foxTotalPCFGScore){
					result.write(" LIBERAL");
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			System.err.println("ERROR: " + e.getMessage()); // print error message
		}
		
		
	}
	
	
	public static void folderRead(String path){
			
	try{
		
		BufferedWriter result = new BufferedWriter(new FileWriter("/home/abhishek/Nlp/elections/result.txt", true));
        PersonalParser cnn = new PersonalParser();
    	PersonalParser fox = new PersonalParser();
		File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        for (int j = 0; j < listOfFiles.length; j++) {
        	
        	String sentence = null;
        	double cnnTotalPCFGScore = 0;
			double foxTotalPCFGScore = 0;
        	FileInputStream fis = new FileInputStream(path + "/"
					  									+listOfFiles[j].getName());
        	System.out.println("\nFile " + listOfFiles[j].getName());
        	DataInputStream dis = new DataInputStream(fis);
        	BufferedReader br = new BufferedReader(new InputStreamReader(dis));
    
        	while((sentence = br.readLine())!= null){
				
    			//System.out.println ("\n\n\n\nORIGINAL:\n\n" + sentence); 
    			String [] splits ;
    			splits = sentence.split("[?!\\.]");
    			//System.out.println(splits.length-1);
    			
    				int i = 0;
	    			while (i < splits.length-1)  
	    			{ 
	    				// print sentence:
	    				//System.out.println ("\n\n\n\n sentence is:\n\n" + splits[i]); 

	    				Double cnnScore = cnn.parser(splits[i], "/home/abhishek/Nlp/elections/cnn_elections_train/cnnElections.ser.gz");
	    				cnnTotalPCFGScore += cnnScore;
	    				
	    				Double foxScore = fox.parser(splits[i], "/home/abhishek/Nlp/elections/fox_elections_train/foxElectionsAll.ser.gz");
	    				foxTotalPCFGScore += foxScore;
	    				
	    				i++;
	    			} 
    			
    			}
        		resultWrite(listOfFiles[j].getName(), cnnTotalPCFGScore, foxTotalPCFGScore, result);
				dis.close(); // close input file
    		}
        result.close(); //close output file
	}
	catch (Exception e) {
		e.printStackTrace();
		System.err.println("ERROR: " + e.getMessage()); // print error message
	}
	
			
	}
	
	public static void main(String[] args) // start of the main method
	{	
		
		System.out.println("\n\n\nSTART\n\n\n"); // print START
		try 
		{
			folderRead("/home/abhishek/Nlp/elections/cnn_elections_test");
		}
		catch (Exception e) // catch error if any
		{ 
			e.printStackTrace();
			System.err.println("ERROR: " + e.getMessage()); // print error message
		}
		
		System.out.println("\n\n\nTHE END\n\n\n"); // print THE END
	} 
} 




