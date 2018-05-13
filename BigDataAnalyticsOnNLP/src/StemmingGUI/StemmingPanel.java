/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StemmingGUI;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**

 @author Shubham
 */
public class StemmingPanel extends JPanel {

    // ArrayList to hold the panels of different steps from 1a to 5b
    public ArrayList<StepPanel> stepPanels;
    private JFrame frame;

    public StemmingPanel(JFrame frame) {
	this.stepPanels = new ArrayList<>();
	this.frame = frame;
	createAllStepPanels();
	createStemmingPanel();
	colorAllStepPanels();
    }

    // Create Panel for step 1a
    private void create_Step_1a() {
	ArrayList<String> prereqList = new ArrayList<>();
	ArrayList<String> regexList = new ArrayList<>();
	ArrayList<String> removeList = new ArrayList<>();
	ArrayList<String> addList = new ArrayList<>();

	prereqList.add("");
	prereqList.add("");
	prereqList.add("");
	prereqList.add("");

	regexList.add("");
	regexList.add("");
	regexList.add("");
	regexList.add("");

	removeList.add("sses");
	removeList.add("ies");
	removeList.add("ss");
	removeList.add("s");

	addList.add("ss");
	addList.add("i");
	addList.add("ss");
	addList.add("");

	stepPanels.add(new StepPanel("1a", prereqList, regexList, addList, removeList, frame));
    }

    // Create Panel for step 1b
    private void create_Step_1b() {
	ArrayList<String> prereqList = new ArrayList<>();
	ArrayList<String> regexList = new ArrayList<>();
	ArrayList<String> removeList = new ArrayList<>();
	ArrayList<String> addList = new ArrayList<>();

	prereqList.add("");
	prereqList.add("");
	prereqList.add("");
	prereqList.add("2,3");
	prereqList.add("2,3");
	prereqList.add("2,3");
	prereqList.add("2,3");

	regexList.add("m>0");
	regexList.add("*v*");
	regexList.add("*v*");
	regexList.add("");
	regexList.add("");
	regexList.add("");
	regexList.add("*d&^*L&^*S&^*Z");

	removeList.add("eed");
	removeList.add("ed");
	removeList.add("ing");
	removeList.add("at");
	removeList.add("bl");
	removeList.add("iz");
	removeList.add("2");

	addList.add("ee");
	addList.add("");
	addList.add("");
	addList.add("ate");
	addList.add("ble");
	addList.add("ize");
	addList.add("1");

	stepPanels.add(new StepPanel("1b", prereqList, regexList, addList, removeList, frame));
    }

    // Create Panel for step 1c
    private void create_Step_1c() {
	ArrayList<String> prereqList = new ArrayList<>();
	ArrayList<String> regexList = new ArrayList<>();
	ArrayList<String> removeList = new ArrayList<>();
	ArrayList<String> addList = new ArrayList<>();

	prereqList.add("");
	regexList.add("*v*");
	removeList.add("y");
	addList.add("i");

	stepPanels.add(new StepPanel("1c", prereqList, regexList, addList, removeList, frame));
    }

    // Create Panel for step 2
    private void create_Step_2() {
	ArrayList<String> prereqList = new ArrayList<>();
	ArrayList<String> regexList = new ArrayList<>();
	ArrayList<String> removeList = new ArrayList<>();
	ArrayList<String> addList = new ArrayList<>();

	prereqList.add("");
	prereqList.add("");
	prereqList.add("");
	prereqList.add("");
	prereqList.add("");
	prereqList.add("");
	prereqList.add("");
	prereqList.add("");
	prereqList.add("");
	prereqList.add("");
	prereqList.add("");
	prereqList.add("");
	prereqList.add("");
	prereqList.add("");
	prereqList.add("");
	prereqList.add("");
	prereqList.add("");
	prereqList.add("");
	prereqList.add("");
	prereqList.add("");

	regexList.add("m>0");
	regexList.add("m>0");
	regexList.add("m>0");
	regexList.add("m>0");
	regexList.add("m>0");
	regexList.add("m>0");
	regexList.add("m>0");
	regexList.add("m>0");
	regexList.add("m>0");
	regexList.add("m>0");
	regexList.add("m>0");
	regexList.add("m>0");
	regexList.add("m>0");
	regexList.add("m>0");
	regexList.add("m>0");
	regexList.add("m>0");
	regexList.add("m>0");
	regexList.add("m>0");
	regexList.add("m>0");
	regexList.add("m>0");

	removeList.add("ational");
	removeList.add("tional");
	removeList.add("enci");
	removeList.add("anci");
	removeList.add("izer");
	removeList.add("abli");
	removeList.add("alli");
	removeList.add("entli");
	removeList.add("eli");
	removeList.add("ousli");
	removeList.add("ization");
	removeList.add("ation");
	removeList.add("ator");
	removeList.add("alism");
	removeList.add("iveness");
	removeList.add("fulness");
	removeList.add("ousness");
	removeList.add("aliti");
	removeList.add("iviti");
	removeList.add("biliti");

	addList.add("ate");
	addList.add("tion");
	addList.add("ence");
	addList.add("ance");
	addList.add("ize");
	addList.add("able");
	addList.add("al");
	addList.add("ent");
	addList.add("e");
	addList.add("ous");
	addList.add("ize");
	addList.add("ate");
	addList.add("ate");
	addList.add("al");
	addList.add("ive");
	addList.add("ful");
	addList.add("ous");
	addList.add("al");
	addList.add("ive");
	addList.add("ble");

	stepPanels.add(new StepPanel("2", prereqList, regexList, addList, removeList, frame));
    }

    // Create Panel for step 3
    private void create_Step_3() {
	ArrayList<String> prereqList = new ArrayList<>();
	ArrayList<String> regexList = new ArrayList<>();
	ArrayList<String> removeList = new ArrayList<>();
	ArrayList<String> addList = new ArrayList<>();

	prereqList.add("");
	prereqList.add("");
	prereqList.add("");
	prereqList.add("");
	prereqList.add("");
	prereqList.add("");
	prereqList.add("");

	regexList.add("m>0");
	regexList.add("m>0");
	regexList.add("m>0");
	regexList.add("m>0");
	regexList.add("m>0");
	regexList.add("m>0");
	regexList.add("m>0");

	removeList.add("icate");
	removeList.add("ative");
	removeList.add("alize");
	removeList.add("iciti");
	removeList.add("ical");
	removeList.add("ful");
	removeList.add("ness");

	addList.add("ic");
	addList.add("");
	addList.add("al");
	addList.add("ic");
	addList.add("ic");
	addList.add("");
	addList.add("");

	stepPanels.add(new StepPanel("3", prereqList, regexList, addList, removeList, frame));
    }

    // Create Panel for step 4
    private void create_Step_4() {
	ArrayList<String> prereqList = new ArrayList<>();
	ArrayList<String> regexList = new ArrayList<>();
	ArrayList<String> removeList = new ArrayList<>();
	ArrayList<String> addList = new ArrayList<>();

	prereqList.add("");
	prereqList.add("");
	prereqList.add("");
	prereqList.add("");
	prereqList.add("");
	prereqList.add("");
	prereqList.add("");
	prereqList.add("");
	prereqList.add("");
	prereqList.add("");
	prereqList.add("");
	prereqList.add("");
	prereqList.add("");
	prereqList.add("");
	prereqList.add("");
	prereqList.add("");
	prereqList.add("");
	prereqList.add("");
	prereqList.add("");

	regexList.add("m>1");
	regexList.add("m>1");
	regexList.add("m>1");
	regexList.add("m>1");
	regexList.add("m>1");
	regexList.add("m>1");
	regexList.add("m>1");
	regexList.add("m>1");
	regexList.add("m>1");
	regexList.add("m>1");
	regexList.add("m>1");
	//regexList.add("[^A-Za-z]*[A-Za-z]*([AEIOUaeiou]+[B-DF-HJ-NP-TV-Zb-df-hj-np-tv-z]+)([AEIOUaeiou]+[B-DF-HJ-NP-TV-Zb-df-hj-np-tv-z]+)+[A-Za-z]*(s|t)ion[^A-Za-z]+");
	regexList.add("m>1&*S,m>1&*T");
	regexList.add("m>1");
	regexList.add("m>1");
	regexList.add("m>1");
	regexList.add("m>1");
	regexList.add("m>1");
	regexList.add("m>1");
	regexList.add("m>1");

	removeList.add("al");
	removeList.add("ance");
	removeList.add("ence");
	removeList.add("er");
	removeList.add("ic");
	removeList.add("able");
	removeList.add("ible");
	removeList.add("ant");
	removeList.add("ement");
	removeList.add("ment");
	removeList.add("ent");
	removeList.add("ion");
	removeList.add("ou");
	removeList.add("ism");
	removeList.add("ate");
	removeList.add("iti");
	removeList.add("ous");
	removeList.add("ive");
	removeList.add("ize");

	addList.add("");
	addList.add("");
	addList.add("");
	addList.add("");
	addList.add("");
	addList.add("");
	addList.add("");
	addList.add("");
	addList.add("");
	addList.add("");
	addList.add("");
	addList.add("");
	addList.add("");
	addList.add("");
	addList.add("");
	addList.add("");
	addList.add("");
	addList.add("");
	addList.add("");

	stepPanels.add(new StepPanel("4", prereqList, regexList, addList, removeList, frame));
    }

    // Create Panel for step 5a
    private void create_Step_5a() {
	ArrayList<String> prereqList = new ArrayList<>();
	ArrayList<String> regexList = new ArrayList<>();
	ArrayList<String> removeList = new ArrayList<>();
	ArrayList<String> addList = new ArrayList<>();

	prereqList.add("");
	prereqList.add("");

	regexList.add("m>1");
	regexList.add("m=1&^*o");

	removeList.add("e");
	removeList.add("e");

	addList.add("");
	addList.add("");

	stepPanels.add(new StepPanel("5a", prereqList, regexList, addList, removeList, frame));
    }

    // Create Panel for step 5b
    private void create_Step_5b() {
	ArrayList<String> prereqList = new ArrayList<>();
	ArrayList<String> regexList = new ArrayList<>();
	ArrayList<String> removeList = new ArrayList<>();
	ArrayList<String> addList = new ArrayList<>();

	prereqList.add("");
	//regexList.add("m>1&*d&*L");
	regexList.add("m>1&*d&*L");
	removeList.add("2");
	addList.add("1");

	stepPanels.add(new StepPanel("5b", prereqList, regexList, addList, removeList, frame));
    }

    // Invoke all above methods
    private void createAllStepPanels() {
	create_Step_1a();
	create_Step_1b();
	create_Step_1c();
	create_Step_2();
	create_Step_3();
	create_Step_4();
	create_Step_5a();
	create_Step_5b();
    }

    // Coloe the passed panel with provided coloe
    private void colorAStep(StepPanel panel, Color color) {
	int i;

	for (i = 0; i < panel.panelList.size(); i++) {
	    panel.panelList.get(i).setBackground(color);
	}
    }

    // Invoke the above method multiple times by passing panels of different steps in each instance
    private void colorAllStepPanels() {
	Color colorRED;
	Color colorBLUE;
	Color colorYELLOW;
	Color colorGREEN;
	Color colorORANGE;

	colorRED = new Color(255, 204, 204);
	colorBLUE = new Color(204, 204, 255);
	colorYELLOW = new Color(255, 255, 153);
	colorGREEN = new Color(204, 255, 153);
	colorORANGE = new Color(255, 178, 102);

	// Color Panels of Step 1 with light RED color
	colorAStep(stepPanels.get(0), colorRED);
	colorAStep(stepPanels.get(1), colorRED);
	colorAStep(stepPanels.get(2), colorRED);
        //stepPanels.get(0).setBackground(colorRED);
	//stepPanels.get(1).setBackground(colorRED);
	//stepPanels.get(2).setBackground(colorRED);

	// Color Panel of Step 2 with light PURPLE color
	colorAStep(stepPanels.get(3), colorBLUE);
        //stepPanels.get(3).setBackground(colorBLUE);

	// Color Panel of Step 3 with light YELLOW color
	colorAStep(stepPanels.get(4), colorYELLOW);
        //stepPanels.get(4).setBackground(new Color(255, 255, 153));

	// Color Panel of Step 4 with light GREEN color
	colorAStep(stepPanels.get(5), colorGREEN);
        //stepPanels.get(5).setBackground(new Color(204, 255, 153));

	// Color Panels of Step 5 with light ORANGE color
	colorAStep(stepPanels.get(6), colorORANGE);
	colorAStep(stepPanels.get(7), colorORANGE);
	//stepPanels.get(6).setBackground(new Color(255, 178, 102));
	//stepPanels.get(7).setBackground(new Color(255, 178, 102));
    }

    // Create the entire Stemming panel by adding panel of each step to the main Stemming panel
    private void createStemmingPanel() {
	int i;

	removeAll();
	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

	for (i = 0; i < stepPanels.size(); i++) {
	    add(stepPanels.get(i));
	}
    }

    // Force panel of each rule to create their ArrayLists by reading values from their textFields
    public void createAllLists() {
	for (StepPanel panel : stepPanels) {
	    panel.createSuffixRegexList();
	}
    }

}
