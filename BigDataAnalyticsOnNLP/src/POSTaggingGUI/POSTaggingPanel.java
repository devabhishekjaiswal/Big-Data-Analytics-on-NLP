/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POSTaggingGUI;

import SentenceDetectorGUI.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.text.BadLocationException;

/**

 @author Shubham
 */
public class POSTaggingPanel extends javax.swing.JPanel implements ActionListener {

    /**
     Creates new form SentenceDetectorPanel
     */
    public POSTaggingPanel() {
	initComponents();
	setAttributes();
    }

    /**
     This method is called from within the constructor to
     initialize the form.
     WARNING: Do NOT modify this code. The content of this method is
     always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaInput = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaOutput = new javax.swing.JTextArea();
        jCheckBox = new javax.swing.JCheckBox();

        jTextAreaInput.setColumns(20);
        jTextAreaInput.setRows(5);
        jScrollPane1.setViewportView(jTextAreaInput);

        jTextAreaOutput.setColumns(20);
        jTextAreaOutput.setRows(5);
        jScrollPane2.setViewportView(jTextAreaOutput);

        jCheckBox.setText("Use Editor");
        jCheckBox.setToolTipText("Detect Sentences in Editor Text");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 382, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jCheckBox)
                .addGap(338, 338, 338))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addComponent(jCheckBox)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jCheckBox;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextAreaInput;
    private javax.swing.JTextArea jTextAreaOutput;
    // End of variables declaration//GEN-END:variables

    private void toggleTextArea(boolean state) {
	jTextAreaInput.setEnabled(state);
	jTextAreaOutput.setEnabled(state);
    }
    
    private void setAttributes() {
	jTextAreaInput.setLineWrap(true);
	jTextAreaOutput.setLineWrap(true);
	toggleTextArea(false);
	jTextAreaOutput.setEditable(false);
	addListeners();
    }
    
    private void addListeners() {
	jCheckBox.addActionListener(this);
    }
    
    public String getEditorText() {
	try {
	    return jTextAreaInput.getDocument().getText(0, jTextAreaInput.getDocument().getLength());
	} catch (BadLocationException ex) {
	    System.out.println("Exception while parsing editor text:\t" + ex.toString());
	    return null;
	}
    }
    
    public void setEditorText(String summary) {
	jTextAreaOutput.setText(summary);
    }
    
    public boolean useEditor() {
	return jCheckBox.isSelected();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
	toggleTextArea(jCheckBox.isSelected());
    }
    
}