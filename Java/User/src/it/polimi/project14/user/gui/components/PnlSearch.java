package it.polimi.project14.user.gui.components;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.time.LocalDateTime;

import java.util.HashSet;
import java.util.TreeSet;

import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;

import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.optionalusertools.DateTimeChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateTimeChangeEvent;

import it.polimi.project14.common.Event;
import it.polimi.project14.common.SearchFilter;

public class PnlSearch extends JPanel implements ActionListener, DateTimeChangeListener {

   PnlSearchFilter pnlSearchFilter;
   PnlEventsTable pnlFoundEvents;

   SearchFilter currFilter;

   public PnlSearch() {
      super();

      GridBagLayout gbSearch = new GridBagLayout();
      GridBagConstraints gbcSearch = new GridBagConstraints();
      setLayout(gbSearch);

      pnlSearchFilter = new PnlSearchFilter();
      gbcSearch.gridx = 0;
      gbcSearch.gridy = 0;
      gbcSearch.gridwidth = 1;
      gbcSearch.gridheight = 1;
      gbcSearch.fill = GridBagConstraints.BOTH;
      gbcSearch.weightx = 0;
      gbcSearch.weighty = 0;
      gbcSearch.anchor = GridBagConstraints.WEST;
      gbcSearch.insets = new Insets(0, 0, 0, 0);
      gbSearch.setConstraints(pnlSearchFilter, gbcSearch);
      add(pnlSearchFilter);

      pnlSearchFilter.btFind.addActionListener(this);
      pnlSearchFilter.dtpDateSince.addDateTimeChangeListener(this);
      pnlSearchFilter.dtpDateSince.addDateTimeChangeListener(this);

      pnlFoundEvents = new PnlEventsTable(new TreeSet<Event>());
      pnlFoundEvents.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.gray));
      gbcSearch.gridx = 0;
      gbcSearch.gridy = 1;
      gbcSearch.gridwidth = 1;
      gbcSearch.gridheight = 1;
      gbcSearch.fill = GridBagConstraints.BOTH;
      gbcSearch.weightx = 1;
      gbcSearch.weighty = 1;
      gbcSearch.anchor = GridBagConstraints.WEST;
      gbcSearch.insets = new Insets(0, 0, 0, 0);
      gbSearch.setConstraints(pnlFoundEvents, gbcSearch);
      add(pnlFoundEvents);
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == pnlSearchFilter.btFind) {
         // Action for btFind
         currFilter = new SearchFilter();

         // With datepicker
         LocalDateTime sinceDateToFilter = pnlSearchFilter.dtpDateSince.getDateTimePermissive();
         LocalDateTime untilDateToFilter = pnlSearchFilter.dtpDateUntil.getDateTimePermissive();

         String province = (String) pnlSearchFilter.cmbProvince.getSelectedItem();
         String municipality = (String) pnlSearchFilter.cmbMunicipality.getSelectedItem();
         HashSet<String> capsToFilter = new HashSet<String>();
         if (province != null && municipality != null) {
            // capsToFilter = Caps.filter(province, municipality);
         } else if (province != null) {
            // capsToFilter = Caps.filter(province);
         } else if (municipality != null) {
            // capsToFilter = Caps.filter(dataMuniciplaity)
         }

         String kindToFilter = (String) pnlSearchFilter.cmbKind.getSelectedItem();

         currFilter.setExpectedSince(sinceDateToFilter);
         currFilter.setExpectedUntil(untilDateToFilter);
         currFilter.setCapList(capsToFilter);
         currFilter.setKind(kindToFilter);
      }
   }

   @Override
   public void dateOrTimeChanged(DateTimeChangeEvent e) {
      if (e.getSource() == pnlSearchFilter.dtpDateSince) {
         DateTimePicker since = pnlSearchFilter.dtpDateSince;
         DateTimePicker until = pnlSearchFilter.dtpDateUntil;
         if (since.getDateTimePermissive().isAfter(until.getDateTimePermissive())) {
            until.setDateTimePermissive(since.getDateTimePermissive().plusDays(1));
         }
      } else if (e.getSource() == pnlSearchFilter.dtpDateSince) {
         DateTimePicker since = pnlSearchFilter.dtpDateSince;
         DateTimePicker until = pnlSearchFilter.dtpDateUntil;
         if (until.getDateTimePermissive().isBefore(since.getDateTimePermissive())) {
            since.setDateTimePermissive(until.getDateTimePermissive().minusDays(1));
         }
      }
   }

   public class PnlSearchFilter extends JPanel {
      JLabel lbDataSince;
      DateTimePicker dtpDateSince;
      JLabel lbDataUntil;
      DateTimePicker dtpDateUntil;
      JLabel lbProvince;
      JComboBox<String> cmbProvince;
      JLabel lbMunicipality;
      JComboBox<String> cmbMunicipality;
      JLabel lbKind;
      JComboBox<String> cmbKind;
      JButton btFind;

      public PnlSearchFilter() {
         super();

         GridBagLayout gbSearchFilter = new GridBagLayout();
         GridBagConstraints gbcSearchFilter = new GridBagConstraints();
         setLayout(gbSearchFilter);

         // #region Column 0
         lbDataSince = new JLabel("Ricerca da :");
         gbcSearchFilter.gridx = 0;
         gbcSearchFilter.gridy = 0;
         gbcSearchFilter.gridwidth = 1;
         gbcSearchFilter.gridheight = 1;
         gbcSearchFilter.fill = GridBagConstraints.NONE;
         gbcSearchFilter.weightx = 0;
         gbcSearchFilter.weighty = 0;
         gbcSearchFilter.anchor = GridBagConstraints.WEST;
         gbcSearchFilter.insets = new Insets(30, 30, 0, 30);
         gbSearchFilter.setConstraints(lbDataSince, gbcSearchFilter);
         add(lbDataSince);

         dtpDateSince = new DateTimePicker();
         dtpDateSince.setDateTimePermissive(LocalDateTime.now().minusDays(1));
         gbcSearchFilter.gridx = 0;
         gbcSearchFilter.gridy = 1;
         gbcSearchFilter.gridwidth = 1;
         gbcSearchFilter.gridheight = 1;
         gbcSearchFilter.fill = GridBagConstraints.NONE;
         gbcSearchFilter.weightx = 0;
         gbcSearchFilter.weighty = 0;
         gbcSearchFilter.anchor = GridBagConstraints.WEST;
         gbcSearchFilter.insets = new Insets(0, 30, 10, 30);
         gbSearchFilter.setConstraints(dtpDateSince, gbcSearchFilter);
         add(dtpDateSince);

         lbDataUntil = new JLabel("Ricerca a :");
         gbcSearchFilter.gridx = 0;
         gbcSearchFilter.gridy = 2;
         gbcSearchFilter.gridwidth = 1;
         gbcSearchFilter.gridheight = 1;
         gbcSearchFilter.fill = GridBagConstraints.NONE;
         gbcSearchFilter.weightx = 0;
         gbcSearchFilter.weighty = 0;
         gbcSearchFilter.anchor = GridBagConstraints.WEST;
         gbcSearchFilter.insets = new Insets(10, 30, 0, 30);
         gbSearchFilter.setConstraints(lbDataUntil, gbcSearchFilter);
         add(lbDataUntil);

         dtpDateUntil = new DateTimePicker();
         dtpDateUntil.setDateTimePermissive(LocalDateTime.now());
         gbcSearchFilter.gridx = 0;
         gbcSearchFilter.gridy = 3;
         gbcSearchFilter.gridwidth = 1;
         gbcSearchFilter.gridheight = 1;
         gbcSearchFilter.fill = GridBagConstraints.NONE;
         gbcSearchFilter.weightx = 0;
         gbcSearchFilter.weighty = 0;
         gbcSearchFilter.anchor = GridBagConstraints.WEST;
         gbcSearchFilter.insets = new Insets(0, 30, 30, 30);
         gbSearchFilter.setConstraints(dtpDateUntil, gbcSearchFilter);
         add(dtpDateUntil);
         // #endregion

         // #region Column 1
         lbProvince = new JLabel("Provincia :");
         gbcSearchFilter.gridx = 1;
         gbcSearchFilter.gridy = 0;
         gbcSearchFilter.gridwidth = 1;
         gbcSearchFilter.gridheight = 1;
         gbcSearchFilter.fill = GridBagConstraints.NONE;
         gbcSearchFilter.weightx = 0;
         gbcSearchFilter.weighty = 0;
         gbcSearchFilter.anchor = GridBagConstraints.WEST;
         gbcSearchFilter.insets = new Insets(30, 30, 0, 30);
         gbSearchFilter.setConstraints(lbProvince, gbcSearchFilter);
         add(lbProvince);

         String[] dataProvince = { "Chocolate", "Ice Cream", "Apple Pie" };
         cmbProvince = new JComboBox<String>(dataProvince);
         gbcSearchFilter.gridx = 1;
         gbcSearchFilter.gridy = 1;
         gbcSearchFilter.gridwidth = 1;
         gbcSearchFilter.gridheight = 1;
         gbcSearchFilter.fill = GridBagConstraints.NONE;
         gbcSearchFilter.weightx = 0;
         gbcSearchFilter.weighty = 0;
         gbcSearchFilter.anchor = GridBagConstraints.WEST;
         gbcSearchFilter.insets = new Insets(0, 30, 10, 30);
         gbSearchFilter.setConstraints(cmbProvince, gbcSearchFilter);
         add(cmbProvince);

         lbMunicipality = new JLabel("Comune :");
         gbcSearchFilter.gridx = 1;
         gbcSearchFilter.gridy = 2;
         gbcSearchFilter.gridwidth = 1;
         gbcSearchFilter.gridheight = 1;
         gbcSearchFilter.fill = GridBagConstraints.NONE;
         gbcSearchFilter.weightx = 0;
         gbcSearchFilter.weighty = 0;
         gbcSearchFilter.anchor = GridBagConstraints.WEST;
         gbcSearchFilter.insets = new Insets(10, 30, 0, 30);
         gbSearchFilter.setConstraints(lbMunicipality, gbcSearchFilter);
         add(lbMunicipality);

         String[] dataMuniciplaity = { "Chocolate", "Ice Cream", "Apple Pie" };
         cmbMunicipality = new JComboBox<String>(dataMuniciplaity);
         gbcSearchFilter.gridx = 1;
         gbcSearchFilter.gridy = 3;
         gbcSearchFilter.gridwidth = 1;
         gbcSearchFilter.gridheight = 1;
         gbcSearchFilter.fill = GridBagConstraints.NONE;
         gbcSearchFilter.weightx = 0;
         gbcSearchFilter.weighty = 0;
         gbcSearchFilter.anchor = GridBagConstraints.WEST;
         gbcSearchFilter.insets = new Insets(0, 30, 30, 30);
         gbSearchFilter.setConstraints(cmbMunicipality, gbcSearchFilter);
         add(cmbMunicipality);
         // #endregion

         // #region Column 2
         lbKind = new JLabel("Tipo :");
         gbcSearchFilter.gridx = 2;
         gbcSearchFilter.gridy = 0;
         gbcSearchFilter.gridwidth = 1;
         gbcSearchFilter.gridheight = 1;
         gbcSearchFilter.fill = GridBagConstraints.NONE;
         gbcSearchFilter.weightx = 0;
         gbcSearchFilter.weighty = 0;
         gbcSearchFilter.anchor = GridBagConstraints.WEST;
         gbcSearchFilter.insets = new Insets(30, 30, 0, 30);
         gbSearchFilter.setConstraints(lbKind, gbcSearchFilter);
         add(lbKind);

         String[] dataKind = { "Chocolate", "Ice Cream", "Apple Pie" };
         cmbKind = new JComboBox<String>(dataKind);
         gbcSearchFilter.gridx = 2;
         gbcSearchFilter.gridy = 1;
         gbcSearchFilter.gridwidth = 1;
         gbcSearchFilter.gridheight = 1;
         gbcSearchFilter.fill = GridBagConstraints.NONE;
         gbcSearchFilter.weightx = 1;
         gbcSearchFilter.weighty = 0;
         gbcSearchFilter.anchor = GridBagConstraints.WEST;
         gbcSearchFilter.insets = new Insets(0, 30, 10, 30);
         gbSearchFilter.setConstraints(cmbKind, gbcSearchFilter);
         add(cmbKind);

         btFind = new JButton("Cerca");
         // btFind.addActionListener(this);
         gbcSearchFilter.gridx = 2;
         gbcSearchFilter.gridy = 3;
         gbcSearchFilter.gridwidth = 1;
         gbcSearchFilter.gridheight = 1;
         gbcSearchFilter.fill = GridBagConstraints.NONE;
         gbcSearchFilter.weightx = 1;
         gbcSearchFilter.weighty = 0;
         gbcSearchFilter.anchor = GridBagConstraints.EAST;
         gbcSearchFilter.insets = new Insets(0, 0, 30, 30);
         gbSearchFilter.setConstraints(btFind, gbcSearchFilter);
         add(btFind);
         // #endregion
      }

   }

}