package StemmingCore;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class StemmingExecutor {

    // ArrayLists for prerequisite steps
    public ArrayList<String> prereqList_1a;
    public ArrayList<String> prereqList_1b;
    public ArrayList<String> prereqList_1c;
    public ArrayList<String> prereqList_2;
    public ArrayList<String> prereqList_3;
    public ArrayList<String> prereqList_4;
    public ArrayList<String> prereqList_5a;
    public ArrayList<String> prereqList_5b;

    // ArrayLists for regular expressions
    public ArrayList<String> regexList_1a;
    public ArrayList<String> regexList_1b;
    public ArrayList<String> regexList_1c;
    public ArrayList<String> regexList_2;
    public ArrayList<String> regexList_3;
    public ArrayList<String> regexList_4;
    public ArrayList<String> regexList_5a;
    public ArrayList<String> regexList_5b;

    // ArrayLists for beneficiary suffix
    public ArrayList<String> addList_1a;
    public ArrayList<String> addList_1b;
    public ArrayList<String> addList_1c;
    public ArrayList<String> addList_2;
    public ArrayList<String> addList_3;
    public ArrayList<String> addList_4;
    public ArrayList<String> addList_5a;
    public ArrayList<String> addList_5b;

    // ArrayLists for victim suffix
    public ArrayList<String> removeList_1a;
    public ArrayList<String> removeList_1b;
    public ArrayList<String> removeList_1c;
    public ArrayList<String> removeList_2;
    public ArrayList<String> removeList_3;
    public ArrayList<String> removeList_4;
    public ArrayList<String> removeList_5a;
    public ArrayList<String> removeList_5b;

    // Other attributes
    public BufferedReader reader;
    public BufferedWriter writer;
    public HashSet<Character> vowelSet;

    // Instantiate a new Stemmer object
    public StemmingExecutor() {
	try {
	    vowelSet = new HashSet<>();
	    createVowelSet();
	} catch (Exception ex) {
	    System.out.println("Exception:\t" + ex.toString());
	    System.exit(0);
	}
    }

    // Add vowels to the vowelSet
    private void createVowelSet() {
	vowelSet.add('a');
	vowelSet.add('e');
	vowelSet.add('i');
	vowelSet.add('o');
	vowelSet.add('u');
    }

    // Instantiate the BufferedReader
    public void setInputFile(String inputFile) {
	try {
	    reader = new BufferedReader(new FileReader(inputFile));
	} catch (Exception ex) {
	    System.out.println("Exception:\t" + ex.toString());
	    System.exit(0);
	}
    }

    // Instantiate the BufferedReader
    public void setInputFile(File inputFile) {
	try {
	    reader = new BufferedReader(new FileReader(inputFile));
	} catch (Exception ex) {
	    System.out.println("Exception:\t" + ex.toString());
	    System.exit(0);
	}
    }

    // Instantiate the BufferedWriter
    public void setOutputFile(String outputFile) {
	try {
	    writer = new BufferedWriter(new FileWriter(outputFile));
	} catch (Exception ex) {
	    System.out.println("Exception:\t" + ex.toString());
	    System.exit(0);
	}
    }

    // Instantiate the BufferedWriter
    public void setOutputFile(File inputFile) {
	try {
	    writer = new BufferedWriter(new FileWriter(inputFile));
	} catch (Exception ex) {
	    System.out.println("Exception:\t" + ex.toString());
	    System.exit(0);
	}
    }

    // Instantiate reader and writer
    public void setIOFiles(String inputFile, String outputFile) {
	setInputFile(inputFile);
	setOutputFile(outputFile);
    }

    // Instantiate reader and writer
    public void setIOFiles(File inputFile, File outputFile) {
	try {
	    reader = new BufferedReader(new FileReader(inputFile));
	    writer = new BufferedWriter(new FileWriter(outputFile));
	} catch (Exception e) {
	    System.out.println("Exception while instantiating File:\t" + e.toString());
	    System.exit(0);
	}
    }

    // Initialize ArrayLists of the specified step
    public void setStepLists(String stepName, ArrayList<String> prereqList, ArrayList<String> regexList, ArrayList<String> removeList, ArrayList<String> addList) {
	if (stepName.equals("1a")) {
	    this.prereqList_1a = prereqList;
	    this.regexList_1a = regexList;
	    this.removeList_1a = removeList;
	    this.addList_1a = addList;
	} else if (stepName.equals("1b")) {
	    this.prereqList_1b = prereqList;
	    this.regexList_1b = regexList;
	    this.removeList_1b = removeList;
	    this.addList_1b = addList;
	} else if (stepName.equals("1c")) {
	    this.prereqList_1c = prereqList;
	    this.regexList_1c = regexList;
	    this.removeList_1c = removeList;
	    this.addList_1c = addList;
	} else if (stepName.equals("2")) {
	    this.prereqList_2 = prereqList;
	    this.regexList_2 = regexList;
	    this.removeList_2 = removeList;
	    this.addList_2 = addList;
	} else if (stepName.equals("3")) {
	    this.prereqList_3 = prereqList;
	    this.regexList_3 = regexList;
	    this.removeList_3 = removeList;
	    this.addList_3 = addList;
	} else if (stepName.equals("4")) {
	    this.prereqList_4 = prereqList;
	    this.regexList_4 = regexList;
	    this.removeList_4 = removeList;
	    this.addList_4 = addList;
	} else if (stepName.equals("5a")) {
	    this.prereqList_5a = prereqList;
	    this.regexList_5a = regexList;
	    this.removeList_5a = removeList;
	    this.addList_5a = addList;
	} else if (stepName.equals("5b")) {
	    this.prereqList_5b = prereqList;
	    this.regexList_5b = regexList;
	    this.removeList_5b = removeList;
	    this.addList_5b = addList;
	} else {
	    System.out.println("\nInvalid step name");
	    System.exit(0);
	}
    }

    // Calculates and returns value of 'm'
    private int measure(String word, String suffix) {
	String stem;
	int i;
	int len;
	int measure;
	char chCrr;
	char chPrev;
	boolean seeingVowelSeq;

	len = word.length() - suffix.length();

	if (len <= 0) {
	    return 0;
	}

	stem = word.substring(0, len);
	measure = 0;
	seeingVowelSeq = false;
	chPrev = 'a';

	for (i = 0; i < len; i++) {
	    chCrr = stem.charAt(i);

	    if (vowelSet.contains(chCrr)) {
		// Read actual vowel
		seeingVowelSeq = true;
	    } else if (chCrr == 'y' && !vowelSet.contains(chPrev)) {
		// Read pseudo-vowel (y preceded by consonant)
		seeingVowelSeq = true;
	    } else if (seeingVowelSeq) {
		// Read consonant after vowel sequence
		seeingVowelSeq = false;
		measure++;
	    }

	    chPrev = chCrr;
	}

	return measure;
    }

    // Calculates and returns whether 'm' >= minimum or not
    private boolean measureSufficed(String word, String suffix, int minimum) {
	String stem;
	int i;
	int len;
	int measure;
	char chCrr;
	char chPrev;
	boolean seeingVowelSeq;

	len = word.length() - suffix.length();

	if (len <= 0) {
	    return minimum == 0;
	}

	stem = word.substring(0, len);
	measure = 0;
	seeingVowelSeq = false;
	chPrev = 'a';

	for (i = 0; i < len; i++) {
	    chCrr = stem.charAt(i);

	    if (vowelSet.contains(chCrr)) {
		// Read actual vowel
		seeingVowelSeq = true;
	    } else if (chCrr == 'y' && !vowelSet.contains(chPrev)) {
		// Read pseudo-vowel (y preceded by consonant)
		seeingVowelSeq = true;
	    } else if (seeingVowelSeq) {
		// Read consonant after vowel sequence
		seeingVowelSeq = false;
		measure++;
	    }

	    if (measure == minimum) {
		return true;
	    }

	    chPrev = chCrr;
	}

	return false;
    }

    // Returns whether stem contains a vowel
    private boolean containsVowel(String word, String suffix) {
	int i;
	int len;
	char chCrr;
	char chPrev;
	String stem;

	len = word.length() - suffix.length();

	if (len <= 0) {
	    return false;
	}

	stem = word.substring(0, len);
	chPrev = 'a';

	for (i = 0; i < len; i++) {
	    chCrr = stem.charAt(i);

	    if (vowelSet.contains(chCrr)) {
		return true;
	    } else if (chCrr == 'y' && !vowelSet.contains(chPrev)) {
		return true;
	    }
	}

	return false;
    }

    // Returns whether stem ends with the specified consonant
    private boolean endsWithSingleConsonant(String word, String suffix, char consonant) {
	String stem;
	int len;

	len = word.length() - suffix.length();

	if (len <= 0) {
	    return false;
	}

	stem = word.substring(0, len);

	if (len > 0 && stem.charAt(len - 1) == consonant) {
	    return true;
	} else {
	    return false;
	}
    }

    // Returns whether stem ends with any double consonants except those in the exceptions[] array
    private boolean endsWithDoubleConsonant(String word, String suffix, char[] exceptions) {
	String stem;
	char lastChar;
	char secondLastChar;
	int len;
	int i;

	len = word.length() - suffix.length();

	if (len <= 0) {
	    return false;
	}

	stem = word.substring(0, len);

	if (len >= 2) {
	    lastChar = stem.charAt(len - 1);
	    secondLastChar = stem.charAt(len - 2);

	    if (lastChar == secondLastChar && !vowelSet.contains(lastChar)) {
		// Ends with double consonant
		if (exceptions != null) {
		    for (i = 0; i < exceptions.length; i++) {
			if (lastChar == exceptions[i]) {
			    return false;
			}
		    }

		    return true;
		}
		return true;
	    }
	}

	return false;
    }

    // Returns whether stem ends with cvc where second c does not belong to those in the exceptions[] array
    private boolean endsWithCVC(String word, String suffix, char[] exceptions) {
	String stem;
	int len;
	int i;
	char chCrr;
	char chPrev;
	boolean C1;
	boolean V;
	boolean C2;

	len = word.length() - suffix.length();

	if (len <= 0) {
	    return false;
	}

	stem = word.substring(0, len);
	C1 = V = C2 = false;
	chCrr = 0;

	// Check for first c
	if (len >= 3) {
	    chPrev = (len >= 4) ? stem.charAt(len - 4) : 'a';
	    chCrr = stem.charAt(len - 3);
	    if (!vowelSet.contains(chCrr) && chCrr != 'y') {
		C1 = true;
	    } else if (chCrr == 'y' && !vowelSet.contains(chPrev)) {
		C1 = true;
	    }
	}

	// Check for v
	if (C1) {
	    chPrev = chCrr;
	    chCrr = stem.charAt(len - 2);
	    if (vowelSet.contains(chCrr)) {
		V = true;
	    } else if (chCrr == 'y' && !vowelSet.contains(chPrev)) {
		V = true;
	    }
	}

	// Check for second c
	if (V) {
	    chPrev = chCrr;
	    chCrr = stem.charAt(len - 1);
	    if (!vowelSet.contains(chCrr) && chCrr != 'y') {
		C2 = true;
	    } else if (chCrr == 'y' && !vowelSet.contains(chPrev)) {
		C2 = true;
	    }
	}

	// Check for exceptions in second c
	if (C2 && exceptions != null) {
	    for (i = 0; i < exceptions.length; i++) {
		if (chCrr == exceptions[i]) {
		    return false;
		}
	    }
	}

	return C2;
    }

    // Perform the Stemming process by reading input file, processing and writing output file
    public void doStemming() throws IOException {
	if (reader == null || writer == null) {
	    System.out.println("IO Files not specified");
	    System.exit(0);
	}

	String line;
	StringBuilder builder;
	String[] tokens;
	int i;

	// Read the input file line-by-line
	while ((line = reader.readLine()) != null) {
	    // Split the line that has been read into tokens containing only alphabets

	    line = line.toLowerCase();
	    tokens = line.split("[^a-z]");
	    builder = new StringBuilder();

	    // Read the line word by word and do stemming
	    for (String word : tokens) {
		if (!word.isEmpty()) {
		    // do stemming
		    word = stemByStep(word, prereqList_1a, regexList_1a, removeList_1a, addList_1a);
		    word = stemByStep(word, prereqList_1b, regexList_1b, removeList_1b, addList_1b);
		    word = stemByStep(word, prereqList_1c, regexList_1c, removeList_1c, addList_1c);
		    word = stemByStep(word, prereqList_2, regexList_2, removeList_2, addList_2);
		    word = stemByStep(word, prereqList_3, regexList_3, removeList_3, addList_3);
		    word = stemByStep(word, prereqList_4, regexList_4, removeList_4, addList_4);
		    word = stemByStep(word, prereqList_5a, regexList_5a, removeList_5a, addList_5a);
		    word = stemByStep(word, prereqList_5b, regexList_5b, removeList_5b, addList_5b);

		    // append the stemmed word to the line
		    builder.append(word);
		    builder.append(' ');
		}
	    }

	    // Write the line of stemmed words to the output file
	    writer.write(builder.toString());
	    writer.newLine();
	}

	closeReaderWriter();
    }

    // Close the reader and writer
    public void closeReaderWriter() {
	try {
	    reader.close();
	    writer.flush();
	    writer.close();
	} catch (Exception e) {
	    System.out.println("Exception while closing reader-writer:\t" + e.toString());

	}
    }

    // Returns whether the list of regular expressions match the word alongwith given suffix or not
    private boolean regexMatches(String word, String suffix, String regex) {
	// See if regular expression present or not
	if (regex.isEmpty()) {
	    return true;
	}

	String[] choiceList;
	String[] parts;
	boolean isTrue;
	boolean negation;
	int lim;

	choiceList = regex.split(",");
	isTrue = true;

	for (String choice : choiceList) {
	    if (!choice.startsWith("#")) {
		// Pre-defined expressions
		parts = choice.split("&");
		for (String exp : parts) {
		    // Check for negation ^
		    if (negation = (exp.charAt(0) == '^')) {
			exp = exp.substring(1);
		    }

		    if (exp.charAt(0) == 'm') {
			// Check for measure expression 'm'
			switch (exp.charAt(1)) {
			    case '=':
				isTrue = Character.getNumericValue(exp.charAt(2)) == measure(word, suffix);
				break;

			    case '<':
				isTrue = measureSufficed(word, suffix, Character.getNumericValue(exp.charAt(2)));
				break;

			    case '>':
				isTrue = Character.getNumericValue(exp.charAt(2)) < measure(word, suffix);
				break;

			    default:
				isTrue = false;
			}
		    } else if (exp.charAt(0) == '*') {
			if (exp.endsWith("*")) {
			    // Check for vowel expression *v*
			    isTrue = containsVowel(word, suffix);
			} else if (Character.isUpperCase(exp.charAt(1))) {
			    // Check for *S (ending with single consonant)
			    isTrue = endsWithSingleConsonant(word, suffix, exp.charAt(1));
			} else if (exp.charAt(1) == 'd') {
			    // Checl for *d (ending with double consonant)
			    isTrue = endsWithDoubleConsonant(word, suffix, null);
			} else if (exp.charAt(1) == 'o') {
			    // Check for *o (ending with cvc)
			    isTrue = endsWithCVC(word, suffix, null);
			} else {
			    isTrue = false;
			}
		    } else {
			isTrue = false;
		    }

		    // Perform negation is necessary
		    isTrue = (negation) ? !isTrue : isTrue;

		    // If current clause isn't true, no need to check for further clauses
		    if (!isTrue) {
			break;
		    }
		}
	    } else {
		// Remove the # symbol
		choice = choice.substring(1);
		// Checl for user-defined regex
		isTrue = word.matches(choice);
	    }

	    // If one complete expression has been found to match, then no need to check for other expressions
	    if (isTrue) {
		return true;
	    }
	}

	return false;
    }

    // stem the word based on the particular rule whose arraylists have been supplied
    public String stemByStep(String word, ArrayList<String> prereqList, ArrayList<String> regexList, ArrayList<String> removeList, ArrayList<String> addList) {
	int i;
	int j;
	int matchedRuleNo;
	boolean ruleMatched;
	String[] prereqRules;

	ruleMatched = false;
	matchedRuleNo = -1;

	// Stem words by rules
	for (i = 0; (i < regexList.size()) && (prereqList.get(i).isEmpty()); i++) {
	    // check if word matches the one of the regexes and whether it ends with corresponding suffix given in removeList
	    if (regexMatches(word, removeList.get(i), regexList.get(i)) && word.endsWith(removeList.get(i))) {
		// Remove the old suffix
		word = word.substring(0, word.length() - removeList.get(i).length());
		// Add the new suffix
		word = word + addList.get(i);

		matchedRuleNo = i + 1;
		ruleMatched = true;
		break;
	    }
	}

	// Stem words based on prerequisite rules
	if (ruleMatched) {
	    // If some rule has been matched, proceed to further rules
	    for (i++; i < prereqList.size(); i++) {
		// If some rule has some prerequistes, proceed further
		if (!prereqList.get(i).isEmpty()) {
		    // Determine all prerequisite rules by splitting using comma ,
		    prereqRules = prereqList.get(i).split(",");
		    for (j = 0; j < prereqRules.length; j++) {
			// If the same rule as one of those mentioned in the prerequisite list has matched earlier, then apply stemming
			if (matchedRuleNo == Integer.parseInt(prereqRules[j])) {
			    if (regexMatches(word, removeList.get(i), regexList.get(i)) && word.endsWith(removeList.get(i))) {
				// Remove the old suffix
				word = word.substring(0, word.length() - removeList.get(i).length());
				// Add the new suffix
				word = word + addList.get(i);
				return word;
			    }
			}
		    }
		}
	    }
	}

	return word;
    }

}
