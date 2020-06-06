package it.polimi.project14.user.gui.components;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;

import it.polimi.project14.user.Caps;
import it.polimi.project14.user.User;

public class PnlPreferences extends JPanel implements ActionListener {

   JTextField txfMyCaps;
   JButton btnDeleteCaps;
   JLabel lblMyCaps;

   JPanel pnlFilterCaps;
   JComboBox<String> cmbProvince;
   JComboBox<String> cmbMunicipality;
   JLabel lblMunicipality;
   JLabel lblProvince;
   JButton btnAddCaps;

   JPanel pnlFoundCaps;
   JLabel lblFoundCaps;
   JTextField txfFoundCaps;

   User user;
   Set<String> capsToAdd;

   public PnlPreferences(User user) {
      super();

      this.user = user;

      GridBagLayout gbPnlPreferences = new GridBagLayout();
      GridBagConstraints gbcPnlPreferences = new GridBagConstraints();
      this.setLayout(gbPnlPreferences);

      lblMyCaps = new JLabel("I tuoi CAP :");
      gbcPnlPreferences.gridx = 0;
      gbcPnlPreferences.gridy = 0;
      gbcPnlPreferences.gridwidth = 1;
      gbcPnlPreferences.gridheight = 1;
      gbcPnlPreferences.fill = GridBagConstraints.BOTH;
      gbcPnlPreferences.weightx = 0;
      gbcPnlPreferences.weighty = 0;
      gbcPnlPreferences.anchor = GridBagConstraints.WEST;
      gbcPnlPreferences.insets = new Insets(10, 10, 10, 10);
      gbPnlPreferences.setConstraints(lblMyCaps, gbcPnlPreferences);
      this.add(lblMyCaps);

      txfMyCaps = new JTextField();
      txfMyCaps.setText(String.join(";", this.user.getFavoriteCaps()));
      txfMyCaps.setBackground(new Color(240, 240, 240));
      txfMyCaps.setEditable(false);
      gbcPnlPreferences.gridx = 1;
      gbcPnlPreferences.gridy = 0;
      gbcPnlPreferences.gridwidth = 2;
      gbcPnlPreferences.gridheight = 1;
      gbcPnlPreferences.fill = GridBagConstraints.HORIZONTAL;
      gbcPnlPreferences.weightx = 0;
      gbcPnlPreferences.weighty = 0;
      gbcPnlPreferences.anchor = GridBagConstraints.NORTH;
      gbcPnlPreferences.insets = new Insets(10, 10, 10, 10);
      gbPnlPreferences.setConstraints(txfMyCaps, gbcPnlPreferences);
      this.add(txfMyCaps);

      btnDeleteCaps = new JButton("Elimina Cap preferiti");
      btnDeleteCaps.addActionListener(this);
      gbcPnlPreferences.gridx = 2;
      gbcPnlPreferences.gridy = 1;
      gbcPnlPreferences.gridwidth = 1;
      gbcPnlPreferences.gridheight = 1;
      gbcPnlPreferences.fill = GridBagConstraints.VERTICAL;
      gbcPnlPreferences.weightx = 1;
      gbcPnlPreferences.weighty = 0;
      gbcPnlPreferences.anchor = GridBagConstraints.NORTHEAST;
      gbcPnlPreferences.insets = new Insets(3, 3, 3, 10);
      gbPnlPreferences.setConstraints(btnDeleteCaps, gbcPnlPreferences);
      this.add(btnDeleteCaps);

      pnlFilterCaps = new JPanel();
      pnlFilterCaps.setBorder(BorderFactory.createTitledBorder("Aggiungi CAP"));
      GridBagLayout gbPnlFilterCaps = new GridBagLayout();
      GridBagConstraints gbcPnlFilterCaps = new GridBagConstraints();
      pnlFilterCaps.setLayout(gbPnlFilterCaps);

      lblProvince = new JLabel("Filtra per provincia");
      gbcPnlFilterCaps.gridx = 0;
      gbcPnlFilterCaps.gridy = 0;
      gbcPnlFilterCaps.gridwidth = 1;
      gbcPnlFilterCaps.gridheight = 1;
      gbcPnlFilterCaps.fill = GridBagConstraints.NONE;
      gbcPnlFilterCaps.weightx = 0;
      gbcPnlFilterCaps.weighty = 0;
      gbcPnlFilterCaps.anchor = GridBagConstraints.WEST;
      gbcPnlFilterCaps.insets = new Insets(10, 10, 3, 15);
      gbPnlFilterCaps.setConstraints(lblProvince, gbcPnlFilterCaps);
      pnlFilterCaps.add(lblProvince);

      lblMunicipality = new JLabel("Filtra per comune");
      gbcPnlFilterCaps.gridx = 1;
      gbcPnlFilterCaps.gridy = 0;
      gbcPnlFilterCaps.gridwidth = 1;
      gbcPnlFilterCaps.gridheight = 1;
      gbcPnlFilterCaps.fill = GridBagConstraints.NONE;
      gbcPnlFilterCaps.weightx = 1;
      gbcPnlFilterCaps.weighty = 0;
      gbcPnlFilterCaps.anchor = GridBagConstraints.WEST;
      gbcPnlFilterCaps.insets = new Insets(10, 15, 3, 10);
      gbPnlFilterCaps.setConstraints(lblMunicipality, gbcPnlFilterCaps);
      pnlFilterCaps.add(lblMunicipality);

      String[] provinces = (new TreeSet<String>(Caps.getProvinces())).toArray(new String[0]);
      cmbProvince = new JComboBox<String>(provinces);
      cmbProvince.setSelectedItem(-1);
      cmbProvince.addActionListener(this);
      gbcPnlFilterCaps.gridx = 0;
      gbcPnlFilterCaps.gridy = 1;
      gbcPnlFilterCaps.gridwidth = 1;
      gbcPnlFilterCaps.gridheight = 1;
      gbcPnlFilterCaps.fill = GridBagConstraints.NONE;
      gbcPnlFilterCaps.weightx = 0;
      gbcPnlFilterCaps.weighty = 0;
      gbcPnlFilterCaps.anchor = GridBagConstraints.WEST;
      gbcPnlFilterCaps.insets = new Insets(3, 10, 10, 15);
      gbPnlFilterCaps.setConstraints(cmbProvince, gbcPnlFilterCaps);
      pnlFilterCaps.add(cmbProvince);

      cmbMunicipality = new JComboBox<String>();
      cmbMunicipality.addActionListener(this);
      gbcPnlFilterCaps.gridx = 1;
      gbcPnlFilterCaps.gridy = 1;
      gbcPnlFilterCaps.gridwidth = 1;
      gbcPnlFilterCaps.gridheight = 1;
      gbcPnlFilterCaps.fill = GridBagConstraints.NONE;
      gbcPnlFilterCaps.weightx = 1;
      gbcPnlFilterCaps.weighty = 0;
      gbcPnlFilterCaps.anchor = GridBagConstraints.WEST;
      gbcPnlFilterCaps.insets = new Insets(3, 15, 10, 10);
      gbPnlFilterCaps.setConstraints(cmbMunicipality, gbcPnlFilterCaps);
      pnlFilterCaps.add(cmbMunicipality);

      pnlFoundCaps = new JPanel();
      GridBagLayout gbPnlFoundCaps = new GridBagLayout();
      GridBagConstraints gbcPnlFoundCaps = new GridBagConstraints();
      pnlFoundCaps.setLayout(gbPnlFoundCaps);

      lblFoundCaps = new JLabel("Cap trovati :");
      gbcPnlFoundCaps.gridx = 0;
      gbcPnlFoundCaps.gridy = 0;
      gbcPnlFoundCaps.gridwidth = 1;
      gbcPnlFoundCaps.gridheight = 1;
      gbcPnlFoundCaps.fill = GridBagConstraints.HORIZONTAL;
      gbcPnlFoundCaps.weightx = 0;
      gbcPnlFoundCaps.weighty = 0;
      gbcPnlFoundCaps.anchor = GridBagConstraints.WEST;
      gbcPnlFoundCaps.insets = new Insets(3, 3, 3, 3);
      gbPnlFoundCaps.setConstraints(lblFoundCaps, gbcPnlFoundCaps);
      pnlFoundCaps.add(lblFoundCaps);

      txfFoundCaps = new JTextField();
      txfFoundCaps.setBackground(new Color(240, 240, 240));
      txfFoundCaps.setEditable(false);
      gbcPnlFoundCaps.gridx = 1;
      gbcPnlFoundCaps.gridy = 0;
      gbcPnlFoundCaps.gridwidth = 1;
      gbcPnlFoundCaps.gridheight = 1;
      gbcPnlFoundCaps.fill = GridBagConstraints.HORIZONTAL;
      gbcPnlFoundCaps.weightx = 1;
      gbcPnlFoundCaps.weighty = 0;
      gbcPnlFoundCaps.anchor = GridBagConstraints.WEST;
      gbcPnlFoundCaps.insets = new Insets(3, 3, 3, 3);
      gbPnlFoundCaps.setConstraints(txfFoundCaps, gbcPnlFoundCaps);
      pnlFoundCaps.add(txfFoundCaps);

      gbcPnlFilterCaps.gridx = 0;
      gbcPnlFilterCaps.gridy = 2;
      gbcPnlFilterCaps.gridwidth = 3;
      gbcPnlFilterCaps.gridheight = 1;
      gbcPnlFilterCaps.fill = GridBagConstraints.BOTH;
      gbcPnlFilterCaps.weightx = 0;
      gbcPnlFilterCaps.weighty = 1;
      gbcPnlFilterCaps.anchor = GridBagConstraints.NORTH;
      gbcPnlFilterCaps.insets = new Insets(10, 10, 10, 10);
      gbPnlFilterCaps.setConstraints(pnlFoundCaps, gbcPnlFilterCaps);
      pnlFilterCaps.add(pnlFoundCaps);

      btnAddCaps = new JButton("Aggiungi alla tua lista CAP");
      btnAddCaps.addActionListener(this);
      gbcPnlFilterCaps.gridx = 2;
      gbcPnlFilterCaps.gridy = 3;
      gbcPnlFilterCaps.gridwidth = 1;
      gbcPnlFilterCaps.gridheight = 1;
      gbcPnlFilterCaps.fill = GridBagConstraints.NONE;
      gbcPnlFilterCaps.weightx = 0;
      gbcPnlFilterCaps.weighty = 1;
      gbcPnlFilterCaps.anchor = GridBagConstraints.SOUTHEAST;
      gbcPnlFilterCaps.insets = new Insets(3, 3, 3, 3);
      gbPnlFilterCaps.setConstraints(btnAddCaps, gbcPnlFilterCaps);
      pnlFilterCaps.add(btnAddCaps);

      gbcPnlPreferences.gridx = 0;
      gbcPnlPreferences.gridy = 2;
      gbcPnlPreferences.gridwidth = 3;
      gbcPnlPreferences.gridheight = 1;
      gbcPnlPreferences.fill = GridBagConstraints.NONE;
      gbcPnlPreferences.weightx = 0;
      gbcPnlPreferences.weighty = 0;
      gbcPnlPreferences.anchor = GridBagConstraints.NORTH;
      gbPnlPreferences.setConstraints(pnlFilterCaps, gbcPnlPreferences);
      this.add(pnlFilterCaps);

      // This is an empty label not visible in the view and his
      // only task is to put all other components on top of panel
      // through the wighty property.
      // Maybe is possible to find a thin component istead JLabel.
      JLabel lblLayoutHelper = new JLabel();
      gbcPnlPreferences.gridx = 0;
      gbcPnlPreferences.gridy = 3;
      gbcPnlPreferences.gridwidth = 1;
      gbcPnlPreferences.gridheight = 1;
      gbcPnlPreferences.fill = GridBagConstraints.BOTH;
      gbcPnlPreferences.weightx = 0;
      gbcPnlPreferences.weighty = 1;
      gbcPnlPreferences.anchor = GridBagConstraints.WEST;
      gbcPnlPreferences.insets = new Insets(0, 0, 0, 0);
      gbPnlPreferences.setConstraints(lblLayoutHelper, gbcPnlPreferences);
      this.add(lblLayoutHelper);
   }

   private void refreshUserCaps() {
      Set<String> favouriteCaps = this.user.getFavoriteCaps();
      if (favouriteCaps != null) {
         txfMyCaps.setText(String.join(";", this.user.getFavoriteCaps()));
      } else {
         txfMyCaps.setText("");
      }
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == btnDeleteCaps) {
         // Logic
         this.user.clearFavoriteCaps();
         // View
         refreshUserCaps();

      } else if (e.getSource() == cmbProvince) {
         cmbMunicipality.removeAllItems();
         // If nothing is selected do nothing
         if (cmbProvince.getSelectedIndex() == -1) {
            return;
         }
         // Logic
         String selectedProvince = (String) cmbProvince.getSelectedItem();
         cmbMunicipality.addItem("Tutti i comuni");
         SortedSet<String> municipalityByProvince = new TreeSet<String>(Caps.getMunicipalities(selectedProvince));
         // View
         for (String municipality : municipalityByProvince) {
            cmbMunicipality.addItem(municipality);
         }

      } else if (e.getSource() == cmbMunicipality) {
         // If nothing is selected do nothing
         if (cmbMunicipality.getSelectedIndex() == -1) {
            return;
         }

         // Logic
         String province = null;
         String municipality = null;
         if (cmbProvince.getSelectedIndex() > 0) {
            province = (String) cmbProvince.getSelectedItem();
         }
         if (cmbMunicipality.getSelectedIndex() > 0) {
            municipality = (String) cmbMunicipality.getSelectedItem();
         }
         capsToAdd = Caps.filter(province, municipality);

         // View
         txfFoundCaps.setText(String.join(";", capsToAdd));
         btnAddCaps.setEnabled(true);

      } else if (e.getSource() == btnAddCaps) {
         // Logic
         this.user.addFavoriteCaps(capsToAdd);
         // View
         refreshUserCaps();
      }
   }
}
