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

   public PnlUrgentEvents() {
      super();
   }

   public PnlUrgentEvents(SortedSet<Event> sortedUrgentEvents) {
      super();

      setUrgentEvents(sortedUrgentEvents);
   }

   public void setUrgentEvents(SortedSet<Event> sortedUrgentEvents) {
      SortedMap<Integer, SortedSet<Event>> mapTimeEvents = new TreeMap<>();
      if (sortedUrgentEvents != null) {
          for (Event event : sortedUrgentEvents) {
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
         gbcPnlUrgentAllrms.fill = GridBagConstraints.BOTH;
         gbcPnlUrgentAllrms.weightx = 1;
         gbcPnlUrgentAllrms.weighty = 0;
         gbcPnlUrgentAllrms.anchor = GridBagConstraints.NORTH;
         gbcPnlUrgentAllrms.insets = new Insets(10, 10, 10, 10);
         gbPnlUrgentAllrms.setConstraints(eventsTable, gbcPnlUrgentAllrms);
         eventsTable.setVisible(true);
         insidePanel.add(eventsTable);

         i++;
      }

      this.setViewportView(insidePanel);
   }
}
