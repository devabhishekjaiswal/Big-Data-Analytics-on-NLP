/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ParallelProcessingSimulation_Executors;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;
import org.apache.commons.io.IOUtils;

public class Merger {

    String[] sourceFileNames;
    String destinationFileName;
    File[] sourceFiles;
    File destinationFile;

    public Merger(String[] sourceFileNames, String destinationFileName) {
        this.sourceFileNames = sourceFileNames;
        this.destinationFileName = destinationFileName;
        this.sourceFiles = new File[sourceFileNames.length];
    }

    public void openFiles() {
        int i;
        String SOURCE_PATH = "OutputFiles/";
        String DESTINATION_PATH = "OutputFiles/";
        String extension = ".txt";

        try {
            destinationFile = new File(DESTINATION_PATH + destinationFileName + extension);
            if (destinationFile.exists()) {
                destinationFile.delete();
                destinationFile = new File(DESTINATION_PATH + destinationFileName + extension);
            }
            for (i = 0; i < sourceFiles.length; i++) {
                sourceFiles[i] = new File(SOURCE_PATH + sourceFileNames[i] + extension);
            }
        } catch (Exception e) {
            System.out.println("Exception:\t" + e.toString());
        }
    }

    public void mergeFiles() {
        try {
            IOCopier.joinFiles(destinationFile, sourceFiles);
        } catch (IOException e) {
            System.out.println("Exception while merging files:\t" + e.toString());
        }
    }

    public static void main(String[] args) throws Exception {
        Merger merger;
        Scanner scanner;
        String[] sourceFileNames;
        String destinationFileName;
        String input;
        int sourceFileCount;
        int i;

        scanner = new Scanner(System.in);

        System.out.print("Enter destination filename:\t");
        destinationFileName = scanner.nextLine();

        System.out.print("Enter source file count:\t");
        sourceFileCount = scanner.nextInt();

        sourceFileNames = new String[sourceFileCount];
        scanner.nextLine();

        System.out.println("Enter source filenames:-");
        for (i = 0; i < sourceFileCount; i++) {
            sourceFileNames[i] = scanner.nextLine();
        }

        merger = new Merger(sourceFileNames, destinationFileName);
        merger.openFiles();
        merger.mergeFiles();
    }
}

class IOCopier {

    public static void joinFiles(File destination, File[] sources)
            throws IOException {
        OutputStream output = null;
        try {
            output = createAppendableStream(destination);
            for (File source : sources) {
                appendFile(output, source);
            }
        } finally {
            IOUtils.closeQuietly(output);
        }
    }

    private static BufferedOutputStream createAppendableStream(File destination)
            throws FileNotFoundException {
        return new BufferedOutputStream(new FileOutputStream(destination, true));
    }

    private static void appendFile(OutputStream output, File source)
            throws IOException {
        InputStream input = null;
        try {
            input = new BufferedInputStream(new FileInputStream(source));
            IOUtils.copy(input, output);
        } finally {
            IOUtils.closeQuietly(input);
        }
    }
}
