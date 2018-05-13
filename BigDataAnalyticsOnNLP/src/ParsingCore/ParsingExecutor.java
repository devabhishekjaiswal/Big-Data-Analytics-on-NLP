/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ParsingCore;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;

/**

 @author Shubham
 */
public class ParsingExecutor {

    private Parser parser;

    private BufferedReader reader;
    private BufferedWriter writer;

    // Instantiate Parsing Model
    public ParsingExecutor() {
	try {
	    InputStream inputStream;
	    ParserModel model;

	    inputStream = new FileInputStream("en-parser-chunking.bin");
	    model = new ParserModel(inputStream);
	    this.parser = ParserFactory.create(model);

	    inputStream.close();
	} catch (Exception e) {
	    System.out.println("Exception while instantiating parsing objects:\t" + e.toString());
	    this.parser = null;
	}
    }

    // Set IO files
    public void setInputFile(File inputFile) {
	try {
	    reader = new BufferedReader(new FileReader(inputFile));
	} catch (Exception e) {
	    System.out.println("Exception while setting input file:\t" + e.toString());
	    System.exit(0);
	}
    }

    public void setOutputFile(File outputFile) {
	try {
	    writer = new BufferedWriter(new FileWriter(outputFile));
	} catch (Exception e) {
	    System.out.println("Exception while setting output file:\t" + e.toString());
	    System.exit(0);
	}
    }

    public void setIOFiles(File inputFile, File outputFile) {
	setInputFile(inputFile);
	setOutputFile(outputFile);
    }

    private void closeFiles() {
	try {
	    reader.close();
	    writer.close();
	} catch (Exception e) {
	    System.out.println("Exception while closing reader-writer:\t" + e.toString());
	}
    }

    // Do parsing
    public String getParsingOutput() {
	
	
	return null;
    }
    
}
