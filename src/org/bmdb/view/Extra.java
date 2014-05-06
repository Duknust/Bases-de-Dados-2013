/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bmdb.view;

import java.awt.Color;
import java.sql.Connection;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import org.bmdb.bsl.ManagerSystem;
import org.bmdb.bsl.exceptions.BslConnectionBrokerUnavailableException;
import org.bmdb.dao.SystemDAO;
import org.bmdb.dao.domain.Actor;
import org.bmdb.dao.domain.Filme;

/**
 *
 * @author duarteduarte
 */
public class Extra extends javax.swing.JDialog {

    ManagerSystem ms;
    Properties props;

    /**
     * Creates new form Extra
     */
    public Extra(JFrame parent, Properties props) {
        super(parent);
        this.props = props;
        initComponents();
        this.getContentPane().setBackground(Color.black);
        initManagers();
        Actor actor = null;
        Actor actriz = null;
        if ((actor = ms.getActorMaisOscares()) != null) {
            jLabel8.setText(actor.getNomeActor() + " - " + actor.getOscaresVencidos());
        } else {
            jLabel8.setText("");
        }
        if ((actriz = ms.getActrizMaisOscares()) != null) {
            jLabel9.setText(actriz.getNomeActor() + " - " + actriz.getOscaresVencidos());
        } else {
            jLabel9.setText("");
        }
        Filme filmeMaisOscares = null;
        if ((filmeMaisOscares = ms.getFilmeMaisOscares()) != null) {
            jLabel10.setText(filmeMaisOscares.getTitulo() + " - " + filmeMaisOscares.getOscares());
        } else {
            jLabel10.setText("");
        }
        Filme filmeMaisGerou = null;
        Filme filmeMenosGerou = null;
        if ((filmeMaisGerou = ms.getFilmeMaisGerou()) != null) {
            jLabel11.setText(filmeMaisGerou.getTitulo() + " - " + filmeMaisGerou.getGross() + " €");
        } else {
            jLabel11.setText("");
        }
        if ((filmeMenosGerou = ms.getFilmeMenosGerou()) != null) {
            jLabel12.setText(filmeMenosGerou.getTitulo() + " - " + filmeMenosGerou.getGross() + " €");
        } else {
            jLabel12.setText("");
        }
        Filme filmeMaisCaro = null;
        if ((filmeMaisCaro = ms.getFilmeMaisCaro()) != null) {
            jLabel14.setText(filmeMaisCaro.getTitulo() + " - " + filmeMaisCaro.getBudget() + " €");
        } else {
            jLabel14.setText("");
        }
    }

    private void initManagers() {
        try {
            ms = new ManagerSystem(this.props);
        } catch (BslConnectionBrokerUnavailableException ex) {
            Logger.getLogger(Extra.class.getName()).log(Level.SEVERE, null, ex);
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
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel2.setIcon(new javax.swing.ImageIcon("/Users/duarteduarte/NetBeansProjects/bmdb/images/nelsonsupercurto.jpg")); // NOI18N

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 204, 0));
        jLabel1.setText("Welcome to Banana");

        jLabel3.setForeground(new java.awt.Color(255, 204, 0));
        jLabel3.setText("Actor com mais Oscares");

        jLabel4.setForeground(new java.awt.Color(255, 204, 0));
        jLabel4.setText("Actriz com mais Oscares");

        jLabel5.setForeground(new java.awt.Color(255, 204, 0));
        jLabel5.setText("Filme com mais Oscares");

        jLabel6.setForeground(new java.awt.Color(255, 204, 0));
        jLabel6.setText("Filme que mais gerou");

        jLabel7.setForeground(new java.awt.Color(255, 204, 0));
        jLabel7.setText("Filme que menos gerou");

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("res");

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("res");

        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("res");

        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("res");

        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("res");

        jLabel13.setForeground(new java.awt.Color(255, 204, 0));
        jLabel13.setText("Filme mais caro");

        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("res");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 261, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(layout.createSequentialGroup()
                        .add(64, 64, 64)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel3)
                            .add(jLabel4)
                            .add(jLabel5)
                            .add(jLabel6)
                            .add(jLabel7)
                            .add(jLabel13))
                        .add(29, 29, 29)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel12)
                            .add(jLabel11)
                            .add(jLabel10)
                            .add(jLabel9)
                            .add(jLabel8)
                            .add(jLabel14))))
                .addContainerGap(560, Short.MAX_VALUE))
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .add(0, 0, Short.MAX_VALUE)
                .add(jLabel2)
                .add(34, 34, 34))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel3)
                    .add(jLabel8))
                .add(18, 18, 18)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel4)
                    .add(jLabel9))
                .add(18, 18, 18)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel5)
                    .add(jLabel10))
                .add(18, 18, 18)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel6)
                    .add(jLabel11))
                .add(18, 18, 18)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel7)
                    .add(jLabel12))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED, 19, Short.MAX_VALUE)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel2)
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabel13)
                        .add(jLabel14))))
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
            java.util.logging.Logger.getLogger(Extra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Extra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Extra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Extra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Extra(null, null).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    // End of variables declaration//GEN-END:variables
}
