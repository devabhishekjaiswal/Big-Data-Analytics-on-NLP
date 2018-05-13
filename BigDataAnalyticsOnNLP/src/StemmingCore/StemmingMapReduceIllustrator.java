/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StemmingCore;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;

/**

 @author Shubham
 */
public class StemmingMapReduceIllustrator extends StemmingExecutor {

    // My variables
    private LinkedHashMap<String, String> wordStemMap;
    private ArrayList<String> wordOrder;

    // New variables
    public File inputFile;
    public File outputFile;

    public StemmingMapReduceIllustrator() {
	super();
	wordStemMap = new LinkedHashMap<>();
	wordOrder = new ArrayList<>();
    }

    @Override
    public void doStemming() throws IOException {
	if (reader == null || writer == null) {
	    System.out.println("IO Files not specified");
	    System.exit(0);

	}

	//createWordList();
	//showWordList();
	//performStemming();
	//regenerateFile();
	stemming();
	closeReaderWriter();
    }

    @Override
    public void setIOFiles(File inputFile, File outputFile) {
	super.setIOFiles(inputFile, outputFile); //To change body of generated methods, choose Tools | Templates.
	this.inputFile = inputFile;
	this.outputFile = outputFile;
    }

    public void doStemming2() throws IOException {
	StemmerTerminal terminal = new StemmerTerminal();
	terminal.doStemming(inputFile, outputFile);
    }

    private void createWordList() throws IOException {
	String line;
	String[] tokens;
	StringBuilder builder;
	int i;

	i = 0;
	while ((line = reader.readLine()) != null) {
	    line = line.toLowerCase();
	    tokens = line.split("[^a-z]");
	    builder = new StringBuilder();

	    for (String word : tokens) {
		if (!word.isEmpty()) {
		    // add the word to the oredered-list
		    wordOrder.add(word);

		    // add the word to hashMap if not already present
		    if (!wordStemMap.containsKey(word)) {
			wordStemMap.put(word, null);
		    }
		}
	    }

	    // add null word to indicate new-line
	    wordOrder.add(null);

	    // Free up memory using garbage collector
	    i++;
	    if (i % 1000 == 0) {
		System.gc();
	    }
	}
    }

    private void showWordList() {
	for (String word : wordOrder) {
	    System.out.print(word + "\t");
	}
    }

    private void performStemming() {
	String stem;

	for (String word : wordStemMap.keySet()) {
	    if (word != null) {
		// do stemming
		stem = stemByStep(word, prereqList_1a, regexList_1a, removeList_1a, addList_1a);
		stem = stemByStep(stem, prereqList_1b, regexList_1b, removeList_1b, addList_1b);
		stem = stemByStep(stem, prereqList_1c, regexList_1c, removeList_1c, addList_1c);
		stem = stemByStep(stem, prereqList_2, regexList_2, removeList_2, addList_2);
		stem = stemByStep(stem, prereqList_3, regexList_3, removeList_3, addList_3);
		stem = stemByStep(stem, prereqList_4, regexList_4, removeList_4, addList_4);
		stem = stemByStep(stem, prereqList_5a, regexList_5a, removeList_5a, addList_5a);
		stem = stemByStep(stem, prereqList_5b, regexList_5b, removeList_5b, addList_5b);

		// store the result
		wordStemMap.put(word, stem);
	    }
	}
    }

    private void regenerateFile() throws IOException {
	StringBuilder builder;

	builder = new StringBuilder();
	for (String word : wordOrder) {
	    if (word != null) {
		// append the stem to stringbuilder
		builder.append(wordStemMap.get(word));
		builder.append(' ');
	    } else {
		// Write the builder to output file
		writer.write(builder.toString());
		writer.newLine();

		// clear the string builder
		builder = new StringBuilder();
	    }
	}
    }

    private void stemming() throws IOException {
	String line;
	String stem;
	String[] tokens;
	StringBuilder builder;
	int i;

	i = 0;
	while ((line = reader.readLine()) != null) {
	    line = line.toLowerCase();
	    tokens = line.split("[^a-z]");
	    builder = new StringBuilder();

	    for (String word : tokens) {
		if (!word.isEmpty()) {
		    // add the word to the oredered-list
		    //wordOrder.add(word);

		    // add the word and stem to hashMap if not already present
		    stem = word;
		    if (!wordStemMap.containsKey(word)) {
			// do stemming
			stem = stemByStep(word, prereqList_1a, regexList_1a, removeList_1a, addList_1a);
			stem = stemByStep(stem, prereqList_1b, regexList_1b, removeList_1b, addList_1b);
			stem = stemByStep(stem, prereqList_1c, regexList_1c, removeList_1c, addList_1c);
			stem = stemByStep(stem, prereqList_2, regexList_2, removeList_2, addList_2);
			stem = stemByStep(stem, prereqList_3, regexList_3, removeList_3, addList_3);
			stem = stemByStep(stem, prereqList_4, regexList_4, removeList_4, addList_4);
			stem = stemByStep(stem, prereqList_5a, regexList_5a, removeList_5a, addList_5a);
			stem = stemByStep(stem, prereqList_5b, regexList_5b, removeList_5b, addList_5b);

			// store the result
			wordStemMap.put(word, stem);
		    }
		    // append the stem to stringBuilder
		    builder.append(stem);
		    builder.append(' ');
		}
	    }

	    // output line to writer and append with new-line
	    writer.write(builder.toString());
	    writer.newLine();

	    // Free up memory using garbage collector
	    i++;
	    if (i % 100 == 0) {
		System.gc();
	    }
	}
    }

}
