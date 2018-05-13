/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ParallelProcessingSimulation_FileGenerators;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Y2K
 */
public class Fragmenter_2 {

    FileWriter[] writer;
    Scanner scanner;
    int fragments;
    int[] limit;

    public Fragmenter_2(String inputFile, String[] outputFiles, int limit, int fragments) {
        String PATH = "InputFiles/";
        String extension = ".txt";
        int i;

        try {
            this.fragments = fragments;
            this.limit = new int[fragments];
            this.scanner = new Scanner(new FileReader(PATH + inputFile + extension));
            this.writer = new FileWriter[fragments];

            for (i = 0; i < fragments; i++) {
                this.limit[i] = (i + 1) * (limit / fragments);
            }

            for (i = 0; i < fragments; i++) {
                writer[i] = new FileWriter(PATH + outputFiles[i] + extension);
            }
        } catch (IOException e) {
            System.out.println("Exception while instantiating reader-writers:\t" + e.toString());
            System.exit(0);
        }
    }

    public void fragmentFile() {
        int number;
        int i;

        try {
            while (scanner.hasNext()) {
                number = scanner.nextInt();

                for (i = 0; i < fragments; i++) {
                    if (number < limit[i]) {
                        writer[i].write(number + "\n");
                        break;
                    }
                }
            }

            for (i = 0; i < fragments; i++) {
                writer[i].close();
            }
        } catch (IOException e) {
            System.out.println("Error while reading-writing files:\t" + e.toString());
        }
    }

    public void closeFiles() {
        int i;

        try {
            scanner.close();
            for (i = 0; i < fragments; i++) {
                writer[i].close();
            }
        } catch (IOException e) {
            System.out.println("Exception while closing files:\t" + e.toString());
        }
    }

    public static void main(String[] args) {
        Fragmenter_2 fragmenter;
        String inputFile;
        String[] outputFiles;
        String outputFilename;
        int limit;
        int fragments;
        Scanner scanner;
        int i;

        scanner = new Scanner(System.in);

        System.out.print("Enter input filename:\t\t");
        inputFile = scanner.nextLine();

        System.out.print("Enter no of fragments:\t\t");
        fragments = scanner.nextInt();

        scanner.nextLine();
        outputFiles = new String[fragments];

        System.out.print("Enter output filename:\t\t");
        outputFilename = scanner.nextLine();

        for (i = 0; i < fragments; i++) {
            outputFiles[i] = outputFilename + "_" + (i + 1);
        }

        System.out.print("Enter limit:\t\t\t");
        limit = scanner.nextInt();

        fragmenter = new Fragmenter_2(inputFile, outputFiles, limit, fragments);

        fragmenter.fragmentFile();
        fragmenter.closeFiles();
    }

}
