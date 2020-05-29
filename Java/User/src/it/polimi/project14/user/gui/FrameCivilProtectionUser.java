package it.polimi.project14.user.gui;

import javax.swing.JFrame;
// import javax.swing.UIManager;
// import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDateTime;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

import it.polimi.project14.common.Event;
import it.polimi.project14.user.gui.components.*;

public class FrameCivilProtectionUser extends JFrame {
   static FrameCivilProtectionUser theJfrCivilProtectionUser;

   JPanel pnlMain;
   JTabbedPane tbpContents;

   JPanel tabHome;

   JPanel pnlSearch;

   JPanel pnlPreferences;

   JPanel pnlTitle;
   JLabel lblTitle;

   public static void main(String args[]) {
      // try {
      // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      // } catch (ClassNotFoundException e) {
      // } catch (InstantiationException e) {
      // } catch (IllegalAccessException e) {
      // } catch (UnsupportedLookAndFeelException e) {
      // }
      // theJfrCivilProtectionUser = new JfrCivilProtectionUser();
      SortedSet<Event> testSetEvents = new TreeSet<Event>();
      for (int i = 0; i < 10; i++) {
         Event testEvent = new Event();
         testEvent.setKind("Terremoto");
         testEvent.setCap("22100");
         testEvent.setSeverity(9);
         testEvent.setExpectedAt(LocalDateTime.of(2020, 5, 5, i, 0));
         testEvent.setMessage("BlaBlaBlaBlaBla");

         testSetEvents.add(testEvent);
      }
      theJfrCivilProtectionUser = new FrameCivilProtectionUser(testSetEvents);
   }

   // Should have user connected
   public FrameCivilProtectionUser(SortedSet<Event> eventsData) {
      super("Protezione Civile");

      pnlMain = new JPanel();
      GridBagLayout gbPnlMain = new GridBagLayout();
      GridBagConstraints gbcPnlMain = new GridBagConstraints();
      pnlMain.setLayout(gbPnlMain);

      pnlTitle = new JPanel();
      GridBagLayout gbPnlTitle = new GridBagLayout();
      GridBagConstraints gbcPnlTitle = new GridBagConstraints();
      pnlTitle.setLayout(gbPnlTitle);

      // lblTitle = new JLabel("PROTEZIONE CIVILE");
      // gbcPnlTitle.gridx = 0;
      // gbcPnlTitle.gridy = 0;
      // gbcPnlTitle.gridwidth = 1;
      // gbcPnlTitle.gridheight = 1;
      // gbcPnlTitle.fill = GridBagConstraints.NONE;
      // gbcPnlTitle.weightx = 0;
      // gbcPnlTitle.weighty = 0;
      // gbcPnlTitle.anchor = GridBagConstraints.CENTER;
      // gbPnlTitle.setConstraints(lblTitle, gbcPnlTitle);
      // pnlTitle.add(lblTitle);
      // gbcPnlMain.gridx = 0;
      // gbcPnlMain.gridy = 0;
      // gbcPnlMain.gridwidth = 1;
      // gbcPnlMain.gridheight = 1;
      // gbcPnlMain.fill = GridBagConstraints.BOTH;
      // gbcPnlMain.weightx = 0;
      // gbcPnlMain.weighty = 0;
      // gbcPnlMain.anchor = GridBagConstraints.NORTH;
      // gbcPnlMain.insets = new Insets(20, 0, 20, 0);
      // gbPnlMain.setConstraints(pnlTitle, gbcPnlMain);
      // pnlMain.add(pnlTitle);

      tbpContents = new JTabbedPane();
      tbpContents.setTabPlacement(JTabbedPane.TOP);

      // Tab Home
      tabHome = new JPanel();
      GridBagLayout gbTabHome = new GridBagLayout();
      GridBagConstraints gbcTabHome = new GridBagConstraints();
      tabHome.setLayout(gbTabHome);
      // Inside Tab Home

      // Add uregnt events panel
      JScrollPane pnlUrgentEvents = new PnlUrgentAllarms(eventsData);
      pnlUrgentEvents.setBorder(BorderFactory.createTitledBorder("Allarmi in evidenza"));
      gbcTabHome.gridx = 0;
      gbcTabHome.gridy = 0;
      gbcTabHome.gridwidth = 1;
      gbcTabHome.gridheight = 1;
      gbcTabHome.fill = GridBagConstraints.BOTH;
      gbcTabHome.weightx = 1;
      gbcTabHome.weighty = 1;
      gbcTabHome.anchor = GridBagConstraints.NORTH;
      gbcTabHome.insets = new Insets(5, 5, 0, 5);
      gbTabHome.setConstraints(pnlUrgentEvents, gbcTabHome);
      tabHome.add(pnlUrgentEvents);
      // Add my events
      JPanel pnlMyEvents = new PnlEventsTable(eventsData);
      pnlMyEvents.setBorder(BorderFactory.createTitledBorder("Prossime 24h nei tuoi CAP preferiti"));
      gbcTabHome.gridx = 0;
      gbcTabHome.gridy = 1;
      gbcTabHome.gridwidth = 1;
      gbcTabHome.gridheight = 1;
      gbcTabHome.fill = GridBagConstraints.BOTH;
      gbcTabHome.weightx = 0;
      gbcTabHome.weighty = 0;
      gbcTabHome.anchor = GridBagConstraints.NORTH;
      gbcTabHome.insets = new Insets(5, 5, 0, 5);
      gbTabHome.setConstraints(pnlMyEvents, gbcTabHome);
      tabHome.add(pnlMyEvents);
      // make the panlel scrollable
      JScrollPane scrollableHome = new JScrollPane(tabHome);

      tbpContents.addTab("Home", scrollableHome);
      // End Tab Home


      // Tab Search
      PnlSearch pnlSearch = new PnlSearch();
      tbpContents.addTab("Effettua ricerca", pnlSearch);
      // End Tab Search

      // Tab Preferences
      PnlPreferences tabPreferences = new PnlPreferences();
      tbpContents.addTab("Impostazioni", tabPreferences);
      // End Tab Preferences

      gbcPnlMain.gridx = 0;
      gbcPnlMain.gridy = 0;
      gbcPnlMain.gridwidth = 1;
      gbcPnlMain.gridheight = 1;
      gbcPnlMain.fill = GridBagConstraints.BOTH;
      gbcPnlMain.weightx = 1;
      gbcPnlMain.weighty = 1;
      gbcPnlMain.anchor = GridBagConstraints.CENTER;
      gbcPnlMain.insets = new Insets(5, 3, 0, 3);
      gbPnlMain.setConstraints(tbpContents, gbcPnlMain);
      pnlMain.add(tbpContents);

      setDefaultCloseOperation(EXIT_ON_CLOSE);

      setContentPane(pnlMain);
      pack();
      setVisible(true);
   }
}
