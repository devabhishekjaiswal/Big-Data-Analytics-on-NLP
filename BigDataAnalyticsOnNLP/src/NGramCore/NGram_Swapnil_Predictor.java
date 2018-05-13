/*
 Swapnil Jain
 */
package NGramCore;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NGram_Swapnil_Predictor {

    HashMap<String, Integer> uniGramMap = new HashMap<>(); //<NGram, Frequency>
    HashMap<String, Integer> biGramMap = new HashMap<>();
    HashMap<String, Integer> hashCode = new HashMap<>();
    HashMap<Integer, String> reversehashCode = new HashMap<>();

    public static void main(String[] args) throws Exception {
	NGram_Swapnil_Predictor ngram = new NGram_Swapnil_Predictor();
	Scanner scan = new Scanner(System.in);
	String filename = "ngram_input.txt";

	ngram.doPrediction(filename);
    }

    private void generateNgrams(int N, String sent, HashMap<String, Integer> NGramMap) {
	String[] tokens = sent.split("\\s+"); //split sentence into tokens
	//GENERATE THE N-GRAMS
	for (int k = 0; k < (tokens.length - N + 1); k++) {
	    String s = "";
	    int start = k;
	    int end = k + N;
	    for (int j = start; j < end; j++) {
		s = s + " " + tokens[j];
	    }

	    //Add n-gram to a list
	    // System.out.println(s);
	    if (NGramMap.containsKey(s)) {
		int value = NGramMap.get(s);
		NGramMap.remove(s);
		NGramMap.put(s, value + 1);
	    } else {
		NGramMap.put(s, 1);
	    }
	    //	ngramList.add(s);
	}
	// printMap(NGramMap);
    }

    private void makeFile(HashMap<String, Integer> map, String filename) throws Exception {
	FileWriter fw = new FileWriter(filename);
	for (Map.Entry<String, Integer> entrySet : map.entrySet()) {
	    String key = entrySet.getKey();
	    Integer value = entrySet.getValue();
	    fw.write((key + " " + value + "\n"));
	}
	fw.close();
    }

    private String readFile(String filename) throws FileNotFoundException {
	Scanner scan_file = new Scanner(new File(filename));
	String str = "";
	while (scan_file.hasNextLine()) {
	    str += scan_file.nextLine();
	}
	str = str.replaceAll("[^a-zA-Z0-9']+", " ");
	//  System.out.println(str);
	return str;
    }

    private void printMap(HashMap<String, Integer> map) {
	for (Map.Entry<String, Integer> entrySet : map.entrySet()) {
	    String key = entrySet.getKey();
	    Integer value = entrySet.getValue();
	    System.out.println(key + " " + value);
	}
    }

    private void doPrediction(String filename) throws FileNotFoundException, Exception {
	String corpora = readFile(filename); //Punctuation Removed except '
	generateNgrams(1, corpora, uniGramMap);
	generateNgrams(2, corpora, biGramMap);
	makeFile(uniGramMap, "unigram.txt");
	makeFile(biGramMap, "bigram.txt");

	//Getting String-to-Integer Format;
	int value = 0;
	for (Map.Entry<String, Integer> entrySet : uniGramMap.entrySet()) {
	    String key = entrySet.getKey();
	    //Integer value1 = entrySet.getValue();
	    hashCode.put(key, value);
	    reversehashCode.put(value++, key);
	}
	makeFile(hashCode, "hashCode.txt");

	//Filling Matrix with Bigram Data.
	double[][] matrix = new double[value + 1][value + 1];
	for (Map.Entry<String, Integer> entrySet : biGramMap.entrySet()) {
	    String key = entrySet.getKey();
	    Integer value1 = entrySet.getValue();
	    Scanner scan = new Scanner(key);
	    String w1 = scan.next();
	    String w2 = scan.next();
	    matrix[hashCode.get(" " + w1)][hashCode.get(" " + w2)] = value1;
	}

	//Calculating probability (percentage) of each word.
	for (int i = 0; i < value; ++i) {
	    for (int j = 0; j < value; ++j) {
		matrix[i][j] /= uniGramMap.get(reversehashCode.get(i));
		matrix[i][j] = Math.round(matrix[i][j] * 100);
	    }
	}

	System.out.println(value);
	askForPredictions(matrix, value);
    }

    private void askForPredictions(double[][] matrix, int value) {
	Scanner scan = new Scanner(System.in);
	String query = "";
	query = scan.next();

	int queryHashCode = hashCode.get(" " + query);
	System.out.println(queryHashCode);

	System.out.println("This query has following predictions: ");
	for (int i = 0; i < value; ++i) {
	    if (matrix[queryHashCode][i] != 0) {
		System.out.println(reversehashCode.get(i).trim() + " (" + matrix[queryHashCode][i] + "%)");
	    }
	}
    }

}
