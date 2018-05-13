/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ParallelProcessingSimulation_Executors;

import ParallelProcessingSimulation_FileGenerators.Fragmenter_2;
import java.util.Scanner;

/**

 @author Y2K
 */
public class Caller {

    Fragmenter_2 fragmenter;
    Merger merger;
    String outputFile;
    int limit;
    int fragments;
    String[] unsortedFragments;
    String[] sortedFragments;

    public Caller(String inputFile, String outputFile, int limit, int fragments) {
	int i;

	this.outputFile = outputFile;
	this.limit = limit;
	this.fragments = fragments;
	this.unsortedFragments = new String[fragments];
	this.sortedFragments = new String[fragments];

	for (i = 0; i < fragments; i++) {
	    unsortedFragments[i] = "uS_frag_" + (i + 1);
	    sortedFragments[i] = "S_frag_" + (i + 1);
	}
	//this.fragmenter = new Fragmenter_2(inputFile, this.unsortedFragments, limit, fragments);
    }

    public void fragmentFile() {
	fragmenter.fragmentFile();
	fragmenter.closeFiles();
	System.out.println("File fragmented");
    }

    public void sortFragment(Sorter sorter, int index) {
	sorter.countFrequencies();
	sorter.writeOutput();
	sorter.closeFiles();
	System.out.println("Fragment " + index + " sorted");
    }

    public void mergeFragments() {
	merger = new Merger(sortedFragments, outputFile);
	merger.openFiles();
	merger.mergeFiles();
	System.out.println("\nFragments merged");
    }

    public static void main(String[] args) {
	final Caller caller;
	Thread[] thread;
	Scanner scanner;
	String inputFile;
	String outputFile;
	final int[] limit;
	int fragments;
	int i;
	int index;
	long startTime;
	long endTime;
	final String INPUT_PATH = "InputFiles/";
	final String OUTPUT_PATH = "OutputFiles/";
	final String extension = ".txt";

	scanner = new Scanner(System.in);

	/*
	 System.out.print("Enter input filename:\t");
	 inputFile = scanner.nextLine();

	 System.out.print("Enter output filename:\t");
	 outputFile = scanner.nextLine();

	 System.out.print("Enter no of fragments:\t");
	 fragments = scanner.nextInt();
	 */
	inputFile = "bigFile_2";
	outputFile = "outFile_MultiThreaded";
	fragments = 10;

	limit = new int[fragments + 1];
	thread = new Thread[fragments];

	/*
	 System.out.print("Enter the limit:\t");
	 limit[fragments] = scanner.nextInt();
	 */
	limit[fragments] = 1000000;

	caller = new Caller(inputFile, outputFile, limit[fragments], fragments);
	//caller.fragmentFile();

	for (i = 0; i < fragments; i++) {
	    limit[i] = i * (limit[fragments] / fragments);
	}

	for (i = 0; i < fragments; i++) {
	    final int j = i;
	    thread[i] = new Thread(() -> {
		Sorter sorter;
		sorter = new Sorter(limit[j], limit[j + 1], caller.unsortedFragments[j], caller.sortedFragments[j]);
		caller.sortFragment(sorter, j + 1);
	    });
	}

	startTime = System.currentTimeMillis();
	for (i = 0; i < fragments; i++) {
	    thread[i].start();
	}

	try {
	    for (i = 0; i < fragments; i++) {
		thread[i].join();
	    }
	} catch (InterruptedException e) {
	    System.out.println("Exception while running threads:\t" + e.toString());
	}

	caller.mergeFragments();
	endTime = System.currentTimeMillis();

	System.out.println("\nOutput file " + outputFile + ".txt successfully written");
	System.out.println("Execution Time:\t" + (endTime - startTime) / 1000.0f + " seconds");

	/*
	 if (Desktop.isDesktopSupported()) {
	 try {
	 Desktop.getDesktop().edit(new File(INPUT_PATH + inputFile +
	 extension));
	 Desktop.getDesktop().edit(new File(OUTPUT_PATH + outputFile +
	 extension));
	 } catch (Exception e) {
	 }
	 }
	 */
    }

}
