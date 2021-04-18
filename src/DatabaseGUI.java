import Models.Court;
import Models.Player;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class DatabaseGUI implements ActionListener{
    private JFrame mainFrame;
    private JScrollPane scrollPane;
    private JTable table;
    private DefaultTableModel tableData;
    private JPopupMenu popupMenu;
    private JMenuItem checkAllPlayersItem;
    private JMenuItem checkWonPlayersItem;
    private JMenuItem checkUnusedCourtsItem;


    public DatabaseGUI(){
        initInterface();
    }


    private void initInterface(){
        mainFrame = new JFrame("Database");
        table = new JTable();
        scrollPane = new JScrollPane(table);
        popupMenu = new JPopupMenu();
        checkAllPlayersItem = new JMenuItem("Check All Players");
        checkWonPlayersItem = new JMenuItem("Check Won Players");
        checkUnusedCourtsItem = new JMenuItem("Check Unused Courts");
        tableData = new DefaultTableModel() {
            public Class<String> getColumnClass(int columnIndex) {
                return String.class;
            }
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };


        popupMenu.add(checkAllPlayersItem);
        popupMenu.add(checkWonPlayersItem);
        popupMenu.add(checkUnusedCourtsItem);

        mainFrame.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                showPopup(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                showPopup(e);
            }

            private void showPopup(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popupMenu.show(e.getComponent(),
                            e.getX(), e.getY());
                }
            }
        });

        checkAllPlayersItem.addActionListener(this);
        checkWonPlayersItem.addActionListener(this);
        checkUnusedCourtsItem.addActionListener(this);

        table.setComponentPopupMenu(popupMenu);
        table.setFillsViewportHeight(true);
        table.add(popupMenu);

        mainFrame.add(scrollPane, BorderLayout.CENTER);
//
//
        mainFrame.setSize(400, 400);
        mainFrame.setVisible(true);
    }

    public void checkAllPlayers(){
        ArrayList<Player> players = Main.getAllPlayers();
        if(players.isEmpty()){
            JOptionPane.showMessageDialog(null, "There is no player.");
        }
        else{
            String[] columnNames = new String[]{"Full Name", "Email", "Phone Number(s)"};
            Object[][] data = new Object[players.size()][columnNames.length];

            for(int i = 0; i < players.size(); i++){
                Player player = players.get(i);
                String fullName = player.getFullName();
                String email = player.getEmail();
                String phoneNumber = player.getPhoneNumbers();
                data[i][0] = fullName;
                data[i][1] = email;
                data[i][2] = phoneNumber;
            }

            tableData.setDataVector(data, columnNames);
            table.setModel(tableData);
        }
    }

    private void checkWonPlayers(){
        LinkedHashMap<String, Integer> map = Main.getAllWonPlayers();
        if(map.isEmpty()){
            JOptionPane.showMessageDialog(null, "There is no player won.");
        }
        else{
            String[] columnNames = new String[]{"Email", "Won Times"};
            Object[][] data = new Object[map.size()][columnNames.length];
            int i = 0;

            for (Map.Entry<String, Integer> entry : map.entrySet()){
                String email = entry.getKey();
                int times = entry.getValue();
                data[i][0] = email;
                data[i][1] = times;
                i++;
            }
            tableData.setDataVector(data, columnNames);
            table.setModel(tableData);
        }
    }

    private void checkUnusedCourts(){
        ArrayList<Court> courts = Main.getUnUsedCourt();
        if(courts.isEmpty()){
            JOptionPane.showMessageDialog(null, "There is no player won.");
        }
        else{
            String[] columnNames = new String[]{"Court Number", "Venue Name", "Address"};
            Object[][] data = new Object[courts.size()][columnNames.length];

            for(int i = 0; i < courts.size(); i++){
                Court court = courts.get(i);
                int number = court.getNumber();
                String venueName = court.getVenueName();
                String venueAddress = court.getVenueAddress();
                data[i][0] = number;
                data[i][1] = venueName;
                data[i][2] = venueAddress;
            }

            tableData.setDataVector(data, columnNames);
            table.setModel(tableData);
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if(event.getSource() instanceof JMenuItem){
            //for the popup menu
            JMenuItem menu = (JMenuItem) event.getSource();
            if (menu == checkAllPlayersItem) {
                checkAllPlayers();
            }
            else if (menu == checkWonPlayersItem) {
                checkWonPlayers();
            }
            else if (menu == checkUnusedCourtsItem){
                checkUnusedCourts();
            }
        }
    }


}
