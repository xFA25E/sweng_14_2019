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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.SortedSet;

import it.polimi.project14.common.Event;
import it.polimi.project14.common.EventStatus;
import it.polimi.project14.common.SearchFilter;
import it.polimi.project14.user.Caps;
import it.polimi.project14.user.User;
import it.polimi.project14.user.gui.components.*;

public class FrameCivilProtectionUser extends JFrame implements ActionListener, EventsNotificationShower {

   JPanel pnlMain;
   JTabbedPane tbpContents;

   JPanel tabHome;
   JButton btnUpdate;
   PnlUrgentEvents pnlUrgentEvents;
   PnlEventsTable pnlMyEvents;

   JPanel pnlSearch;

   JPanel pnlPreferences;

   JPanel pnlTitle;
   JLabel lblTitle;

   // Logic
   User user;
   Timer clockTimer;

   // TODO: put icon in jar resources
   final Image civilProtectionIcon = Toolkit.getDefaultToolkit()
         .createImage("java/user/data/protezione-civile-icona.png");
   TrayIcon trayIcon = new TrayIcon(civilProtectionIcon, "Protezione Civile");

   public FrameCivilProtectionUser(User user) {
      super("Protezione Civile");

      Objects.requireNonNull(user);
      this.user = user;

      pnlMain = new JPanel();
      GridBagLayout gbPnlMain = new GridBagLayout();
      GridBagConstraints gbcPnlMain = new GridBagConstraints();
      pnlMain.setLayout(gbPnlMain);

      // #region trayForNotification
      if (SystemTray.isSupported()) {
         SystemTray tray = SystemTray.getSystemTray();

         trayIcon.setImageAutoSize(true);
         try {
            tray.add(trayIcon);
         } catch (Exception e) {
         }
      } else {
         System.out.println("Notification unsupported");
      }
      // #endregion

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
         // TODO: show exception to user
         System.out.println("It wasn't possible to load uregnt events");
      }

      // Load my events
      SearchFilter myCapsEventsOn24h = new SearchFilter();
      myCapsEventsOn24h.setCapList(Caps.filter(null, null));
      // Since today (at start of day) until ever
      myCapsEventsOn24h.setExpectedSince(LocalDate.now().atStartOfDay());
      SortedSet<Event> myEvents = null;
      try {
         myEvents = user.searchEvents(myCapsEventsOn24h);
      } catch (Exception e) {
         // TODO: show exception to user
         System.out.println("It wasn't possible to load personal events");
      }

      // Show all
      this.pnlUrgentEvents.setUrgentEvents(urgentEvents);
      this.pnlMyEvents.setEvents(myEvents);
   }

   public void showNotification(Event eventToNotify) throws Exception {
      if (!SystemTray.isSupported()) {
         throw new Exception("Unsupported Tray notification");
      }

      String kind = eventToNotify.getKind();
      LocalDateTime expectedAt = eventToNotify.getExpectedAt();
      int severity = eventToNotify.getSeverity();
      String cap = eventToNotify.getCap();
      EventStatus status = eventToNotify.getStatus();

      String message;
      switch (status) {
         case EXPECTED:
            message = String.format("%s: alle %td del %td %tB, gravità: %d, CAP: %s", kind, expectedAt, expectedAt,
                  expectedAt, severity, cap);
            trayIcon.displayMessage("Evento previsto nei i tuoi CAP", message, MessageType.WARNING);
            break;

         case ONGOING:
            message = String.format("%s in corso, gravità: %d, CAP: %s", kind, severity, cap);
            trayIcon.displayMessage("Evento in corso nei tuoi CAP", message, MessageType.WARNING);
            break;

         case CANCELED:
            message = String.format("L'evento di tipo %s previsto per le %td del %td %tB, CAP: %s è stato cancellato",
                  kind, expectedAt, expectedAt, expectedAt, cap);
            trayIcon.displayMessage("Evento cancellato nei i tuoi CAP", message, MessageType.WARNING);
            break;

         default:
            break;
      }
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == btnUpdate) {
         updateEventsData();
      }
   }
}
