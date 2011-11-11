package com.myParser.Parser;
//import some libraries:
import java.io.*; 
import java.util.*; 
import edu.stanford.nlp.trees.*; 
import edu.stanford.nlp.process.*;
import edu.stanford.nlp.objectbank.TokenizerFactory;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;

class Parser // start of the Parser class
{
	@SuppressWarnings("deprecation")
	public static void main(String[] args) // start of the main method
	{	
		
		System.out.println("\n\n\nSTART\n\n\n"); // print START
		try // device to handle potential errors
		{
			// open file whose path is passed
			// as the first argument of the main method:
			FileInputStream fis = new FileInputStream("/home/abhishek/Nlp/test/test1.txt"); 
			DataInputStream dis = new DataInputStream(fis);
			BufferedReader br = new BufferedReader(new InputStreamReader(dis));
			// prepare Parser, Tokenizer and Tree printer:
			LexicalizedParser lp = new LexicalizedParser("/home/abhishek/Nlp/test/test1.ser.gz"); 
			//lp.setOptionFlags(new String[]{"-maxLength", "80", "-sentences","newline"});
			//lp.setOptionFlags(new String[]{"-maxLength", "80", "-retainTmpSubcategories"});
			TokenizerFactory tf = PTBTokenizer.factory(false, new WordTokenFactory());
			//System.out.println(tf);
			TreePrint tp = new TreePrint("penn,typedDependenciesCollapsed");
			String sentence = null;
			double Total_PCFGScore = 0;
			while((sentence = br.readLine())!= null){
			//while (dis.available() != 0){
				//sentence = dis.readLine();
			
			System.out.println ("\n\n\n\nORIGINAL:\n\n" + sentence); 
			String [] splits ;
			splits = sentence.split("[?!\\.]");
			System.out.println(splits.length-1);
			int i =0;
			
			while (i < splits.length-1)  
			{ 
				// print sentence:
				System.out.println ("\n\n\n\n sentence is:\n\n" + splits[i]); 
				// put tokens in a list:
				
				List tokens = tf.getTokenizer(new StringReader(splits[i])).tokenize(); 
				lp.parse(tokens); // parse the tokens
				Tree t = lp.getBestParse(); // get the best parse tree
				double Score = lp.getPCFGScore();
				System.out.println("\nPROCESSED:\n\n"); tp.printTree(t); // print tree
				System.out.println("The PCFG score for the sentence is : "+ Score);
				Total_PCFGScore += Score;
				i ++;
			} 
			//dis.close(); // close input file
			
		}
			dis.close(); // close input file
			System.out.println("The PCFG score of the document is :" + Total_PCFGScore);
		}
		catch (Exception e) // catch error if any
		{ 
			System.err.println("ERROR: " + e.getMessage()); // print error message
		}
		
		System.out.println("\n\n\nTHE END\n\n\n"); // print THE END
	} // end of the main method
} // end of the myParser class
