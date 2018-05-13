/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StemmingGUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Shubham
 */
public class StepPanel extends JPanel implements ActionListener {

    public String stepName;
    public ArrayList<RowPanel> panelList;
    public ArrayList<String> prereqList;
    public ArrayList<String> regexList;
    public ArrayList<String> addList;
    public ArrayList<String> removeList;
    private JFrame frame;

    // Constructor to create the main panel of the rule
    private StepPanel(String stepName, ArrayList<String> prereqList, ArrayList<String> regexList, ArrayList<String> addList, ArrayList<String> removeList) {
	super(new BorderLayout());
	this.stepName = stepName;
	this.prereqList = prereqList;
	this.regexList = regexList;
	this.addList = addList;
	this.removeList = removeList;
	this.panelList = new ArrayList<>();
	this.createMainPanel(true);
    }

    public StepPanel(String stepName, ArrayList<String> prereqList, ArrayList<String> regexList, ArrayList<String> addList, ArrayList<String> removeList, JFrame frame) {
	this(stepName, prereqList, regexList, addList, removeList);
	this.frame = frame;
    }

    // Creates the lists of suffixes by reading textFields
    public void createSuffixRegexList() {
	int i;

	prereqList.clear();
	regexList.clear();
	addList.clear();
	removeList.clear();

	for (i = 0; i < panelList.size(); i++) {
	    if (panelList.get(i).checkBox.isSelected()) {
		prereqList.add(panelList.get(i).textFieldPrereq.getText().replaceAll(" ", ""));
		regexList.add(panelList.get(i).textFieldRegex.getText().replaceAll(" ", ""));
		addList.add(panelList.get(i).textFieldAdd.getText().replaceAll(" ", ""));
		removeList.add(panelList.get(i).textFieldRemove.getText().replaceAll(" ", ""));
	    }
	}
    }

    // Creates the list of panels by reading the suffixLists
    private void createPanelList() {
	int i;

	removeAllListeners();
	panelList.clear();

	for (i = 0; i < removeList.size(); i++) {
	    panelList.add(new RowPanel(prereqList.get(i), regexList.get(i), removeList.get(i), addList.get(i)));
	}

	addAllListeners();
    }

    // Creates the main panel of the rule by
    // either reading suffixLists and then creating rulePanels
    // or reading the textFields and then creating suffixLists
    private void createMainPanel(boolean readSuffixList) {
	if (readSuffixList) {
	    createPanelList();
	}
	addRulePanelsToMainPanel(!readSuffixList);
    }

    // Adds all the rulePanels to the main panel of the rule
    private void addRulePanelsToMainPanel(boolean writeSuffixList) {
	/*
	 * int i;
	 * JPanel panel;
	 *
	 * removeAllListeners();
	 * removeAll();
	 *
	 * if (writeSuffixList) {
	 * createSuffixList();
	 * }
	 *
	 * panel = new JPanel();
	 * panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	 * setLayout(new BorderLayout());
	 *
	 * for (i = 0; i < panelList.size(); i++) {
	 * panel.add(panelList.get(i));
	 * }
	 *
	 * add(panel, BorderLayout.PAGE_START);
	 * addAllListeners();
	 * add(new JLabel("Rule " + stepName), BorderLayout.PAGE_START);
	 */

	int i;
	JPanel panelOuter;
	JPanel panelInner;

	removeAllListeners();
	removeAll();

	if (writeSuffixList) {
	    createSuffixRegexList();
	}

	panelInner = new JPanel();
	panelOuter = new JPanel();

	panelOuter.setLayout(new BorderLayout());
	panelInner.setLayout(new BoxLayout(panelInner, BoxLayout.Y_AXIS));
	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

	for (i = 0; i < panelList.size(); i++) {
	    panelInner.add(panelList.get(i));
	}

	add(new JLabel("Rule " + stepName));
	panelOuter.add(panelInner, BorderLayout.PAGE_START);
	add(panelOuter);

	addAllListeners();
    }

    // Removes actionListeners from the buttons of all the rulePanels
    private void removeAllListeners() {
	int i;

	for (i = 0; i < panelList.size(); i++) {
	    panelList.get(i).buttonRemove.removeActionListener(this);
	    panelList.get(i).buttonAdd.removeActionListener(this);
	}
    }

    // Adds actionListeners to the buttons of all the rulePanels
    private void addAllListeners() {
	int i;

	for (i = 0; i < panelList.size(); i++) {
	    panelList.get(i).buttonRemove.addActionListener(this);
	    panelList.get(i).buttonAdd.addActionListener(this);
	}
    }

    // Creates a new rulePanel at specified index
    private void addNewRulePanel(int index) {
	panelList.add(index, new RowPanel());
	panelList.get(index).buttonRemove.addActionListener(this);
	panelList.get(index).buttonAdd.addActionListener(this);
	createMainPanel(false);
    }

    // Removes the rulePanel from the specified index
    private void removeARulePanel(int index) {
	panelList.get(index).buttonRemove.removeActionListener(this);
	panelList.get(index).buttonAdd.removeActionListener(this);
	panelList.remove(index);
	createMainPanel(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	int i;

	for (i = 0; i < panelList.size(); i++) {
	    if (e.getSource() == panelList.get(i).buttonRemove && panelList.size() > 1) {
		removeARulePanel(i);
		//frame.setBounds(frame.getX(), frame.getY(), this.getWidth(), this.getHeight());
		frame.pack();
		frame.revalidate();
		break;
	    } else if (e.getSource() == panelList.get(i).buttonAdd) {
		addNewRulePanel(i + 1);
		//frame.setBounds(frame.getX(), frame.getY(), this.getWidth(), this.getHeight());
		frame.pack();
		frame.revalidate();
		break;
	    }
	}
    }

}
