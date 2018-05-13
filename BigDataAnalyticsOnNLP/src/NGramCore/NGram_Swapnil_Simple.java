package NGramCore;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class NGram_Swapnil_Simple {
    
    HashMap<String, Integer> NGramMap = new HashMap<>(); //<NGram, Frequency>
    
    public static void main(String[] args) throws Exception {
        NGram_Swapnil_Simple ngram = new NGram_Swapnil_Simple();
        int n;
        Scanner scan = new Scanner(System.in);
       
        //Size of N-gram
        n = scan.nextInt();
        //FileName
        String filename = "ngram_input.txt";
        
        String sentence = ngram.readFile(filename);
        ngram.generateNgrams(n, sentence);
        ngram.printMap(ngram.NGramMap);
    }

    private void generateNgrams(int N, String sent) {
        String[] tokens = sent.split("\\s+"); //split sentence into tokens
        //GENERATE THE N-GRAMS
        for (int k = 0; k < (tokens.length - N + 1); k++) {
            String s = "";
            int start = k;
            int end = k + N;
	    
	    // Read n-gram from text
            for (int j = start; j < end; j++) {
                s = s + " " + tokens[j];
            }
	    
            //Add n-gram to a list if not already present
           // System.out.println(s);
            if(NGramMap.containsKey(s)) {
                int value = NGramMap.get(s);
                NGramMap.remove(s);
                NGramMap.put(s, value + 1);
            } else {
                NGramMap.put(s, 1);
            }
            //	ngramList.add(s);
        }
    }
    
    private void printMap(HashMap<String, Integer> map) throws Exception {
        FileWriter fw = new FileWriter("ngram_output3.txt");
        
        for (Map.Entry<String, Integer> entrySet : map.entrySet()) {
            String key = entrySet.getKey();
            Integer value = entrySet.getValue();
            fw.write(key + " " + value);
            
        } 
        fw.close();
    }
    
    private String readFile(String filename) throws FileNotFoundException {
        Scanner scan_file = new Scanner(new File(filename));
        String str = "";
        while(scan_file.hasNextLine()) str += scan_file.nextLine();
        str = str.replaceAll("[^a-zA-Z0-9']+"," ");
        return str;
    }

}
