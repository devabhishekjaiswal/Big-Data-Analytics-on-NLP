package POSTaggingCore;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import opennlp.tools.cmdline.PerformanceMonitor;
import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;

/*
 To change this license header, choose License Headers in Project Properties.
 To change this template file, choose Tools | Templates
 and open the template in the editor.
 */
/**

 @author vanila
 */
public class POSTaggingExecutor {

    private BufferedReader reader;
    private BufferedWriter writer;

    private POSTaggerME tagger;

    // Constructor to instantiate objects
    public POSTaggingExecutor() {
	POSModel model;

	model = new POSModelLoader().load(new File("en-pos-maxent.bin"));
	tagger = new POSTaggerME(model);
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

    // Print tokens and their tags in alignment
    private static void printTokens(String[] tokenList) {
	for (String token : tokenList) {
	    System.out.print(token + "\t");
	}
	System.out.println();
    }

    private static void printTags(String[] tokenList, String[] tagList) {
	int i;
	int j;
	int quotient;

	/*
	 for (String token: tokenList) {
	 System.out.print(token.length());

	 quotient = token.length() / 8;
	 for (j = 0; j <= quotient; j++) {
	 System.out.print("\t");
	 }
	 }
	 System.out.println();
	 */
	i = 0;
	for (String tag : tagList) {
	    System.out.print(tag);

	    quotient = tokenList[i].length() / 8;
	    for (j = 0; j <= quotient; j++) {
		System.out.print("\t");
	    }
	    i++;
	}
	System.out.println();
    }

    // Perform POS-Tagging
    public String getTaggingOutput() {
	try {
	    StringBuilder builder;
	    String line;
	    String[] tokenList;
	    String[] tagList;
	    int quotient;
	    int i;
	    int j;

	    builder = new StringBuilder();
	    while ((line = reader.readLine()) != null) {
		if (!line.isEmpty()) {
		    for (String sentence : line.split("[.!?]")) {
			// generate list of tokens and tags
			if (!sentence.isEmpty()) {
			    tokenList = WhitespaceTokenizer.INSTANCE.tokenize(sentence);
			    tagList = tagger.tag(tokenList);

			    // append token list to string-builder
			    for (String token : tokenList) {
				builder.append(token);
				builder.append('\t');
			    }
			    builder.append('\n');

			    // append tag list to string-builder
			    i = 0;
			    for (String tag : tagList) {
				builder.append(tag);

				quotient = tokenList[i].length() / 8;
				for (j = 0; j <= quotient; j++) {
				    builder.append('\t');
				}
				i++;
			    }
			    builder.append("\n\n");
			}
		    }
		}
	    }

	    return builder.toString();
	} catch (IOException ex) {
	    System.out.println("Exception while tagging:\t" + ex.toString());
	    return null;
	}
    }

    public void doTagging() {
	try {
	    String output = getTaggingOutput();
	    System.out.println(output);
	    writer.write(output);
	    writer.newLine();
	} catch (Exception e) {
	    System.out.println("Exception while writing:\t" + e.toString());
	}
	closeFiles();
    }

    // Main method
    public static void main(String[] args) throws IOException {
	POSTaggingExecutor.POSTag();
    }

    public static void POSTag() throws IOException {
	POSTaggingExecutor executor;

	POSModel model = new POSModelLoader()
		.load(new File("en-pos-maxent.bin"));
	PerformanceMonitor perfMon = new PerformanceMonitor(System.err, "sent");
	POSTaggerME tagger = new POSTaggerME(model);
	FileWriter writer = new FileWriter("POSTagging_OP.txt");

	String input = "Hi. How are you? This is Mike.The     institute was   established by the efforts of Prof. Murli Manohar Joshi, Union Minister of Human Resource Development, GoI. Realizing the vital significance of IT in the years to come, Prof. Joshi, himself a reputed academician, was instrumental in getting the project conceived, initiated and executed in record time. The 100 acre campus, situated at Deoghat, Jhalwa was designed on the Penrose Geometry pattern. The campus is envisaged to be a fully residential one, with all its faculty, staff and students housed in different pockets. All academic and residential areas are connected to the Institutes's network.";
	ObjectStream<String> lineStream = new PlainTextByLineStream(new StringReader(input));

	executor = new POSTaggingExecutor();
	executor.setInputFile(new File("gandhi.txt"));
	System.out.println(executor.getTaggingOutput());
	System.out.println();

	perfMon.start();

	String line;
	while ((line = lineStream.read()) != null) {
	    //System.out.println("Line = " + line);
	    String whitespaceTokenizerLine[] = WhitespaceTokenizer.INSTANCE
		    .tokenize(line);
	    String[] tags = tagger.tag(whitespaceTokenizerLine);

	    printTokens(whitespaceTokenizerLine);
	    printTags(whitespaceTokenizerLine, tags);

	    System.out.println("tokenCount = " + whitespaceTokenizerLine.length + "\t\ttagCount = " + tags.length);

	    POSSample sample = new POSSample(whitespaceTokenizerLine, tags);
	    System.out.println(sample.toString());
	    //writer.write(sample.toString());
	    //writer.write("\n\n");
	    perfMon.incrementCounter();
	}
	perfMon.stopAndPrintFinalResult();
	writer.close();
    }

}
