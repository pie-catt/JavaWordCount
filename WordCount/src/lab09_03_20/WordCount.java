/**
 * User
 */
package lab09_03_20;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Pietro Cattaneo
 *
 */
public class WordCount {
	
	//Map for input and output source
	private static Map<String, String> IOMap = new HashMap<>();
	//Map for words occurrence numbers
	private static Map<String, Integer> wordNum = new HashMap<>();
	//Scanner for input scan
	private static Scanner myScan;
	/**
	 * Mapping input and output source
	 * 
	 * @param args the command line parameters
	 */
	static void setIOMap(String[] args){
		
		//Setting initially input and output source to null
		//Treating input source null as System.in 
		//and output source null as System.out
		IOMap.put("input", null);
		IOMap.put("output", null);
		
		for(int i = 0; i < args.length; i++){
			
			if(args[i].equals("-i")){
				IOMap.put("input", args[i+1]);
			}
			
			if(args[i].equals("-o")){
				IOMap.put("output", args[i+1]);
			}
		}
	}
	
	/**
	 * Tries to open an input file for a BufferedReader
	 * If the input is mapped to null or an Exception is thrown,
	 * System.in is used as input stream source
	 *
	 * @return a new BufferedReader
	 */
	static	BufferedReader tryOpenReader() {
	
		//If input is mapped to null, System.in is used
		if(IOMap.get("input") == null){
			System.out.println("No input file, using stdin for text input");
			return new BufferedReader(new InputStreamReader(System.in));			
		}
	
	//Tries to open a file
	try	{	
	
		return new BufferedReader(new FileReader(IOMap.get("input")));
	
	//If the file cannot be opened, System.in is used
	} catch (FileNotFoundException e) {
		System.out.println("Cannot open " + IOMap.get("input"));
		System.out.println("Using stdin for text input");
		return new BufferedReader(new InputStreamReader(System.in));
		}
				
	}
	
	/**
	 * Tries to open an output file for a PrintWriter
	 * If the output is mapped to null or an Exception is thrown,
	 * System.out is used as output stream source
	 *
	 * @return a new PrintWriter
	 */
	static	PrintWriter tryOpenWriter() {
	
		//If output is mapped to null, System.out is used
	if(IOMap.get("output") == null){
		System.out.println("No output file, using stdout for text output");
		return new PrintWriter(System.out);			
		}
	
	//Tries to open a file for printing output text
	try	{	
	
		return new PrintWriter(IOMap.get("output"));
	
	//If the file cannot be opened, System.out is used
	} catch (FileNotFoundException e) {
		System.out.println("Cannot open " + IOMap.get("output"));
		System.out.println("Using stdin for text output");
		return new PrintWriter(System.out);			
		}
				
	}
	
	/**
	 * Reads text input, counts words occurrence number and prints result on specified output
	 * @param reader input text
	 * @param printer output text
	 */
	static void inputOutput(Reader reader, PrintWriter printer){
		
		//Creating a Scanner with the input set
		myScan = new Scanner(reader);
		//Since the Scanner must match only words,
		//it is set to ignore numbers and punctuation
		myScan.useDelimiter("[^A-Za-z]+");
		//Scanning input and counting words
		while(myScan.hasNext()){
			//reads next token
			String token = myScan.next();
			// if the word is already present in the hashmap
		    if (wordNum.containsKey(token)) {
		        // just increment the current frequency of the word
		        // this overrides the existing frequency
		        wordNum.put(token, wordNum.get(token) + 1);
		    } else {
		        // since the word is not there just put it with a frequency 1
		        wordNum.put(token, 1);
		    }
			
		}
	
		//No more tokens to read
		//print result on output
		
		for (String key : wordNum.keySet()) {
		    
			printer.print(key + "=" + wordNum.get(key) + ", ");
			
		}
		
	}
	
	
	//Main
	public static void main(String[] args) {

		//Setting Input and Output Source
		setIOMap(args);

		try
		(Reader reader = tryOpenReader(); PrintWriter printer = tryOpenWriter()) {
		inputOutput(reader,printer);
	
		} catch (IOException e){
			
		e.printStackTrace();
		
		} catch (Throwable t){
			
		t.printStackTrace();
			
		}
	}
}