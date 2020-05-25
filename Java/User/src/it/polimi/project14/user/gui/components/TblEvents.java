package it.polimi.project14.user.gui.components;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TblEvents extends JPanel{

    public TblEvents(String[][] data){

        String [] header =
        {"Tipo", "CAP", "Gravità", "Data e ora", "Descrizione"};

        DefaultTableModel model = new DefaultTableModel(data,header);

        JTable table = new JTable(model);

        // table.setPreferredScrollableViewportSize(new Dimension(450,63));
        // table.setFillsViewportHeight(true);

        JScrollPane js=new JScrollPane(table);
        js.setVisible(true);
        this.add(js);

    }
    public static void main(String [] a) {
        JFrame jf=new JFrame();
        String [][] data = 
        {
            {"Terremoto","22100", "9", "16/10/20 21:30", "blablablalbalabla"},
            {"Terremoto","22100", "9", "16/10/20 21:30", "blablablalbalabla"},
            {"Terremoto","22100", "9", "16/10/20 21:30", "blablablalbalabla"},
            {"Terremoto","22100", "9", "16/10/20 21:30", "blablablalbalabla"},
            {"Terremoto","22100", "9", "16/10/20 21:30", "blablablalbalabla"},
            {"Terremoto","22100", "9", "16/10/20 21:30", "blablablalbalabla"},
            {"Terremoto","22100", "9", "16/10/20 21:30", "blablablalbalabla"},
            {"Terremoto","22100", "9", "16/10/20 21:30", "blablablalbalabla"},
            {"Terremoto","22100", "9", "16/10/20 21:30", "blablablalbalabla"}
        };
        TblEvents tab= new TblEvents(data);
        jf.setTitle("Table");
        jf.setSize(500, 500);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.add(tab);
    }
}