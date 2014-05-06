/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bmdb.view;

import java.awt.Color;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import org.bmdb.bsl.ManagerSystem;
import org.bmdb.bsl.exceptions.BslConnectionBrokerUnavailableException;
import org.bmdb.dao.SystemDAO;
import org.bmdb.dao.domain.Actor;

/**
 *
 * @author duarteduarte
 */
public class TheOtherWorld extends javax.swing.JDialog {

    ManagerSystem ms;
    final ImageIcon icon = new ImageIcon("images/BananaLOGOpequeno.png");
    Properties props;

    /**
     * Creates new form BlessedWinners
     */
    public TheOtherWorld(JFrame parent, Properties props) {
        super(parent);
        initComponents();
        jLabelNumber1Pic.setText("");
        jLabelNumber1Desc.setText("");
        jLabelNumber2Pic.setText("");
        jLabelNumber2Desc.setText("");
        jLabelNumber3Pic.setText("");
        jLabelNumber3Desc.setText("");
        jLabelNumber4Pic.setText("");
        jLabelNumber4Desc.setText("");
        this.getContentPane().setBackground(Color.black);
        try {
            ms = new ManagerSystem(this.props);

            int rating = 0;
            List<Actor> theotherworld = ms.getTheOtherWorld();
            if (theotherworld.size() >= 1) {
                rating = theotherworld.get(0).getPremiosVencidosActor();
                jLabelNumber1Pic.setIcon(new ImageIcon("images/actores/" + theotherworld.get(0).getImagem()));
                jLabelNumber1Desc.setText(theotherworld.get(0).getNomeActor() + " (" + rating + "%)");
            }
            if (theotherworld.size() >= 2) {
                rating = theotherworld.get(1).getPremiosVencidosActor();
                jLabelNumber2Pic.setIcon(new ImageIcon("images/actores/" + theotherworld.get(1).getImagem()));
                jLabelNumber2Desc.setText(theotherworld.get(1).getNomeActor() + " (" + rating + "%)");
            }
            if (theotherworld.size() >= 3) {
                rating = theotherworld.get(2).getPremiosVencidosActor();
                jLabelNumber3Pic.setIcon(new ImageIcon("images/actores/" + theotherworld.get(2).getImagem()));
                jLabelNumber3Desc.setText(theotherworld.get(2).getNomeActor() + " (" + rating + "%)");
            }
            if (theotherworld.size() >= 4) {
                rating = theotherworld.get(3).getPremiosVencidosActor();
                jLabelNumber4Pic.setIcon(new ImageIcon("images/actores/" + theotherworld.get(3).getImagem()));
                jLabelNumber4Desc.setText(theotherworld.get(3).getNomeActor() + " (" + rating + "%)");
            }
        } catch (BslConnectionBrokerUnavailableException ex) {
            Logger.getLogger(TheOtherWorld.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabelNumber1Desc = new javax.swing.JLabel();
        jLabelNumber1Pic = new javax.swing.JLabel();
        jLabelNumber3Desc = new javax.swing.JLabel();
        jLabelNumber3Pic = new javax.swing.JLabel();
        jLabelNumber2Desc = new javax.swing.JLabel();
        jLabelNumber2Pic = new javax.swing.JLabel();
        jLabelNumber4Pic = new javax.swing.JLabel();
        jLabelNumber4Desc = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Formula de Calculo : ((premios vencidos + oscares) / premios nomeado) *100");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 200, 0));
        jLabel1.setText("\"The Other World\" (adult)");

        jLabelNumber1Desc.setForeground(new java.awt.Color(255, 200, 0));
        jLabelNumber1Desc.setText("jLabel2");

        jLabelNumber1Pic.setForeground(new java.awt.Color(255, 200, 0));
        jLabelNumber1Pic.setText("jLabel2");

        jLabelNumber3Desc.setForeground(new java.awt.Color(255, 200, 0));
        jLabelNumber3Desc.setText("jLabel2");

        jLabelNumber3Pic.setForeground(new java.awt.Color(255, 200, 0));
        jLabelNumber3Pic.setText("jLabel2");

        jLabelNumber2Desc.setForeground(new java.awt.Color(255, 200, 0));
        jLabelNumber2Desc.setText("jLabel2");

        jLabelNumber2Pic.setForeground(new java.awt.Color(255, 200, 0));
        jLabelNumber2Pic.setText("jLabel2");

        jLabelNumber4Pic.setForeground(new java.awt.Color(255, 200, 0));
        jLabelNumber4Pic.setText("jLabel2");

        jLabelNumber4Desc.setForeground(new java.awt.Color(255, 200, 0));
        jLabelNumber4Desc.setText("jLabel2");

        jLabel3.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Formula de Calculo : max(premios_vencidos)");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(jLabelNumber1Pic, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 147, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(jLabelNumber1Desc, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 240, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jLabelNumber2Pic, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 147, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(jLabelNumber2Desc, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 240, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(0, 0, Short.MAX_VALUE))
                    .add(layout.createSequentialGroup()
                        .add(jLabelNumber3Pic, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 147, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(jLabelNumber3Desc, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 240, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(jLabelNumber4Pic, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 147, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(jLabelNumber4Desc, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 240, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel3)
                    .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 457, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 42, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabelNumber2Desc, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 157, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jLabelNumber2Pic, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 157, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabelNumber1Desc, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 157, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jLabelNumber1Pic, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 157, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabelNumber4Desc, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 157, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jLabelNumber4Pic, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 157, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabelNumber3Desc, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 157, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jLabelNumber3Pic, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 157, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 33, Short.MAX_VALUE)
                .add(jLabel3))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TheOtherWorld.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TheOtherWorld.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TheOtherWorld.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TheOtherWorld.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TheOtherWorld(null, null).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelNumber1Desc;
    private javax.swing.JLabel jLabelNumber1Pic;
    private javax.swing.JLabel jLabelNumber2Desc;
    private javax.swing.JLabel jLabelNumber2Pic;
    private javax.swing.JLabel jLabelNumber3Desc;
    private javax.swing.JLabel jLabelNumber3Pic;
    private javax.swing.JLabel jLabelNumber4Desc;
    private javax.swing.JLabel jLabelNumber4Pic;
    // End of variables declaration//GEN-END:variables
}
