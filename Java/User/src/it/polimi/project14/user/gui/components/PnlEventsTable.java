package it.polimi.project14.user.gui.components;

import java.time.LocalDateTime;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import it.polimi.project14.common.Event;

public class PnlEventsTable extends JPanel {

    public PnlEventsTable(SortedSet<Event> sortedEvents){

        Object[] header = {"Tipo", "CAP", "Gravità", "Data e ora", "Descrizione"};

        DefaultTableModel model = new DefaultTableModel(header, 0)
        {
            public boolean isCellEditable(int row, int column)
            {
              return false;//This causes all cells to be not editable
            }
        };

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
                model.addRow(eventData);
            }
        }

        JTable table = new JTable(model);

        table.setPreferredScrollableViewportSize(table.getPreferredSize());

        JScrollPane js=new JScrollPane(table);
        js.setVisible(true);
        this.add(js);
    }

    public static void main(String[] a) {
        JFrame jf = new JFrame();

        SortedSet<Event> testSetEvents = new TreeSet<Event>();
        for (int i = 0; i < 10; i++) {
            Event testEvent = new Event();
            testEvent.setKind("Terremoto");
            testEvent.setCap("22100");
            testEvent.setSeverity(9);
            testEvent.setExpectedAt(LocalDateTime.of(2020, 3, 3, i, 0));
            testEvent.setMessage("BlaBlaBlaBlaBla");

            testSetEvents.add(testEvent);
        }

        PnlEventsTable tab = new PnlEventsTable(testSetEvents);
        jf.setTitle("Table");
        jf.setSize(500, 500);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.add(tab);
    }
}
