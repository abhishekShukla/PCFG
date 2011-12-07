package com.myParser.Parser;
import java.io.*;

public class PreProcessor {
	
	public static void main(String args[])
    {
        try
        {   
        	
        	if(args.length != 3){
        		System.out.println("Format: Program Name <Input Folder Path> <Output Folder Path>" +
        				" <Stanford Parser Directory Path>");
        	}
        	else{
            	String inputFolderName = args[0]; 
                String outputFolderName = args[1];
                String stanfordParserDir = args[2];
                
            
            File folder = new File(inputFolderName);
                File[] listOfFiles = folder.listFiles();
                

                for (int i = 0; i < listOfFiles.length - 1; i+=2) {

                    System.out.println("File " + listOfFiles[i].getName());
                    System.out.println("File " + listOfFiles[i+1].getName());
                    
                    Runtime first_rt = Runtime.getRuntime();
                    Runtime second_rt = Runtime.getRuntime();
               
                    String first_command = "nohup java -Xms1g -Xmx1g " +
                    "-cp " + stanfordParserDir +  "stanford-parser.jar " +
                    "edu.stanford.nlp.process.DocumentPreprocessor " +
                    inputFolderName + listOfFiles[i].getName();
                    
                    String second_command = "nohup java -Xms1g -Xmx1g " +
                    "-cp " + stanfordParserDir +  "stanford-parser.jar " +
                    "edu.stanford.nlp.process.DocumentPreprocessor " +
                    inputFolderName + listOfFiles[i+1].getName();
                                    
                   FileOutputStream first_fos = new FileOutputStream(outputFolderName  
                    + listOfFiles[i].getName(), true);
                    
                    FileOutputStream second_fos = new FileOutputStream(outputFolderName 
                            + listOfFiles[i+1].getName(), true);
                    
                    Process first_proc = first_rt.exec(first_command);
                    Process second_proc = second_rt.exec(second_command);

                    
                    StreamGobbler first_mesgGobbler = new 
                            StreamGobbler(first_proc.getErrorStream(), "MESSAGE");  
                    StreamGobbler second_mesgGobbler = new 
                            StreamGobbler(second_proc.getErrorStream(), "MESSAGE");  
                    
                    StreamGobbler first_outputGobbler = new 
                        StreamGobbler(first_proc.getInputStream(), "OUTPUT", first_fos);
                    StreamGobbler second_outputGobbler = new 
                            StreamGobbler(second_proc.getInputStream(), "OUTPUT", second_fos);

                    first_mesgGobbler.run();
                    first_outputGobbler.run();
                    
                    second_mesgGobbler.run();
                    second_outputGobbler.run();
                    
                    int first_exitVal = first_proc.waitFor();
                    int second_exitVal = second_proc.waitFor();

                     
                    System.out.println("First Process exitValue: " + first_exitVal);
                    System.out.println("Second Process exitValue: " + second_exitVal);
                    
                    first_fos.flush();
                    first_fos.close();
                    
                    second_fos.flush();
                    second_fos.close();
                    
        	}

            }
            
        } catch (Throwable t)
          {
            t.printStackTrace();
          }
    }

}

