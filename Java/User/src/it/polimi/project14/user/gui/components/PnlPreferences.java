package it.polimi.project14.user.gui.components;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class PnlPreferences extends JFrame {
   static PnlPreferences thePnlPreferences;

   JPanel pnlReturnPanel;
   JLabel lblMyCaps;
   JTextField txfMyCaps;
   JButton btnDeleteCaps;
   
   JPanel pnlFilterCaps;
   JComboBox cmbProvince;
   JComboBox cmbMunicipality;
   JLabel lblMunicipality;
   JLabel lblProvince;
   JButton btnAddCaps;
   
   JPanel pnlFoundCaps;
   JLabel lblFoundCaps;
   JTextField txfFoundCaps;

   public static void main(String args[]) {
      try {
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch (ClassNotFoundException e) {
      } catch (InstantiationException e) {
      } catch (IllegalAccessException e) {
      } catch (UnsupportedLookAndFeelException e) {
      }
      thePnlPreferences = new PnlPreferences();
   }

   public PnlPreferences() {
      super("TITLE");

      pnlReturnPanel = new JPanel();
      GridBagLayout gbPanel0 = new GridBagLayout();
      GridBagConstraints gbcPanel0 = new GridBagConstraints();
      pnlReturnPanel.setLayout(gbPanel0);

      lblMyCaps = new JLabel("I tuoi CAP :");
      gbcPanel0.gridx = 0;
      gbcPanel0.gridy = 0;
      gbcPanel0.gridwidth = 1;
      gbcPanel0.gridheight = 1;
      gbcPanel0.fill = GridBagConstraints.BOTH;
      gbcPanel0.weightx = 0;
      gbcPanel0.weighty = 0;
      gbcPanel0.anchor = GridBagConstraints.WEST;
      gbcPanel0.insets = new Insets(10, 10, 10, 10);
      gbPanel0.setConstraints(lblMyCaps, gbcPanel0);
      pnlReturnPanel.add(lblMyCaps);

      txfMyCaps = new JTextField();
      txfMyCaps.setBackground(new Color(240, 240, 240));
      txfMyCaps.setEditable(false);
      gbcPanel0.gridx = 1;
      gbcPanel0.gridy = 0;
      gbcPanel0.gridwidth = 2;
      gbcPanel0.gridheight = 1;
      gbcPanel0.fill = GridBagConstraints.HORIZONTAL;
      gbcPanel0.weightx = 0;
      gbcPanel0.weighty = 0;
      gbcPanel0.anchor = GridBagConstraints.NORTH;
      gbcPanel0.insets = new Insets(10, 10, 10, 10);
      gbPanel0.setConstraints(txfMyCaps, gbcPanel0);
      pnlReturnPanel.add(txfMyCaps);

      btnDeleteCaps = new JButton("Elimina Cap preferiti");
      gbcPanel0.gridx = 2;
      gbcPanel0.gridy = 1;
      gbcPanel0.gridwidth = 1;
      gbcPanel0.gridheight = 1;
      gbcPanel0.fill = GridBagConstraints.VERTICAL;
      gbcPanel0.weightx = 1;
      gbcPanel0.weighty = 0;
      gbcPanel0.anchor = GridBagConstraints.NORTHEAST;
      gbcPanel0.insets = new Insets(3, 3, 3, 10);
      gbPanel0.setConstraints(btnDeleteCaps, gbcPanel0);
      pnlReturnPanel.add(btnDeleteCaps);

      pnlFilterCaps = new JPanel();
      pnlFilterCaps.setBorder(BorderFactory.createTitledBorder("Aggiungi CAP"));
      GridBagLayout gbPanel1 = new GridBagLayout();
      GridBagConstraints gbcPanel1 = new GridBagConstraints();
      pnlFilterCaps.setLayout(gbPanel1);

      lblProvince = new JLabel("Filtra per provincia");
      gbcPanel1.gridx = 0;
      gbcPanel1.gridy = 0;
      gbcPanel1.gridwidth = 1;
      gbcPanel1.gridheight = 1;
      gbcPanel1.fill = GridBagConstraints.NONE;
      gbcPanel1.weightx = 0;
      gbcPanel1.weighty = 0;
      gbcPanel1.anchor = GridBagConstraints.WEST;
      gbcPanel1.insets = new Insets(10, 10, 3, 15);
      gbPanel1.setConstraints(lblProvince, gbcPanel1);
      pnlFilterCaps.add(lblProvince);

      lblMunicipality = new JLabel("Filtra per comune");
      gbcPanel1.gridx = 1;
      gbcPanel1.gridy = 0;
      gbcPanel1.gridwidth = 2;
      gbcPanel1.gridheight = 1;
      gbcPanel1.fill = GridBagConstraints.NONE;
      gbcPanel1.weightx = 1;
      gbcPanel1.weighty = 0;
      gbcPanel1.anchor = GridBagConstraints.WEST;
      gbcPanel1.insets = new Insets(10, 15, 3, 10);
      gbPanel1.setConstraints(lblMunicipality, gbcPanel1);
      pnlFilterCaps.add(lblMunicipality);

      String[] dataCombo0 = {"Chocolate", "Ice Cream", "Apple Pie" };
      cmbProvince = new JComboBox(dataCombo0);
      gbcPanel1.gridx = 0;
      gbcPanel1.gridy = 1;
      gbcPanel1.gridwidth = 1;
      gbcPanel1.gridheight = 1;
      gbcPanel1.fill = GridBagConstraints.NONE;
      gbcPanel1.weightx = 0;
      gbcPanel1.weighty = 0;
      gbcPanel1.anchor = GridBagConstraints.WEST;
      gbcPanel1.insets = new Insets(3, 10, 10, 15);
      gbPanel1.setConstraints(cmbProvince, gbcPanel1);
      pnlFilterCaps.add(cmbProvince);

      String[] dataCombo1 = { "Chocolate", "Ice Cream", "Apple Pie" };
      cmbMunicipality = new JComboBox(dataCombo1);
      gbcPanel1.gridx = 1;
      gbcPanel1.gridy = 1;
      gbcPanel1.gridwidth = 2;
      gbcPanel1.gridheight = 1;
      gbcPanel1.fill = GridBagConstraints.NONE;
      gbcPanel1.weightx = 1;
      gbcPanel1.weighty = 0;
      gbcPanel1.anchor = GridBagConstraints.WEST;
      gbcPanel1.insets = new Insets(3, 15, 10, 10);
      gbPanel1.setConstraints(cmbMunicipality, gbcPanel1);
      pnlFilterCaps.add(cmbMunicipality);

      pnlFoundCaps = new JPanel();
      GridBagLayout gblFoundCaps = new GridBagLayout();
      GridBagConstraints gblcFoundCaps = new GridBagConstraints();
      pnlFoundCaps.setLayout(gblFoundCaps);
      gbcPanel1.gridx = 0;
      gbcPanel1.gridy = 3;
      gbcPanel1.gridwidth = 3;
      gbcPanel1.gridheight = 1;
      gbcPanel1.fill = GridBagConstraints.BOTH;
      gbcPanel1.weightx = 0;
      gbcPanel1.weighty = 1;
      gbcPanel1.anchor = GridBagConstraints.NORTH;
      gbcPanel1.insets = new Insets(10, 10, 10, 10);
      gbPanel1.setConstraints(pnlFoundCaps, gbcPanel1);
      pnlFilterCaps.add(pnlFoundCaps);

      lblFoundCaps = new JLabel("Cap trovati :");
      gblcFoundCaps.gridx = 0;
      gblcFoundCaps.gridy = 0;
      gblcFoundCaps.gridwidth = 1;
      gblcFoundCaps.gridheight = 1;
      gblcFoundCaps.fill = GridBagConstraints.HORIZONTAL;
      gblcFoundCaps.weightx = 0;
      gblcFoundCaps.weighty = 0;
      gblcFoundCaps.anchor = GridBagConstraints.WEST;
      gblcFoundCaps.insets = new Insets(3, 3, 3, 3);
      gblFoundCaps.setConstraints(lblFoundCaps, gblcFoundCaps);
      pnlFoundCaps.add(lblFoundCaps);

      txfFoundCaps = new JTextField();
      txfFoundCaps.setBackground(new Color(240, 240, 240));
      txfFoundCaps.setEditable(false);
      gblcFoundCaps.gridx = 1;
      gblcFoundCaps.gridy = 0;
      gblcFoundCaps.gridwidth = 1;
      gblcFoundCaps.gridheight = 1;
      gblcFoundCaps.fill = GridBagConstraints.HORIZONTAL;
      gblcFoundCaps.weightx = 1;
      gblcFoundCaps.weighty = 0;
      gblcFoundCaps.anchor = GridBagConstraints.WEST;
      gblcFoundCaps.insets = new Insets(3, 3, 3, 3);
      gblFoundCaps.setConstraints(txfFoundCaps, gblcFoundCaps);
      pnlFoundCaps.add(txfFoundCaps);

      btnAddCaps = new JButton("Aggiungi alla tua lista CAP");
      gbcPanel1.gridx = 2;
      gbcPanel1.gridy = 4;
      gbcPanel1.gridwidth = 1;
      gbcPanel1.gridheight = 1;
      gbcPanel1.fill = GridBagConstraints.NONE;
      gbcPanel1.weightx = 0;
      gbcPanel1.weighty = 1;
      gbcPanel1.anchor = GridBagConstraints.SOUTHEAST;
      gbcPanel1.insets = new Insets(3, 3, 3, 3);
      gbPanel1.setConstraints(btnAddCaps, gbcPanel1);
      pnlFilterCaps.add(btnAddCaps);

      gbcPanel0.gridx = 0;
      gbcPanel0.gridy = 2;
      gbcPanel0.gridwidth = 3;
      gbcPanel0.gridheight = 1;
      gbcPanel0.fill = GridBagConstraints.BOTH;
      gbcPanel0.weightx = 0;
      gbcPanel0.weighty = 1;
      gbcPanel0.anchor = GridBagConstraints.NORTH;
      gbPanel0.setConstraints(pnlFilterCaps, gbcPanel0);
      pnlReturnPanel.add(pnlFilterCaps);

      setDefaultCloseOperation(EXIT_ON_CLOSE);

      setContentPane(pnlReturnPanel);
      pack();
      setVisible(true);
   }
}
