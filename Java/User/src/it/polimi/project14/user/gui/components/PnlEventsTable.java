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
    final Object[] headerNoDateTime = { "Tipo", "CAP", "Gravità", "Stato", "Descrizione"};

    DefaultTableModel model = new DefaultTableModel(header, 0) {
        public boolean isCellEditable(int row, int column) {
            // This causes all cells to be not editable
            return false;
        }
    };;

    JTable table = new JTable(model);

    public PnlEventsTable() {
        this.table.setPreferredScrollableViewportSize(table.getPreferredSize());
        this.setViewportView(table);
    }

    public PnlEventsTable(SortedSet<Event> sortedEvents) {
        this(sortedEvents, true);
    }

    public PnlEventsTable(SortedSet<Event> sortedEvents, Boolean showDateTime) {
        setEvents(sortedEvents, showDateTime);
        this.setViewportView(table);
    }

    public void setEvents(SortedSet<Event> sortedEvents, Boolean showDateTime) {
        // Clear the table
        this.model.setRowCount(0);

        if (!showDateTime) {
            this.model = new DefaultTableModel(headerNoDateTime, 0) {
                public boolean isCellEditable(int row, int column) {
                    // This causes all cells to be not editable
                    return false;
                }
            };
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

        this.model.fireTableDataChanged();
        this.table.setPreferredScrollableViewportSize(this.table.getPreferredSize());
    }
}
