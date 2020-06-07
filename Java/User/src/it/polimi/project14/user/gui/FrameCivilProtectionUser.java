package it.polimi.project14.user.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.Timer;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemTray;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.Image;
import java.awt.Toolkit;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.SortedSet;

import it.polimi.project14.common.Event;
import it.polimi.project14.common.EventStatus;
import it.polimi.project14.common.SearchFilter;
import it.polimi.project14.user.User;
import it.polimi.project14.user.gui.components.*;

public class FrameCivilProtectionUser extends JFrame implements ActionListener, EventsNotificationShower {

   JPanel pnlMain;
   JTabbedPane tbpContents;

   JPanel tabHome;
   JButton btnUpdate;
   PnlUrgentEvents pnlUrgentEvents;
   PnlEventsTable pnlMyEvents;

   PnlSearch pnlSearch;

   JPanel pnlPreferences;

   JPanel pnlTitle;
   JLabel lblTitle;

   // Logic
   User user;
   Timer clockTimer;
   Boolean isTraySupported = false;

   final Image civilProtectionIcon = Toolkit.getDefaultToolkit()
                                    .getImage(getClass().getClassLoader().getResource("protezione-civile.png"));
   TrayIcon trayIcon;

   public FrameCivilProtectionUser(User user) {
      super("Protezione Civile");

      Objects.requireNonNull(user);
      this.user = user;

      // Check tray icon
      if (!SystemTray.isSupported()) {
         System.out.println("Notifiche di sistema non supportate. I nuovi eventi verranno inviate su questo terminale");
      } else {
         SystemTray tray = SystemTray.getSystemTray();
         trayIcon = new TrayIcon(civilProtectionIcon, "Protezione Civile");
         trayIcon.setImageAutoSize(true);
         try {
            tray.add(trayIcon);
            isTraySupported = true;
         } catch (Exception e) {
            System.out.println(e.getMessage());
         }
      }

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

      // #region reload button
      btnUpdate = new JButton("Aggiorna dati");
      btnUpdate.addActionListener(this);
      gbcTabHome.gridx = 0;
      gbcTabHome.gridy = 0;
      gbcTabHome.gridwidth = 1;
      gbcTabHome.gridheight = 1;
      gbcTabHome.fill = GridBagConstraints.NONE;
      gbcTabHome.weightx = 0;
      gbcTabHome.weighty = 0;
      gbcTabHome.anchor = GridBagConstraints.EAST;
      gbcTabHome.insets = new Insets(5, 5, 0, 5);
      gbTabHome.setConstraints(btnUpdate, gbcTabHome);
      tabHome.add(btnUpdate);

      pnlUrgentEvents = new PnlUrgentEvents();
      pnlUrgentEvents.setBorder(BorderFactory.createTitledBorder("Allarmi in evidenza"));
      gbcTabHome.gridx = 0;
      gbcTabHome.gridy = 1;
      gbcTabHome.gridwidth = 1;
      gbcTabHome.gridheight = 1;
      gbcTabHome.fill = GridBagConstraints.BOTH;
      gbcTabHome.weightx = 1;
      gbcTabHome.weighty = 0;
      gbcTabHome.anchor = GridBagConstraints.NORTH;
      gbcTabHome.insets = new Insets(5, 5, 0, 5);
      gbTabHome.setConstraints(pnlUrgentEvents, gbcTabHome);
      tabHome.add(pnlUrgentEvents);

      pnlMyEvents = new PnlEventsTable();
      pnlMyEvents.setBorder(BorderFactory.createTitledBorder("Prossime 24h nei tuoi CAP preferiti"));
      gbcTabHome.gridx = 0;
      gbcTabHome.gridy = 2;
      gbcTabHome.gridwidth = 1;
      gbcTabHome.gridheight = 1;
      gbcTabHome.fill = GridBagConstraints.BOTH;
      gbcTabHome.weightx = 1;
      gbcTabHome.weighty = 0;
      gbcTabHome.anchor = GridBagConstraints.NORTH;
      gbcTabHome.insets = new Insets(20, 5, 0, 5);
      gbTabHome.setConstraints(pnlMyEvents, gbcTabHome);
      tabHome.add(pnlMyEvents);

      // This is an empty label not visible in the view and his
      // only task is to put all other components on top of panel
      // through the wighty property.
      // Maybe is possible to find a thin component istead JLabel.
      JLabel lblLayoutHelper = new JLabel();
      gbcTabHome.gridx = 0;
      gbcTabHome.gridy = 3;
      gbcTabHome.gridwidth = 1;
      gbcTabHome.gridheight = 1;
      gbcTabHome.fill = GridBagConstraints.NONE;
      gbcTabHome.weightx = 1;
      gbcTabHome.weighty = 1;
      gbcTabHome.anchor = GridBagConstraints.NORTH;
      gbcTabHome.insets = new Insets(0, 0, 0, 0);
      gbTabHome.setConstraints(lblLayoutHelper, gbcTabHome);
      tabHome.add(lblLayoutHelper);

      // make the panel scrollable
      JScrollPane scrollableHome = new JScrollPane(tabHome);

      tbpContents.addTab("Home", scrollableHome);
      // #endregion

      // Tab Search
      PnlSearch pnlSearch = new PnlSearch(user);
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

      updateEventsData();

      setDefaultCloseOperation(EXIT_ON_CLOSE);

      setContentPane(pnlMain);
      pack();
      setVisible(true);
   }

   // Load and show data needed for Home tab:
   // uregentEvents and myEvents
   private void updateEventsData() {
      // Load urgent events
      SortedSet<Event> urgentEvents = null;
      try {
         urgentEvents = user.getUrgentEvents();
      } catch (Exception e) {
         System.out.println("It wasn't possible to load uregnt events");
      }

      // Load my events
      SearchFilter myCapsEventsOn24h = new SearchFilter();
      myCapsEventsOn24h.setCapList(user.getFavoriteCaps());
      // Since today (at start of day) until ever
      myCapsEventsOn24h.setExpectedSince(LocalDateTime.now().withMinute(0));
      SortedSet<Event> myEvents = null;
      try {
         myEvents = user.searchEvents(myCapsEventsOn24h);
      } catch (Exception e) {
         System.out.println("It wasn't possible to load personal events");
      }

      // Show all
      this.pnlUrgentEvents.setUrgentEvents(urgentEvents);
      this.pnlMyEvents.setEvents(myEvents, true);
   }

   public void showNotification(Event eventToNotify) throws Exception {
      String kind = eventToNotify.getKind();
      LocalDateTime expectedAt = eventToNotify.getExpectedAt();
      int severity = eventToNotify.getSeverity();
      String cap = eventToNotify.getCap();
      EventStatus status = eventToNotify.getStatus();

      String title, message;
      switch (status) {
         case EXPECTED:
            title = "Evento previsto nei i tuoi CAP";
            message = String.format("%s: alle %td del %td %tB, gravità: %d, CAP: %s", kind, expectedAt, expectedAt,
                  expectedAt, severity, cap);

            break;

         case ONGOING:
            title = "Evento in corso nei tuoi CAP";
            message = String.format("%s in corso, gravità: %d, CAP: %s", kind, severity, cap);

            break;

         case CANCELED:
            title = "Evento cancellato nei i tuoi CAP";
            message = String.format("L'evento di tipo %s previsto per le %td del %td %tB, CAP: %s è stato cancellato",
                  kind, expectedAt, expectedAt, expectedAt, cap);

            break;

         default:
            return;
      }

      if (isTraySupported) {
         this.trayIcon.displayMessage(title, message, MessageType.WARNING);
      } else {
          System.out.println(String.format("%s%n %s%n", title, message));
      }
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == btnUpdate) {
         updateEventsData();
      }
   }
}
