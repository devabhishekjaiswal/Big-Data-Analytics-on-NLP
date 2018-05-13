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
public class Fragmenter {

    FileWriter writer_1;
    FileWriter writer_2;
    FileWriter writer_3;
    FileWriter writer_4;
    Scanner scanner;
    int limit;

    public Fragmenter(String inputFile, String outputFile_1, String outputFile_2, String outputFile_3, String outputFile_4, int limit) {
        String PATH = "InputFiles/";
        String extension = ".txt";

        try {
            this.limit = limit;
            scanner = new Scanner(new FileReader(PATH + inputFile + extension));
            writer_1 = new FileWriter(PATH + outputFile_1 + extension);
            writer_2 = new FileWriter(PATH + outputFile_2 + extension);
            writer_3 = new FileWriter(PATH + outputFile_3 + extension);
            writer_4 = new FileWriter(PATH + outputFile_4 + extension);
        } catch (IOException e) {
            System.out.println("Exception while instantiating reader-writers:\t" + e.toString());
            System.exit(0);
        }
    }

    public Fragmenter(String inputFile, String[] outputFiles, int limit) {
        this(inputFile, outputFiles[0], outputFiles[1], outputFiles[2], outputFiles[3], limit);
    }

    public void fragmentFile() {
        int number;
        int firstLim;
        int secondLim;
        int thirdLim;

        firstLim = limit / 4;
        secondLim = limit / 2;
        thirdLim = firstLim * 3;

        try {
            while(scanner.hasNext()) {
                number = scanner.nextInt();

                if (number < firstLim) {
                    writer_1.write(Integer.toString(number).concat("\n"));
                } else if (number < secondLim) {
                    writer_2.write(Integer.toString(number).concat("\n"));
                } else if (number < thirdLim) {
                    writer_3.write(Integer.toString(number).concat("\n"));
                } else {
                    writer_4.write(Integer.toString(number).concat("\n"));
                }
            }
            writer_1.flush();
            writer_2.flush();
            writer_3.flush();
            writer_4.flush();
        } catch (IOException e) {
            System.out.println("Error while reading-writing files:\t" + e.toString());
        }
    }

    public void closeFiles() {
        try {
            scanner.close();
            writer_1.close();
            writer_2.close();
            writer_3.close();
            writer_4.close();
        } catch (IOException e) {
            System.out.println("Exception while closing files:\t" + e.toString());
        }
    }

    public static void main(String[] args) {
        Fragmenter fragmenter;
        String inputFile;
        String outputFile_1;
        String outputFile_2;
        String outputFile_3;
        String outputFile_4;
        int limit;
        Scanner scanner;

        scanner = new Scanner(System.in);

        System.out.print("Enter input filename:\t\t");
        inputFile = scanner.nextLine();

        System.out.print("Enter output_1 filename:\t");
        outputFile_1 = scanner.nextLine();

        System.out.print("Enter output_2 filename:\t");
        outputFile_2 = scanner.nextLine();

        System.out.print("Enter output_3 filename:\t");
        outputFile_3 = scanner.nextLine();

        System.out.print("Enter output_4 filename:\t");
        outputFile_4 = scanner.nextLine();

        System.out.print("Enter limit:\t\t\t");
        limit = scanner.nextInt();

        fragmenter = new Fragmenter(inputFile, outputFile_1, outputFile_2, outputFile_3, outputFile_4, limit);

        fragmenter.fragmentFile();
        fragmenter.closeFiles();
    }

}
