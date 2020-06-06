package it.polimi.project14.user.gui.components;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.SortedSet;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import it.polimi.project14.common.Event;

public class PnlEventsTable extends JScrollPane {

    final Object[] header = { "Tipo", "CAP", "Gravità", "Data e Ora", "Stato", "Descrizione"};
    final Object[] headerNoDateTime = { "Tipo", "CAP", "Gravità", "Descrizione","Stato"};

    DefaultTableModel model = new DefaultTableModel(header, 0) {
        public boolean isCellEditable(int row, int column) {
            // This causes all cells to be not editable
            return false;
        }
    };;

    JTable table;

    public PnlEventsTable() {
        this.table = new JTable(model);
        this.table.setPreferredScrollableViewportSize(table.getPreferredSize());
        this.setViewportView(table);
    }

    public PnlEventsTable(SortedSet<Event> sortedEvents) {
        this.table = new JTable(model);
        setEvents(sortedEvents, true);
        this.setViewportView(table);
    }

    public PnlEventsTable(SortedSet<Event> sortedEvents, Boolean showDateTime) {
        this.table = new JTable(model);
        setEvents(sortedEvents, showDateTime);
        this.setViewportView(table);
    }

    public void setEvents(SortedSet<Event> sortedEvents, Boolean showDateTime) {
        // Clear the table
        this.model.setRowCount(0);

        if (showDateTime == false) {
            this.model = new DefaultTableModel(headerNoDateTime, 0);
            table.setModel(this.model);
        }

        if (sortedEvents != null) {
            for (Event event : sortedEvents) {
                ArrayList<String> eventData = new ArrayList<String>();
                eventData.add(event.getKind());
                eventData.add(event.getCap());
                eventData.add(Integer.toString(event.getSeverity()));
                if (showDateTime == true) {
                    eventData.add(event.getExpectedAt()
                    .format(DateTimeFormatter.ofPattern("d-MMMM-YY H:mm")));
                }
                eventData.add(event.getStatus().toString());
                eventData.add(event.getMessage());
                
                // Add Data to the table
                this.model.addRow(eventData.toArray());
            }
        }

        if (!showDateTime) {
            this.model = new DefaultTableModel(headerNoDateTime, 0);
            for (int i = 0; i < this.model.getRowCount(); i++)
            this.model.getDataVector().remove(4);
        }
        this.model.fireTableDataChanged();
        this.table.setPreferredScrollableViewportSize(this.table.getPreferredSize());
    }
}
