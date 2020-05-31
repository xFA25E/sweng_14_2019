package it.polimi.project14.user.gui.components;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;

import java.time.LocalDateTime;

import java.util.HashMap;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import it.polimi.project14.common.*;

public class PnlUrgentEvents extends JScrollPane {
   static PnlUrgentEvents thePnlUrgentAllarms;

   JPanel pnPanel43;
   JTable tbTable27;

   JPanel pnPanel3;
   JTable tbTable2;

   JPanel pnPanel5;
   JTable tbTable4;

   JPanel pnPanel6;
   JTable tbTable5;

   public PnlUrgentEvents(SortedSet<Event> eventsData) {
      super();

      SortedMap<Integer, SortedSet<Event>> mapTimeEvents = new TreeMap<>();
      if (eventsData != null) {
          for (Event event : eventsData) {
              Integer currHour = event.getExpectedAt().getHour();
              if (!mapTimeEvents.containsKey(currHour)) {
                  mapTimeEvents.put(currHour, new TreeSet<Event>());
              }
              mapTimeEvents.get(currHour).add(event);
          }
      }


      JPanel insidePanel = new JPanel();

      GridBagLayout gbPnlUrgentAllrms = new GridBagLayout();
      GridBagConstraints gbcPnlUrgentAllrms = new GridBagConstraints();
      insidePanel.setLayout(gbPnlUrgentAllrms);

      int i = 0;
      for (HashMap.Entry<Integer, SortedSet<Event>> eventsEntry : mapTimeEvents.entrySet()) {

         PnlEventsTable eventsTable = new PnlEventsTable(eventsEntry.getValue());
         String title = String.format("%02dh - %02dh", eventsEntry.getKey(), eventsEntry.getKey() + 1);
         eventsTable.setBorder(BorderFactory.createTitledBorder(title));

         gbcPnlUrgentAllrms.gridx = 0;
         gbcPnlUrgentAllrms.gridy = i;
         gbcPnlUrgentAllrms.gridwidth = 1;
         gbcPnlUrgentAllrms.gridheight = 1;
         gbcPnlUrgentAllrms.fill = GridBagConstraints.NONE;
         gbcPnlUrgentAllrms.weightx = 0;
         gbcPnlUrgentAllrms.weighty = 0;
         gbcPnlUrgentAllrms.anchor = GridBagConstraints.NORTH;
         gbcPnlUrgentAllrms.insets = new Insets(10, 5, 10, 5);
         gbPnlUrgentAllrms.setConstraints(eventsTable, gbcPnlUrgentAllrms);
         eventsTable.setVisible(true);
         insidePanel.add(eventsTable);

         i++;
      }

      this.setViewportView(insidePanel);
   }

   public static void main(String[] a) {
      JFrame jf = new JFrame();

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

      PnlUrgentEvents tab = new PnlUrgentEvents(testSetEvents);
      jf.setTitle("Table");
      jf.setSize(500, 500);
      jf.setVisible(true);
      jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      jf.add(tab);
   }
}
