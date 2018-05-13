/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ParallelProcessingSimulation_FileGenerators;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Y2K
 */
public class BigFileGenerator {

    int limit;
    int size;
    FileWriter fileWriter;

    public BigFileGenerator(String filename, int limit, int size) {
        try {
            this.size = size;
            this.limit = limit;
            this.fileWriter = new FileWriter("InputFiles/" + filename + ".txt");
        } catch (IOException e) {
            System.out.println("Exception while opening file:\t" + e.toString());
            System.exit(0);
        }
    }

    public void writeFile() {
        int i;
        Random random;

        try {
            random = new Random();
            for (i = 1; i <= size; i++) {
                fileWriter.write(Integer.toString(random.nextInt(limit)).concat("\n"));
            }
        } catch (IOException e) {
            System.out.println("Exception while writing file:\t" + e.toString());
        }
    }

    private void closeFile() {
        try {
            fileWriter.close();
        } catch (IOException ex) {
            System.out.println("Exception while closing file:\t" + ex.toString());
        }
    }

    public static void main(String[] args) {
        BigFileGenerator generator;
        Scanner scanner;
        String filename;
        int limit;
        int size;

        scanner = new Scanner(System.in);

        System.out.print("Enter filename:\t");
        filename = scanner.nextLine();

        System.out.print("Enter limit:\t");
        limit = scanner.nextInt();

        System.out.print("Enter size:\t");
        size = scanner.nextInt();

        generator = new BigFileGenerator(filename, limit, size);

        generator.writeFile();
        generator.closeFile();
    }

}
