package it.polimi.project14.user.gui;

// import javax.swing.UIManager;
// import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JTabbedPane;
import javax.swing.Timer;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.SortedSet;


import it.polimi.project14.common.Event;
import it.polimi.project14.common.SearchFilter;
import it.polimi.project14.user.User;
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
   Timer clockTimer;

   // Should have user connected
   public FrameCivilProtectionUser(User user) {
      super("Protezione Civile");

      pnlMain = new JPanel();
      GridBagLayout gbPnlMain = new GridBagLayout();
      GridBagConstraints gbcPnlMain = new GridBagConstraints();
      pnlMain.setLayout(gbPnlMain);

      // #region pnlTitle
      pnlTitle = new JPanel();
      GridBagLayout gbPnlTitle = new GridBagLayout();
      GridBagConstraints gbcPnlTitle = new GridBagConstraints();
      pnlTitle.setLayout(gbPnlTitle);
      // lblTime
      lblTitle = new JLabel();
      lblTitle.setFont(new Font("Helvetica", Font.BOLD, 22));
      gbcPnlTitle.gridx = 0;
      gbcPnlTitle.gridy = 0;
      gbcPnlTitle.gridwidth = 1;
      gbcPnlTitle.gridheight = 1;
      gbcPnlTitle.fill = GridBagConstraints.NONE;
      gbcPnlTitle.weightx = 0;
      gbcPnlTitle.weighty = 0;
      gbcPnlTitle.anchor = GridBagConstraints.CENTER;
      gbPnlTitle.setConstraints(lblTitle, gbcPnlTitle);
      pnlTitle.add(lblTitle);
      gbcPnlMain.gridx = 0;
      gbcPnlMain.gridy = 0;
      gbcPnlMain.gridwidth = 1;
      gbcPnlMain.gridheight = 1;
      gbcPnlMain.fill = GridBagConstraints.BOTH;
      gbcPnlMain.weightx = 0;
      gbcPnlMain.weighty = 0;
      gbcPnlMain.anchor = GridBagConstraints.NORTH;
      gbcPnlMain.insets = new Insets(20, 0, 20, 0);
      gbPnlMain.setConstraints(pnlTitle, gbcPnlMain);
      pnlMain.add(pnlTitle);
      // ActionListener for update lblTime
      ActionListener timeUpdate = new ActionListener() {
         @Override
            public void actionPerformed(ActionEvent e) {
               DateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
               String timeNow = timeFormatter.format(new Date());
               lblTitle.setText(timeNow);
            }
         };
         clockTimer = new Timer(1000, timeUpdate);
         clockTimer.setInitialDelay(0);
         clockTimer.start();
      // #endregion

      // #region Tab
      tbpContents = new JTabbedPane();
      tbpContents.setTabPlacement(JTabbedPane.TOP);

      // #region Tab Home
      tabHome = new JPanel();
      GridBagLayout gbTabHome = new GridBagLayout();
      GridBagConstraints gbcTabHome = new GridBagConstraints();
      tabHome.setLayout(gbTabHome);
      // Inside Tab Home

      // Add uregnt events panel
      SortedSet<Event> urgentEvents = null;
      try {
         urgentEvents = user.getUrgentEvents();
      } catch (Exception e) {
         // TODO: show exception
      }

      JScrollPane pnlUrgentEvents = new PnlUrgentEvents(urgentEvents);
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
      SearchFilter myCapsEventsOn24h = new SearchFilter();
      myCapsEventsOn24h.setCapList(user.getFavoriteCaps());
      myCapsEventsOn24h.setExpectedSince(LocalDateTime.of(LocalDate.now(), LocalTime.MIN));
      myCapsEventsOn24h.setExpectedUntil(LocalDateTime.of(LocalDate.now(), LocalTime.MAX));

      SortedSet<Event> myEvents = null;
      try {
         myEvents = user.searchEvents(myCapsEventsOn24h);
      } catch (Exception e) {
         // TODO: show exception
      }

      JPanel pnlMyEvents = new PnlEventsTable(myEvents);
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
      // make the panel scrollable
      JScrollPane scrollableHome = new JScrollPane(tabHome);

      tbpContents.addTab("Home", scrollableHome);
      // #endregion

      // Tab Search
      PnlSearch pnlSearch = new PnlSearch();
      tbpContents.addTab("Effettua ricerca", pnlSearch);
      // End Tab Search

      // Tab Preferences
      PnlPreferences tabPreferences = new PnlPreferences(user);
      tbpContents.addTab("Impostazioni", tabPreferences);
      // End Tab Preferences

      gbcPnlMain.gridx = 0;
      gbcPnlMain.gridy = 1;
      gbcPnlMain.gridwidth = 1;
      gbcPnlMain.gridheight = 1;
      gbcPnlMain.fill = GridBagConstraints.BOTH;
      gbcPnlMain.weightx = 1;
      gbcPnlMain.weighty = 1;
      gbcPnlMain.anchor = GridBagConstraints.CENTER;
      gbcPnlMain.insets = new Insets(5, 3, 0, 3);
      gbPnlMain.setConstraints(tbpContents, gbcPnlMain);
      pnlMain.add(tbpContents);
      // #endregion

      setDefaultCloseOperation(EXIT_ON_CLOSE);

      setContentPane(pnlMain);
      pack();
      setVisible(true);
   }
}
