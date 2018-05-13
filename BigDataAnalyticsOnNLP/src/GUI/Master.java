package GUI;

import NGramCore.NGramExecutor;
import NGramGUI.NGramPanel;
import POSTaggingCore.POSTaggingExecutor;
import POSTaggingGUI.POSTaggingPanel;
import StemmingCore.StemmingExecutor;
import StemmingCore.StemmingMapReduceIllustrator;
import StemmingGUI.StemmingPanel;
import StemmingGUI.StepPanel;
import SummarizationCore.SummarizationExecutor;
import SummarizationGUI.SummarizationPanel;
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Master extends javax.swing.JFrame implements ActionListener {

    private final JTabbedPane tabbedPane;
    private final JButton buttonInput;
    private final JButton buttonOutput;
    private final JButton buttonExecute;
    private final JLabel labelInput;
    private final JLabel labelOutput;
    private final JFileChooser fileChooser;
    private File inputFile;
    private File outputFile;
    private final StemmingExecutor NLP_Stemming;
    private final StemmingPanel Panel_Stemming;
    private final StemmingMapReduceIllustrator NLP_stemming_2;
    private final NGramExecutor NLP_NGram;
    private final NGramPanel Panel_NGram;
    private final SummarizationExecutor NLP_Summarization;
    private final SummarizationPanel Panel_Summarization;
    private final POSTaggingExecutor NLP_POSTagging;
    private final POSTaggingPanel Panel_POSTagging;

    public Master() throws AWTException {
	tabbedPane = new JTabbedPane();
	buttonInput = new JButton("Select Input File");
	buttonOutput = new JButton("Select Output File");
	buttonExecute = new JButton("Execute");
	labelInput = new JLabel("No input file selected");
	labelOutput = new JLabel("No output file specified");
	fileChooser = new JFileChooser();
	fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Text Files", "txt", "text", "rtf"));
	NLP_Stemming = new StemmingExecutor();
	Panel_Stemming = new StemmingPanel(this);
	NLP_stemming_2 = new StemmingMapReduceIllustrator();
	NLP_NGram = new NGramExecutor();
	Panel_NGram = new NGramPanel(NLP_NGram);
	NLP_Summarization = new SummarizationExecutor();
	Panel_Summarization = new SummarizationPanel();
	NLP_POSTagging = new POSTaggingExecutor();
	Panel_POSTagging = new POSTaggingPanel();
    }

    public static void main(String args[]) throws AWTException {
	Master master;

	try {
	    for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
		if ("Nimbus".equals(info.getName())) {
		    javax.swing.UIManager.setLookAndFeel(info.getClassName());
		    break;
		}
	    }
	} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
	    java.util.logging.Logger.getLogger(Master.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	}

	//</editor-fold>
	master = new Master();
	master.createTabs();
	master.createButtons();
	master.addListeners();
	master.showFrame();

    }

    private void showFrame() {
	final int SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
	final int SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
	final int FRAME_WIDTH = 800;
	final int FRAME_HEIGHT = 500;

	setTitle("Master");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	pack();
	setBounds((SCREEN_WIDTH - FRAME_WIDTH) / 2, (SCREEN_HEIGHT - FRAME_HEIGHT) / 2 - FRAME_HEIGHT / 5, getWidth(), getHeight());
	setVisible(true);
    }

    private void createTabs() {
	JPanel panel;
	JScrollPane scrollPane;

	panel = new JPanel(new GridLayout(1, 1));
	scrollPane = new JScrollPane();

	scrollPane.setViewportView(Panel_Stemming);
	scrollPane.setPreferredSize(new Dimension(800, 500));

	tabbedPane.addTab("Stemming", scrollPane);
	tabbedPane.addTab("N Gram", Panel_NGram);
	tabbedPane.addTab("Summarization", Panel_Summarization);
	tabbedPane.addTab("POS Tagging", Panel_POSTagging);

	JComponent panel4 = new JPanel();
	tabbedPane.addTab("Tab 5", panel4);

	// add all layouts to frame
	panel.add(tabbedPane);
	tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	this.getContentPane().add(panel, BorderLayout.CENTER);
    }

    private void createButtons() {
	JPanel panelGrid;
	JPanel panelEntire;

	panelGrid = new JPanel();
	panelEntire = new JPanel();

	panelGrid.setLayout(new GridLayout(2, 2, 20, 5));
	panelEntire.setLayout(new BoxLayout(panelEntire, BoxLayout.Y_AXIS));

	panelGrid.add(labelInput);
	panelGrid.add(labelOutput);
	panelGrid.add(buttonInput);
	panelGrid.add(buttonOutput);

	panelEntire.add(panelGrid);
	panelEntire.add(buttonExecute);

	this.getContentPane().add(panelEntire, BorderLayout.PAGE_END);
    }

    private void addListeners() {
	buttonInput.addActionListener(this);
	buttonOutput.addActionListener(this);
	buttonExecute.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	int returnVal;

	if (e.getSource() == buttonInput) {
	    returnVal = fileChooser.showOpenDialog(this);
	    if (returnVal == JFileChooser.APPROVE_OPTION) {
		inputFile = fileChooser.getSelectedFile();
		labelInput.setText(inputFile.getName());
	    }
	} else if (e.getSource() == buttonOutput) {
	    returnVal = fileChooser.showSaveDialog(this);
	    if (returnVal == JFileChooser.APPROVE_OPTION) {
		outputFile = fileChooser.getSelectedFile();
		labelOutput.setText(outputFile.getName());
	    }
	} else {
	    // Execute the current operation
	    switch (tabbedPane.getSelectedIndex()) {
		case 0:	// Stemming
		    doStemming();
		    break;

		case 1:
		    try {
			// N-Gram
			doNGram(outputFile != null);
		    } catch (AWTException ex) {
			System.out.println("Exception in NGram:\t" + ex.toString());
		    }
		    break;

		case 2:
		    doSummariazation();
		    break;

		case 3:
		    doPOSTagging();
		    break;

		default: // do nothing
	    }
	}
    }

    // Execute Stemming Process
    private void doStemming() {
	String[] stepNames;
	int i;

	if (inputFileNotSet() || outputFileNotSet()) {
	    System.out.println("Input / Output Files not set");
	    return;
	}

	NLP_Stemming.setIOFiles(inputFile, outputFile);
	Panel_Stemming.createAllLists();

	i = 0;
	stepNames = new String[]{"1a", "1b", "1c", "2", "3", "4", "5a", "5b"};

	// Read data from GUI and assign to lists of stemmer class
	for (StepPanel panel : Panel_Stemming.stepPanels) {
	    NLP_Stemming.setStepLists(stepNames[i++], panel.prereqList, panel.regexList, panel.removeList, panel.addList);
	    //NLP_stemming_2.setStepLists(stepNames[i++], panel.prereqList, panel.regexList, panel.removeList, panel.addList);
	}

	try {
	    NLP_Stemming.doStemming();
	    //NLP_stemming_2.doStemming2();
	} catch (IOException ex) {
	    System.out.println("Exception while stemming:\t" + ex.toString());
	} finally {
	    System.out.println("File Stemmed\nOutput file written\n");
	}

	openFiles();

    }

    // Execute N-Gram Process
    private void doNGram(boolean outputToFile) throws AWTException {
	try {
	    if (inputFileNotSet()) {
		System.out.println("Input File not set");
		return;
	    }
	    NLP_NGram.setInputFile(inputFile);

	    NLP_NGram.generateNGrams();
	    NLP_NGram.preparePredictionTable();

	    if (outputToFile) {
		if (outputFileNotSet()) {
		    System.out.println("Output File not set");
		    return;
		}
		NLP_NGram.setOutputFile(outputFile);
		NLP_NGram.writeOutput(Panel_NGram.getjComboBox().getSelectedIndex() + 1);
		openFiles();
	    }

	    Panel_NGram.addListeners();
	    Panel_NGram.toggleEditor(true);
	} catch (IOException ex) {
	    System.out.println("Exception in NGram:\t" + ex.toString());
	}
    }

    // Execute Summarization Process
    private void doSummariazation() {
	if (Panel_Summarization.useEditor()) {
	    try {
		File temporaryFile;
		FileWriter fileWriter;

		temporaryFile = new File("OutputFiles/SummarizationTemp.txt");
		fileWriter = new FileWriter(temporaryFile);

		fileWriter.write(Panel_Summarization.getEditorText());
		fileWriter.close();

		Panel_Summarization.toggleEditing(true);
		NLP_Summarization.setInputFile(temporaryFile);
		//System.out.println("SUMMARY IS:-\n");
		//System.out.println(NLP_Summarization.getSummary(Panel_Summarization.getPercentage()));
		Panel_Summarization.setEditorText(NLP_Summarization.getSummary(Panel_Summarization.getPercentage()));
		Panel_Summarization.toggleEditing(false);
	    } catch (IOException ex) {
		System.out.println("Error while creating temporary file:\t" + ex.toString());
	    }
	} else {
	    if (inputFileNotSet() || outputFileNotSet()) {
		System.out.println("Input / Output Files not set");
		return;
	    }

	    NLP_Summarization.setIOFiles(inputFile, outputFile);
	    NLP_Summarization.doSummarization(Panel_Summarization.getPercentage());
	    openFiles();
	}
    }

    // Execute POSTagging Process
    private void doPOSTagging() {
	if (Panel_POSTagging.useEditor()) {
	    try {
		File temporaryFile;
		FileWriter fileWriter;

		temporaryFile = new File("OutputFiles/SummarizationTemp.txt");
		fileWriter = new FileWriter(temporaryFile);

		fileWriter.write(Panel_POSTagging.getEditorText());
		fileWriter.close();

		NLP_POSTagging.setInputFile(temporaryFile);
		Panel_POSTagging.setEditorText(NLP_POSTagging.getTaggingOutput());
	    } catch (Exception e) {
		System.out.println("Exception while doing POSTagging:\t" + e.toString());
	    }
	} else {
	    if (inputFileNotSet() || outputFileNotSet()) {
		System.out.println("Input / Output file not set");
		return;
	    }

	    NLP_POSTagging.setIOFiles(inputFile, outputFile);
	    NLP_POSTagging.doTagging();
	    //System.out.println(NLP_POSTagging.getTaggingOutput());
	    openFiles();
	}
    }

    private boolean inputFileNotSet() {
	return inputFile == null;
    }

    private boolean outputFileNotSet() {
	return outputFile == null;
    }

    private void openFiles() {
	if (Desktop.isDesktopSupported()) {
	    try {
		Desktop.getDesktop().edit(inputFile);
		Desktop.getDesktop().edit(outputFile);
	    } catch (Exception e) {
	    }
	}
    }

}
