/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NGramCore;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**

 @author Shubham
 */
public class NGramExecutor {

    // HashMaps
    private HashMap<String, Integer> uniGramMap; //<NGram, Frequency>
    private HashMap<String, Integer> biGramMap;
    private HashMap<String, Integer> triGramMap;
    private HashMap<String, Integer> hashCodeMap;
    private HashMap<Integer, String> reversehashCodeMap;

    // Arrays and Matrices for Next-Word Prediction
    private float[][] biGramProbTable;
    private float[] biGramProbRow;
    private int[] indices;

    // Count of no of unique words in the document
    private int wordCount;

    // File Reader and Writers
    private BufferedReader reader;
    private BufferedWriter writer;

    // Constructor to instantiate maps
    public NGramExecutor() {
	uniGramMap = new HashMap<>();
	biGramMap = new HashMap<>();
	triGramMap = new HashMap<>();
	hashCodeMap = new HashMap<>();
	reversehashCodeMap = new HashMap<>();
    }

    // Set input-output files
    public void setInputFile(File inputFile) {
	try {
	    this.reader = new BufferedReader(new FileReader(inputFile));
	} catch (Exception e) {
	    System.out.println("Exception while setting input file:\t" + e.toString());
	    System.exit(0);
	}
    }

    public void setOutputFile(File outputFile) {
	try {
	    writer = new BufferedWriter(new FileWriter(outputFile));
	} catch (Exception e) {
	    System.out.println("Exception while setting output file");
	    System.exit(0);
	}
    }

    public void setIOFiles(File inputFile, File outputFile) {
	setInputFile(inputFile);
	setOutputFile(outputFile);
    }

    // Close input-output files
    private void closeFiles() {
	try {
	    reader.close();
	    writer.close();
	} catch (Exception e) {
	    System.out.println("Exception while closing files:\t" + e.toString());
	}
    }

    // Generate N-Grams
    public void generateNGrams() throws IOException {
	String line;
	String gram;
	String[] tokens;
	int k;

	while ((line = reader.readLine()) != null) {
	    line = line.toLowerCase();
	    //System.out.println("\n====\nline\n" + line);

	    if (!line.isEmpty()) {
		for (String sentence : line.split("[.?!]+")) {
		    // Remove all non-alphanumeric characters from the sentence
		    sentence = sentence.replaceAll("[^a-z0-9]+", " ");
		    sentence = sentence.trim();
		    //System.out.println("\n\nsentence:\t" + sentence);

		    // Split the sentence to obtain words
		    tokens = sentence.split("\\s+");
		    for (k = 0; k < tokens.length; k++) {
			// Uni-Grams
			gram = getNWords(tokens, k, k + 1);
			addGramToMap(uniGramMap, gram);
			//System.out.print(gram + "\t");

			// Bi-Gram
			gram = getNWords(tokens, k, k + 2);
			addGramToMap(biGramMap, gram);
			//System.out.println(gram + "\t");

			// Tri-Gram
			gram = getNWords(tokens, k, k + 3);
			addGramToMap(triGramMap, gram);
			//System.out.println(gram);
		    }
		}
	    }
	}
    }

    private String getNWords(String[] tokens, int start, int end) {
	if (end > tokens.length) {
	    return null;
	} else {
	    StringBuilder builder;
	    int i;

	    builder = new StringBuilder();
	    builder.setLength(0);

	    for (i = start; i < end; i++) {
		builder.append(' ');
		builder.append(tokens[i]);
	    }
	    return builder.toString();
	}
    }

    private void addGramToMap(HashMap<String, Integer> gramMap, String gram) {
	if (gram != null) {
	    if (gramMap.containsKey(gram)) {
		gramMap.put(gram, gramMap.get(gram) + 1);
	    } else {
		gramMap.put(gram, 1);
	    }
	}
    }

    // Write output file
    public void writeOutput(int n) throws IOException {
	switch (n) {
	    case 1:
		writeFile(uniGramMap);
		break;

	    case 2:
		writeFile(biGramMap);
		break;

	    case 3:
		writeFile(triGramMap);
		break;

	    default:
		System.out.println("Error: Unknown Gram " + n);
	}
	closeFiles();
    }

    private void writeFile(HashMap<String, Integer> gramMap) throws IOException {
	StringBuilder builder;

	builder = new StringBuilder();
	for (Map.Entry<String, Integer> entrySet : gramMap.entrySet()) {
	    builder.setLength(0);
	    builder.append(entrySet.getKey());
	    builder.append(' ');
	    builder.append(entrySet.getValue());
	    builder.append('\n');
	    writer.write(builder.toString());
	}
    }

    // Prediction
    private void createHashCodes() {
	//Getting String-to-Integer Format;
	String key;

	wordCount = 0;
	for (Map.Entry<String, Integer> entrySet : uniGramMap.entrySet()) {
	    key = entrySet.getKey();

	    hashCodeMap.put(key, wordCount);
	    reversehashCodeMap.put(wordCount++, key);
	}
    }

    private void createProbTable() {
	int i;
	int j;
	int spaceIndex;
	String key;
	String word_1;
	String word_2;
	Integer value;

	// Allocate mamory for dummy array to be used later on
	indices = new int[wordCount];
	biGramProbRow = new float[wordCount];

	// Create the frequency table
	// Filling Matrix with Bigram Data.
	biGramProbTable = new float[wordCount][wordCount];
	for (Map.Entry<String, Integer> entrySet : biGramMap.entrySet()) {
	    // Read key and values from biGramHashMap
	    key = entrySet.getKey();
	    value = entrySet.getValue();

	    // Retrieve seperate words from key
	    spaceIndex = key.indexOf(' ', 1);
	    word_1 = key.substring(0, spaceIndex);
	    word_2 = key.substring(spaceIndex);

	    // Store the value in table using hashCodes of the words as keys
	    biGramProbTable[hashCodeMap.get(word_1)][hashCodeMap.get(word_2)] = value;
	}

	/*
	 // Convert the frequency table into probability table
	 // Calculating probability (percentage) of each word.
	 for (i = 0; i < wordCount; ++i) {
	 for (j = 0; j < wordCount; ++j) {
	 biGramProbTable[i][j] /= uniGramMap.get(reversehashCodeMap.get(i));
	 //biGramProbTable[i][j] = Math.round(biGramProbTable[i][j] * 100);
	 }
	 }
	 */
    }

    public void preparePredictionTable() {
	createHashCodes();
	createProbTable();
    }

    public String[] getPredictionList(String word) {
	int i;
	int hashCode = 0;
	int probablesCount = 0;
	String[] probables;

	try {
	    word = word.toLowerCase();
	    hashCode = (this.hashCodeMap.get(word) == null) ? -1 : this.hashCodeMap.get(word);
	    //System.out.println("hashCode = " + hashCode);
	    probablesCount = (hashCode != -1) ? createProbablesList(hashCode) : 0;
	} catch (NullPointerException e) {
	    System.out.println("Something is null\nhashCodeMap " + (hashCodeMap == null) + "\nword = " + (word == null) + "\nthis = " + (this == null));
	} catch (Exception e) {
	    System.out.println("Just an exception");
	}

	//System.out.println("probables count = " + probablesCount);
	if (probablesCount > 1) {
	    quickSort(biGramProbRow, indices, hashCode, probablesCount - 1);
	}

	if (probablesCount > 0) {
	    probables = new String[probablesCount];
	    for (i = 0; i < probablesCount; i++) {
		probables[i] = reversehashCodeMap.get(indices[i]);
	    }
	    return probables;
	} else {
	    return null;
	}
    }

    private int createProbablesList(int hashCode) {
	int i;
	int j;

	for (i = 0, j = 0; i < wordCount; i++) {
	    if (biGramProbTable[hashCode][i] > 0) {
		indices[j] = i;
		biGramProbRow[j++] = biGramProbTable[hashCode][i];
	    }
	}

	return j;
    }

    // Sorting
    private void quickSort(float[] prob, int[] indices, int lo, int hi) {
	if (lo < hi) {
	    int pivotIndex;

	    pivotIndex = partition(prob, indices, lo, hi);
	    quickSort(prob, indices, lo, pivotIndex - 1);
	    quickSort(prob, indices, pivotIndex + 1, hi);
	}
    }

    private int partition(float[] prob, int[] indices, int lo, int hi) {
	int i;
	int j;
	float pivot;

	pivot = prob[hi];
	for (i = lo - 1, j = lo; j < hi; j++) {
	    if (prob[j] >= pivot) {
		swap(prob, ++i, j);
		swap(indices, i, j);
	    }
	}

	swap(prob, ++i, j);
	swap(indices, i, j);

	return i;
    }

    private void swap(float[] arr, int ind_1, int ind_2) {
	float temp;

	temp = arr[ind_1];
	arr[ind_1] = arr[ind_2];
	arr[ind_2] = temp;
    }

    private void swap(int[] arr, int ind_1, int ind_2) {
	int temp;

	temp = arr[ind_1];
	arr[ind_1] = arr[ind_2];
	arr[ind_2] = temp;
    }

    public static void main(String[] args) throws IOException {
	NGramExecutor nGram;
	File inputFile;
	File outputFile;
	String input;
	BufferedReader reader;
	String[] predictList;

	reader = new BufferedReader(new InputStreamReader(System.in));
	nGram = new NGramExecutor();
	inputFile = new File("IIIT_Allahabad.txt");
	outputFile = new File("NGram_OP.txt");

	nGram.setIOFiles(inputFile, outputFile);

	nGram.generateNGrams();
	nGram.preparePredictionTable();

	//nGram.writeOutput(1);
	//nGram.writeOutput(2);

	input = null;
	while (input == null || !input.equals("")) {
	    System.out.print("Enter word: ");
	    input = reader.readLine().toLowerCase();
	    predictList = nGram.getPredictionList(" " + input);
	    nGram.showPredList(predictList);
	}
    }

    private void showPredList(String[] list) {
	if (list == null) {
	    System.out.println("No predictions to make\n");
	} else {
	    System.out.println("The list is:-");
	    for (String word : list) {
		System.out.print(word + "\t");
	    }
	    System.out.println("\n");
	}
    }

}
