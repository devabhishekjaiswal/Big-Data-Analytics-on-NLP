/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ParallelProcessingSimulation_Executors;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Y2K
 */
public class Sorter {

    int lowerLimit;
    int upperLimit;
    int[] frequency;
    Scanner scanner;
    FileWriter writer;

    public Sorter(int lowerLimit, int upperLimit, String inputFile, String outputFile) {
        String INPUT_PATH = "InputFiles/";
        String OUTPUT_PATH = "OutputFiles/";
        String extension = ".txt";

        try {
            this.lowerLimit = lowerLimit;
            this.upperLimit = upperLimit;
            this.frequency = new int[upperLimit - lowerLimit];
            this.scanner = new Scanner(new FileReader(INPUT_PATH + inputFile + extension));
            this.writer = new FileWriter(OUTPUT_PATH + outputFile + extension);
        } catch (IOException e) {
            System.out.println("Exception while opening files:\t" + e.toString());
            closeFiles();
            System.exit(0);
        }
    }

    public void countFrequencies() {
        int i;
        int number;

        while (scanner.hasNext()) {
            number = scanner.nextInt();
            number -= lowerLimit;
            frequency[number]++;
        }
    }

    public void writeOutput() {
        int i;
        int j;
        int frequency;

        try {
            for (i = lowerLimit; i < upperLimit; i++) {
                frequency = this.frequency[i - lowerLimit];
                for (j = 1; j <= frequency; j++) {
                    writer.write(Integer.toString(i).concat("\n"));
                }
            }
            writer.flush();
        } catch (IOException e) {
            System.out.println("Error while writing output file:\t" + e.toString());
            closeFiles();
        }
    }

    public void closeFiles() {
        try {
            scanner.close();
            writer.close();
        } catch (IOException e) {
            System.out.println("Exception while closing files:\t" + e.toString());
        }
    }

    public static void main(String[] args) {
        String inputFile;
        String outputFile;
        int lowerLimit;
        int upperLimit;
        Scanner scanner;
        Sorter sorter;
        long startTime;
        long endTime;

        scanner = new Scanner(System.in);

        /*
         System.out.print("Enter input filename:\t");
         inputFile = scanner.nextLine();

         System.out.print("Enter output filename:\t");
         outputFile = scanner.nextLine();

         System.out.print("Enter lower limit:\t");
         lowerLimit = scanner.nextInt();

         System.out.print("Enter upper limit:\t");
         upperLimit = scanner.nextInt();
         */
        inputFile = "bigFile_2";
        outputFile = "outFile_SingleThreaded";
        lowerLimit = 0;
        upperLimit = 1000000;

        startTime = System.currentTimeMillis();
        sorter = new Sorter(lowerLimit, upperLimit, inputFile, outputFile);

        sorter.countFrequencies();
        sorter.writeOutput();
        sorter.closeFiles();
        endTime = System.currentTimeMillis();

        System.out.println("Output file " + outputFile + ".txt successfully written");
        System.out.println("Exection Time:\t" + (endTime - startTime) / 1000.0f + " seconds");
    }

}
