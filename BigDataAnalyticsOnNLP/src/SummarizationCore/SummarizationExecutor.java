package SummarizationCore;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SummarizationExecutor {

    private final HashMap<String, Integer> map_1;
    private final HashMap<String, Integer> map_2;
    private BufferedReader reader;
    private BufferedWriter writer;
    private int sentenceCount;
    private final String[] sentences;
    private final float[][] matchRatio;
    private final float[] sentenceScore;
    private final int MAX;

    // Constructor to instantiate data structures
    public SummarizationExecutor() {
	MAX = 500;
	map_1 = new HashMap<>();
	map_2 = new HashMap<>();
	sentences = new String[MAX];
	matchRatio = new float[MAX][MAX];
	sentenceScore = new float[MAX];
	sentenceCount = 0;
    }

    // Open & close input-output files
    public void setIOFiles(File inputFile, File outputFile) {
	setInputFile(inputFile);
	setOutputFile(outputFile);
    }

    public void setInputFile(File inputFile) {
	try {
	    reader = new BufferedReader(new FileReader(inputFile));
	} catch (Exception e) {
	    System.out.println("Exception while setting input file:\t" + e.toString());
	}
    }

    public void setOutputFile(File outputFile) {
	try {
	    writer = new BufferedWriter(new FileWriter(outputFile));
	} catch (Exception e) {
	    System.out.println("Exception while setting output file:\t" + e.toString());
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

    // Find match between sentences
    private float intersection(String sentence_1, String sentence_2) {
	int wordMatchCount;
	Integer frequency;
	int wordCount_1;
	int wordCount_2;

	wordMatchCount = 0;
	map_1.clear();
	map_2.clear();

	findWords(sentence_1, map_1);
	findWords(sentence_2, map_2);

	wordCount_1 = map_1.size();
	wordCount_2 = map_2.size();

	for (Map.Entry<String, Integer> entrySet : map_1.entrySet()) {
	    frequency = map_2.get(entrySet.getKey());
	    if (frequency != null) {
		wordMatchCount += entrySet.getValue() * frequency;
	    }
	}

	return (float) (wordMatchCount * Math.sqrt(wordCount_1) * Math.sqrt(wordCount_2) / (wordCount_1 + wordCount_2));
    }

    private void findWords(String sentence, HashMap<String, Integer> map) {
	Integer frequency;

	for (String word : sentence.split(" ")) {
	    if (!word.isEmpty()) {
		frequency = map.get(word);
		if (frequency == null) {
		    frequency = 0;
		}
		map.put(word, ++frequency);
	    }
	}
    }

    // Perform summarization
    private void eraseStructures() {
	int i;

	sentenceCount = 0;
	Arrays.fill(sentences, null);
	Arrays.fill(sentenceScore, Float.MIN_VALUE);
	for (i = 0; i < MAX; i++) {
	    Arrays.fill(matchRatio[i], Float.MIN_VALUE);
	}
    }

    private int readText() {
	try {
	    String line;

	    eraseStructures();
	    while ((line = reader.readLine()) != null) {
		for (String sentence : line.split("[.!?]")) {
		    sentence = sentence.replace('-', ' ').replaceAll("[^A-Za-z0-9 ]", "");
		    sentences[sentenceCount++] = sentence;
		}
		if (sentenceCount > MAX - 100) {
		    break;
		}
	    }
	} catch (IOException ex) {
	    System.out.println("Exception while reading file:\t" + ex.toString());
	} finally {
	    return sentenceCount;
	}
    }

    private void writeFile(String text) {
	try {
	    writer.write(text);
	    writer.newLine();
	} catch (Exception e) {
	    System.out.println("Exception while writing file:\t" + e.toString());
	}
    }

    private void findSentenceScore() {
	int i;
	int j;
	float sum;

	for (i = 0; i < sentenceCount; i++) {
	    for (j = i + 1; j < sentenceCount; j++) {
		matchRatio[i][j] = matchRatio[j][i] = intersection(sentences[i].toLowerCase(), sentences[j].toLowerCase());
	    }
	}

	for (i = 0; i < sentenceCount; i++) {
	    sum = 0;
	    for (j = 0; j < sentenceCount; j++) {
		sum += matchRatio[i][j];
	    }
	    sentenceScore[i] = sum;
	}
    }

    private String makeSummary(int percentage) {
	int i;
	int j;
	int maxScoreIndex;
	float maxScoreValue;
	int frameLength;
	StringBuilder builder;

	frameLength = (int) (sentenceCount / (sentenceCount * percentage / 100.0f));
	builder = new StringBuilder();

	for (i = 0; i < sentenceCount; i += frameLength) {
	    maxScoreValue = Float.MIN_VALUE;
	    maxScoreIndex = Integer.MIN_VALUE;

	    for (j = i; j < sentenceCount && j < i + frameLength; j++) {
		if (sentenceScore[j] > maxScoreValue) {
		    maxScoreValue = sentenceScore[j];
		    maxScoreIndex = j;
		}
	    }

	    builder.append(sentences[maxScoreIndex]);
	    builder.append(".\n\n");
	}

	return builder.toString();
    }

    public String getSummary(int percentage) {
	readText();
	findSentenceScore();
	return makeSummary(percentage);
    }

    public void doSummarization(int percentage) {
	while (readText() > 0) {
	    findSentenceScore();
	    writeFile(makeSummary(percentage));
	}
	closeFiles();
    }

    // Main method
    public static void main(String[] args) {
	File inputFile;
	File outputFile;
	Scanner scanner;
	SummarizationExecutor summarizationNLP;

	scanner = new Scanner(System.in);
	inputFile = new File("gandhi.txt");
	outputFile = new File("summarizationOP.txt");
	summarizationNLP = new SummarizationExecutor();

	summarizationNLP.setIOFiles(inputFile, outputFile);

	System.out.print("Enter summarization percentage (multiples of 10):\t");
	summarizationNLP.doSummarization(scanner.nextInt());
    }

}
