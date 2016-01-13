/*
    Copyright (C) 2015   Martin Dames <martin@bastionbytes.de>
  
    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License along
    with this program; if not, write to the Free Software Foundation, Inc.,
    51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
  
*/
package tingeltangel.gui;

import java.awt.Component;
import java.util.Iterator;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import tingeltangel.core.Properties;
import tingeltangel.core.Tupel;
import tingeltangel.tools.TTS;

/**
 *
 * @author mdames
 */
public class TTSPreferences extends javax.swing.JFrame {

    public final static String PROPERTY_DEFAULT_VOICE = "default_voice";
    public final static String PROPERTY_DEFAULT_VARIANT = "default_variant";
    
    /**
     * Creates new form TTSPreferences
     */
    public TTSPreferences() {
        initComponents();
        
        String voiceID = Properties.getStringProperty(PROPERTY_DEFAULT_VOICE);
        Iterator<String> voices = TTS.getVoiceIDs().iterator();
        DefaultComboBoxModel voiceModel = (DefaultComboBoxModel)defaultLanguage.getModel();
        while(voices.hasNext()) {
            String id = voices.next();
            Tupel<String, String> tupel = new Tupel<String, String>(id, TTS.getVoiceName(id));
            voiceModel.addElement(tupel);
            if(id.equals(voiceID)) {
                voiceModel.setSelectedItem(tupel);
            }
        }
        defaultLanguage.setRenderer(new MyRenderer());
        
        String variantID = Properties.getStringProperty(PROPERTY_DEFAULT_VARIANT);
        Iterator<String> variants = TTS.getVariantIDs().iterator();
        DefaultComboBoxModel variantModel = (DefaultComboBoxModel)defaultVariant.getModel();
        Tupel<String, String> noVariant = new Tupel<String, String>("", "keine Variante");
        variantModel.addElement(noVariant);
        if(variantID == null || variantID.isEmpty()) {
            variantModel.setSelectedItem(noVariant);
        }
        while(variants.hasNext()) {
            String id = variants.next();
            Tupel<String, String> tupel = new Tupel<String, String>(id, TTS.getVariantName(id));
            variantModel.addElement(tupel);
            if(id.equals(variantID)) {
                variantModel.setSelectedItem(tupel);
            }
        }
        defaultVariant.setRenderer(new MyRenderer());
        
    }

    public static String getDefaultVoice() {
        return(Properties.getStringProperty(PROPERTY_DEFAULT_VOICE));
    }
    
    public static String getDefaultVariant() {
        return(Properties.getStringProperty(PROPERTY_DEFAULT_VARIANT));
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        defaultLanguage = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        okButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        defaultVariant = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        defaultLanguage.setModel(new DefaultComboBoxModel());

        jLabel1.setText("Standardsprache:");

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        jLabel2.setText("Standardvariante:");

        defaultVariant.setModel(new DefaultComboBoxModel());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(defaultLanguage, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(defaultVariant, 0, 178, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(okButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(defaultLanguage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(defaultVariant, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(okButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        String langID = ((Tupel<String, String>)defaultLanguage.getSelectedItem()).a;
        String varID = ((Tupel<String, String>)defaultVariant.getSelectedItem()).a;
        Properties.setProperty(PROPERTY_DEFAULT_VOICE, langID);
        Properties.setProperty(PROPERTY_DEFAULT_VARIANT, varID);
        setVisible(false);
    }//GEN-LAST:event_okButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox defaultLanguage;
    private javax.swing.JComboBox defaultVariant;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton okButton;
    // End of variables declaration//GEN-END:variables


    class MyRenderer implements ListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Tupel<String, String> tupel = (Tupel<String, String>)value;
            return(new JLabel(tupel.b));
        }
        
    }
}
