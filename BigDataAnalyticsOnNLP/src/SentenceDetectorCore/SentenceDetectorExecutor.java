package SentenceDetectorCore;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.util.InvalidFormatException;

/*
 To change this license header, choose License Headers in Project Properties.
 To change this template file, choose Tools | Templates
 and open the template in the editor.
 */
/**

 @author vanila
 */
public class SentenceDetectorExecutor {

    private BufferedReader reader;
    private BufferedWriter writer;

    public void setIOFiles(File inputFile, File outputFile) {
	try {
	    reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));
	    writer = new BufferedWriter(new FileWriter(outputFile));
	} catch (Exception e) {
	    System.out.println("Exception while instantiating reader-writer:\t" + e.toString());
	}
    }

    private void closeFiles() {
	try {
	    reader.close();
	    writer.close();
	} catch (Exception e) {
	    System.out.println("Exception while closing files:\t" + e.toString());
	}
    }

    private String readFile() {
	StringBuilder builder;
	String line;

	builder = new StringBuilder();
	try {
	    while ((line = reader.readLine()) != null) {
		builder.append(line);
		builder.append('\n');
	    }
	} catch (Exception e) {
	    System.out.println("Exception while reading input file:\t" + e.toString());
	}

	return builder.toString();
    }

    private void writeFile(String sentence) {
	try {
	    writer.write(sentence);
	    writer.newLine();
	} catch (Exception e) {
	    System.out.println("Exception while writing output file:\t" + e.toString());
	}
    }

    public static void main(String[] args) throws IOException {
	//SentenceDetectorExecutor.SentenceDetect();
	SentenceDetectorExecutor detector;
	File inputFile = null;
	File outputFile = null;

	detector = new SentenceDetectorExecutor();
	try {
	    inputFile = new File("Julius-Ceaser.txt");
	} catch (Exception e) {
	    System.out.println("Julius-Ceasar.txt not found:\t" + e.toString());
	}
	try {
	    outputFile = new File("SentenceDetector_OP.txt");
	} catch (Exception e) {
	    System.out.println("SentenceDetector_OP.txt not found:\t" + e.toString());
	}

	detector.setIOFiles(inputFile, outputFile);
	detector.sentenceDetect(detector.readFile());
    }

    public static void SentenceDetect() throws InvalidFormatException, IOException {
	String paragraph = "Hi. How are you? This is Mike.";

	// always start with a model, a model is learned from training data
	InputStream is = new FileInputStream("en-sent.bin");
	SentenceModel model = new SentenceModel(is);
	SentenceDetectorME sdetector = new SentenceDetectorME(model);

	String sentences[] = sdetector.sentDetect(paragraph);

	System.out.println(sentences[0]);
	System.out.println(sentences[1]);
	is.close();
    }

    public void sentenceDetect(String text) throws InvalidFormatException, IOException {
	String paragraph = text;

	// always start with a model, a model is learned from training data
	InputStream is = new FileInputStream("en-sent.bin");
	SentenceModel model = new SentenceModel(is);
	SentenceDetectorME sdetector = new SentenceDetectorME(model);

	String sentenceList[] = sdetector.sentDetect(paragraph);

	is.close();

	for (String sentence : sentenceList) {
	    writeFile(sentence.concat("\n\n"));
	}

	closeFiles();
    }

}
