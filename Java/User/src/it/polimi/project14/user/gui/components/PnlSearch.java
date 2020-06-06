package it.polimi.project14.user.gui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.time.LocalDateTime;

import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;

import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.optionalusertools.DateTimeChangeListener;
import com.github.lgooddatepicker.zinternaltools.DateTimeChangeEvent;

import it.polimi.project14.user.Caps;
import it.polimi.project14.user.User;
import it.polimi.project14.common.Event;
import it.polimi.project14.common.SearchFilter;

public class PnlSearch extends JPanel implements ActionListener {

   PnlSearchFilter pnlSearchFilter;
   PnlEventsTable pnlFoundEvents;

   SearchFilter currFilter;

   User user;

   public PnlSearch(User user) {
      super();

      this.user = user;

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
         SearchFilter currFilter = pnlSearchFilter.getFilter();
         Objects.requireNonNull(currFilter);

         // Load searched events
         SortedSet<Event> foundEvents = null;
         try {
            foundEvents = user.searchEvents(currFilter);
         } catch (Exception ex) {
            // TODO: show exception to user
            System.out.println("It wasn't possible to load personal events");
         }

         // Show all
         this.pnlFoundEvents.setEvents(foundEvents, true);
         // currFilter = new SearchFilter();

         // // With datepicker
         // LocalDateTime sinceDateToFilter =
         // pnlSearchFilter.dtpDateSince.getDateTimePermissive();
         // LocalDateTime untilDateToFilter =
         // pnlSearchFilter.dtpDateUntil.getDateTimePermissive();

         // String province = (String) pnlSearchFilter.cmbProvince.getSelectedItem();
         // String municipality = (String)
         // pnlSearchFilter.cmbMunicipality.getSelectedItem();
         // HashSet<String> capsToFilter = new HashSet<String>();
         // capsToFilter = (HashSet<String>) Caps.filter(province, municipality);

         // String kindToFilter = (String) pnlSearchFilter.cmbKind.getSelectedItem();

         // currFilter.setExpectedSince(sinceDateToFilter);
         // currFilter.setExpectedUntil(untilDateToFilter);
         // currFilter.setCapList(capsToFilter);
         // currFilter.setKind(kindToFilter);
      }
   }

   public class PnlSearchFilter extends JPanel implements ActionListener, DateTimeChangeListener {

      JLabel lbDataSince;
      DateTimePicker dtpDateSince;
      JLabel lbDataUntil;
      DateTimePicker dtpDateUntil;

      JLabel lbProvince;
      JComboBox<String> cmbProvince;
      JLabel lbMunicipality;
      JComboBox<String> cmbMunicipality;

      JLabel lbKind;
      JTextField txfKind;
      JButton btFind;

      private SearchFilter filter = new SearchFilter();

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
         dtpDateSince.addDateTimeChangeListener(this);
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
         dtpDateUntil.addDateTimeChangeListener(this);
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

         String[] provinces = (new TreeSet<String>(Caps.getProvinces())).toArray(new String[0]);
         cmbProvince = new JComboBox<String>(provinces);
         cmbProvince.setSelectedItem(-1);
         cmbProvince.addActionListener(this);
         gbcSearchFilter.gridx = 1;
         gbcSearchFilter.gridy = 1;
         gbcSearchFilter.gridwidth = 1;
         gbcSearchFilter.gridheight = 1;
         gbcSearchFilter.fill = GridBagConstraints.BOTH;
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

         cmbMunicipality = new JComboBox<String>();
         cmbMunicipality.addActionListener(this);
         gbcSearchFilter.gridx = 1;
         gbcSearchFilter.gridy = 3;
         gbcSearchFilter.gridwidth = 1;
         gbcSearchFilter.gridheight = 1;
         gbcSearchFilter.fill = GridBagConstraints.BOTH;
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

         txfKind = new JTextField();
         txfKind.setPreferredSize(new Dimension(100, txfKind.getPreferredSize().height));
         txfKind.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent event) {
               warn();
            }

            public void removeUpdate(DocumentEvent event) {
               warn();
            }

            public void insertUpdate(DocumentEvent event) {
               warn();
            }

            public void warn() {
               String kind = txfKind.getText();
               filter.setKind(kind);
            }
         });

         gbcSearchFilter.gridx = 2;
         gbcSearchFilter.gridy = 1;
         gbcSearchFilter.gridwidth = 1;
         gbcSearchFilter.gridheight = 1;
         gbcSearchFilter.fill = GridBagConstraints.NONE;
         gbcSearchFilter.weightx = 0;
         gbcSearchFilter.weighty = 0;
         gbcSearchFilter.anchor = GridBagConstraints.WEST;
         gbcSearchFilter.insets = new Insets(0, 30, 10, 30);
         gbSearchFilter.setConstraints(txfKind, gbcSearchFilter);
         add(txfKind);

         btFind = new JButton("Cerca");
         // btFind.addActionListener(this);
         gbcSearchFilter.gridx = 2;
         gbcSearchFilter.gridy = 3;
         gbcSearchFilter.gridwidth = 1;
         gbcSearchFilter.gridheight = 1;
         gbcSearchFilter.fill = GridBagConstraints.NONE;
         gbcSearchFilter.weightx = 1;
         gbcSearchFilter.weighty = 0;
         gbcSearchFilter.anchor = GridBagConstraints.WEST;
         gbcSearchFilter.insets = new Insets(0, 55, 30, 0);
         gbSearchFilter.setConstraints(btFind, gbcSearchFilter);
         add(btFind);
         // #endregion
      }

      public SearchFilter getFilter() {
         return this.filter;
      }

      public void actionPerformed(ActionEvent e) {
         if (e.getSource() == cmbProvince) {
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

            filter.setCapList(Caps.filter(province, municipality));
         }
      }

      @Override
      public void dateOrTimeChanged(DateTimeChangeEvent e) {
         if (e.getSource() == dtpDateSince) {
            DateTimePicker since = dtpDateSince;
            DateTimePicker until = dtpDateUntil;

            if (since.getDateTimePermissive().isAfter(until.getDateTimePermissive())) {
               until.setDateTimePermissive(since.getDateTimePermissive().plusDays(1));
            }

            filter.setExpectedSince(since.getDateTimePermissive());
            filter.setExpectedUntil(until.getDateTimePermissive());

         } else if (e.getSource() == dtpDateUntil) {
            DateTimePicker since = dtpDateSince;
            DateTimePicker until = dtpDateUntil;

            if (until.getDateTimePermissive().isBefore(since.getDateTimePermissive())) {
               since.setDateTimePermissive(until.getDateTimePermissive().minusDays(1));
            }

            filter.setExpectedSince(since.getDateTimePermissive());
            filter.setExpectedUntil(until.getDateTimePermissive());

         }
      }
   }
}