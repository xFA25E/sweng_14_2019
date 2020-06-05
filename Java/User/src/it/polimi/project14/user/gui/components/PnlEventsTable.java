package it.polimi.project14.user.gui.components;

import java.time.LocalDateTime;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import it.polimi.project14.common.Event;

public class PnlEventsTable extends JScrollPane {

    final Object[] header = { "Tipo", "CAP", "Gravità", "Data e ora", "Descrizione"};

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
        setEvents(sortedEvents);
        this.setViewportView(table);
    }

    public void setEvents(SortedSet<Event> sortedEvents) {
        // Clear the table
        model.setRowCount(0);

        if (sortedEvents != null) {
            for (Event event : sortedEvents) {
                String[] eventData = {
                                     event.getKind(),
                                     event.getCap(),
                                     Integer.toString(event.getSeverity()),
                                     // TODO: Format String
                                     event.getExpectedAt().toString(),
                                     event.getMessage() 
                                     };
                // Add Data to the table
                model.addRow(eventData);
            }
        }
        model.fireTableDataChanged();
        this.table.setPreferredScrollableViewportSize(this.table.getPreferredSize());
    }
}
