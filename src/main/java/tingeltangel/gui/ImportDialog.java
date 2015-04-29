/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tingeltangel.gui;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import tingeltangel.core.Tools;

/**
 *
 * @author martin
 */
public class ImportDialog extends javax.swing.JDialog {

    private File ouf = null;
    private File txt = null;
    private File png = null;
    private File src = null;
    private int id = -1;
    private final MapCallback callback;
    
    /**
     * Creates new form ImportDialog
     */
    public ImportDialog(java.awt.Frame parent, boolean modal, MapCallback callback) {
        super(parent, modal);
        this.callback = callback;
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        textFieldOuf = new javax.swing.JTextField();
        textFieldTxt = new javax.swing.JTextField();
        textFieldPng = new javax.swing.JTextField();
        textFieldSrc = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        textFieldID = new javax.swing.JTextField();
        buttonImport = new javax.swing.JButton();
        buttonOuf = new javax.swing.JButton();
        buttonTxt = new javax.swing.JButton();
        buttonPng = new javax.swing.JButton();
        buttonSrc = new javax.swing.JButton();
        buttonID = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Import");
        setAlwaysOnTop(true);
        setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);

        jLabel1.setText("ouf-Datei");

        jLabel2.setText("txt-Datei");

        jLabel3.setText("png-Datei");

        jLabel4.setText("src-Datei");

        textFieldOuf.setEditable(false);

        textFieldTxt.setEditable(false);

        textFieldPng.setEditable(false);

        textFieldSrc.setEditable(false);

        jLabel5.setText("neue ID zuweisen");

        textFieldID.setEditable(false);

        buttonImport.setText("importieren");
        buttonImport.setActionCommand("import");
        buttonImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonImportActionPerformed(evt);
            }
        });

        buttonOuf.setText("jButton2");
        buttonOuf.setActionCommand("ouf");
        buttonOuf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOufActionPerformed(evt);
            }
        });

        buttonTxt.setText("jButton3");
        buttonTxt.setActionCommand("txt");
        buttonTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonTxtActionPerformed(evt);
            }
        });

        buttonPng.setText("jButton4");
        buttonPng.setActionCommand("png");
        buttonPng.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPngActionPerformed(evt);
            }
        });

        buttonSrc.setText("jButton5");
        buttonSrc.setActionCommand("src");
        buttonSrc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSrcActionPerformed(evt);
            }
        });

        buttonID.setText("jButton1");
        buttonID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonIDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(buttonImport)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textFieldTxt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE))
                                    .addComponent(jLabel4))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(textFieldPng, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                                            .addComponent(textFieldSrc, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(textFieldID, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(50, 50, 50)
                                        .addComponent(textFieldOuf, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(242, 242, 242)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(buttonSrc, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                            .addComponent(buttonPng, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                            .addComponent(buttonOuf, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                            .addComponent(buttonTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonID, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))))
                .addGap(0, 63, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(textFieldOuf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonOuf))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(textFieldTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonTxt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(textFieldPng, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonPng))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(textFieldSrc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonSrc))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(textFieldID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonID))
                .addGap(18, 18, 18)
                .addComponent(buttonImport)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void buttonPngActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPngActionPerformed
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(Tools.getHomeDirectory());
        fc.setDialogTitle("png-Datei auswählen");
        fc.setFileFilter(new FileNameExtensionFilter("png Datei (*.png)", "png"));
        if(fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            if(file.isFile() && file.canRead()) {
                png = file;
                textFieldPng.setText(file.getName());
            } else {
                // TODO: error dialog
            }
        }
    }//GEN-LAST:event_buttonPngActionPerformed

    private void buttonTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonTxtActionPerformed
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(Tools.getHomeDirectory());
        fc.setDialogTitle("txt-Datei auswählen");
        fc.setFileFilter(new FileNameExtensionFilter("txt Datei (*.txt)", "txt"));
        if(fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            if(file.isFile() && file.canRead()) {
                txt = file;
                textFieldTxt.setText(file.getName());
            } else {
                // TODO: error dialog
            }
        }
    }//GEN-LAST:event_buttonTxtActionPerformed

    private void buttonSrcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSrcActionPerformed
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(Tools.getHomeDirectory());
        fc.setDialogTitle("src-Datei auswählen");
        fc.setFileFilter(new FileNameExtensionFilter("src Datei (*.src)", "src"));
        if(fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            if(file.isFile() && file.canRead()) {
                src = file;
                textFieldSrc.setText(file.getName());
            } else {
                // TODO: error dialog
            }
        }
    }//GEN-LAST:event_buttonSrcActionPerformed

    private void buttonImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonImportActionPerformed
        Map<String, Object> map = new HashMap<String, Object>();
        if(txt != null) {
            map.put("txt", txt);
        }
        if(png != null) {
            map.put("png", png);
        }
        if(src != null) {
            map.put("src", src);
        }
        if(ouf != null) {
            map.put("ouf", ouf);
        }
        if(id >= 0) {
            map.put("id", id);
        }
        callback.callback(map);
    }//GEN-LAST:event_buttonImportActionPerformed

    private void buttonOufActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOufActionPerformed
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(Tools.getHomeDirectory());
        fc.setDialogTitle("ouf-Datei auswählen");
        fc.setFileFilter(new FileNameExtensionFilter("ouf Datei (*.ouf)", "ouf"));
        if(fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            if(file.isFile() && file.canRead()) {
                ouf = file;
                textFieldOuf.setText(file.getName());
            } else {
                // TODO: error dialog
            }
        }
    }//GEN-LAST:event_buttonOufActionPerformed

    private void buttonIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonIDActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonID;
    private javax.swing.JButton buttonImport;
    private javax.swing.JButton buttonOuf;
    private javax.swing.JButton buttonPng;
    private javax.swing.JButton buttonSrc;
    private javax.swing.JButton buttonTxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField textFieldID;
    private javax.swing.JTextField textFieldOuf;
    private javax.swing.JTextField textFieldPng;
    private javax.swing.JTextField textFieldSrc;
    private javax.swing.JTextField textFieldTxt;
    // End of variables declaration//GEN-END:variables
}
