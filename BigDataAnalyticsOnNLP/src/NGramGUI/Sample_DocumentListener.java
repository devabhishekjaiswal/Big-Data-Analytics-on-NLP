package NGramGUI;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Utilities;

public class Sample_DocumentListener {

    JTextArea textArea;
    JFrame frame;
    DocumentListener documentListener;

    public Sample_DocumentListener() {
	frame = new JFrame("Example");
	textArea = new JTextArea(20, 50);
	documentListener = new MyDocumentListener(textArea);
    }

    void createFrame() {
	frame.setContentPane(textArea);
	frame.pack();
    }

    void addListeners() {
	textArea.getDocument().addDocumentListener(documentListener);
    }

    void showFrame() {
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setVisible(true);
    }

    public static void main(String[] args) {
	Sample_DocumentListener dle;

	dle = new Sample_DocumentListener();

	dle.createFrame();
	dle.addListeners();
	dle.showFrame();
    }

}

class MyDocumentListener implements DocumentListener {

    JTextArea textArea;
    String lastWord;
    int start;
    int end;
    boolean read;

    public MyDocumentListener(JTextArea textArea) {
	this.textArea = textArea;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
	checkLastWord();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
	checkLastWord();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
	checkLastWord();
    }

    protected void checkLastWord() {
	try {
	    start = Utilities.getWordStart(textArea, textArea.getCaretPosition());
	    if (read) {
		end = Utilities.getWordEnd(textArea, textArea.getCaretPosition());
	    }
	    String text = textArea.getDocument().getText(start, end - start);

	    if (lastWord == null || text.startsWith(lastWord)) {
		lastWord = text;
		read = true;
	    } else {
		if (!lastWord.isEmpty()) {
		    System.out.println(lastWord);
		}
		lastWord = null;
		read = true;
	    }

	} catch (BadLocationException e) {
	    end--;
	    read = false;
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

}
