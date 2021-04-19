import Models.Court;
import Models.League;
import Models.Model;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class DatabaseGUI implements ActionListener {
    private JFrame mainFrame;
    private JPanel panel;
    private JScrollPane scrollPane;
    private JTable table;
    private DefaultTableModel tableData;
    private JPopupMenu popupMenu;
    private JMenuItem checkAllPlayersItem;
    private JMenuItem checkWonPlayersItem;
    private JMenuItem checkUnusedCourtsItem;
    private JMenuItem checkLeagueItem;
    private JMenuItem addNewMatchItem;
    private JComboBox leagueNameCombo;
    private JComboBox leagueYearCombo;


    public DatabaseGUI() {
        initInterface();
    }

    private void initInterface() {
        mainFrame = new JFrame("Database");
        table = new JTable();
        scrollPane = new JScrollPane(table);
        panel = new JPanel(new BorderLayout());
        popupMenu = new JPopupMenu();
        checkAllPlayersItem = new JMenuItem("Check All Players");
        checkWonPlayersItem = new JMenuItem("Check Won Players");
        checkUnusedCourtsItem = new JMenuItem("Check Unused Courts");
        checkLeagueItem = new JMenuItem("Check League's Matches");
        addNewMatchItem = new JMenuItem("Add a New Match");

        tableData = new DefaultTableModel() {
            public Class<String> getColumnClass(int columnIndex) {
                return String.class;
            }

            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ArrayList<String> leagueNameList = Main.getAllLeagueNames();
        String[] leagueNames = new String[leagueNameList.size()];

        for (int i = 0; i < leagueNames.length; i++) {
            leagueNames[i] = leagueNameList.get(i);
        }

        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);

        JLabel leagueNameLabel = new JLabel("Select League Name：");
        JLabel leagueYearLabel = new JLabel("Select League Year: ");
        JButton searchBtn = new JButton("Search");
        JComboBox leagueNameCombo = new JComboBox(leagueNames);
        ArrayList<Integer> leagueYearList = Main.getAllLeagueYearsByName(leagueNameCombo.getSelectedItem().toString());
        String[] leagueYears = new String[leagueYearList.size()];
        for(int i = 0; i < leagueYears.length; i++){
            leagueYears[i] = leagueYearList.get(i) + "";
        }
        JComboBox leagueYearCombo = new JComboBox(leagueYears);

        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGap(5);
        hGroup.addGroup(layout.createParallelGroup().addComponent(leagueNameLabel)
                .addComponent(leagueYearLabel));
        hGroup.addGap(5);
        hGroup.addGroup(layout.createParallelGroup()
                .addComponent(leagueYearCombo)
                .addComponent(leagueNameCombo)
                .addComponent(searchBtn));
        hGroup.addGap(5);
        layout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGap(10);
        vGroup.addGroup(layout.createParallelGroup());
        vGroup.addGap(10);
        vGroup.addGroup(layout.createParallelGroup().addComponent(leagueNameLabel)
                .addComponent(leagueNameCombo));
        vGroup.addGap(5);
        vGroup.addGroup(layout.createParallelGroup().addComponent(leagueYearLabel)
                .addComponent(leagueYearCombo));
        vGroup.addGap(10);
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                .addComponent(searchBtn));
        vGroup.addGap(10);

        layout.setVerticalGroup(vGroup);

        popupMenu.add(checkAllPlayersItem);
        popupMenu.add(checkWonPlayersItem);
        popupMenu.add(checkUnusedCourtsItem);
        popupMenu.add(checkLeagueItem);
        popupMenu.add(addNewMatchItem);

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
        checkLeagueItem.addActionListener(this);
        addNewMatchItem.addActionListener(this);

        table.setComponentPopupMenu(popupMenu);
        table.setFillsViewportHeight(true);
        table.add(popupMenu);

        mainFrame.add(panel, BorderLayout.NORTH);
        mainFrame.add(scrollPane, BorderLayout.SOUTH);
//
        mainFrame.setSize(600, 800);
        mainFrame.setVisible(true);
    }

    public void checkAllPlayers() {
        ArrayList<Model> players = Main.getAllPlayers();
        if (players.isEmpty()) {
            JOptionPane.showMessageDialog(null, "There is no player.");
        } else {
            String[] columnNames = new String[]{"Full Name", "Email", "Phone Number(s)"};
            Object[][] data = convertToTableData(players, columnNames);

            tableData.setDataVector(data, columnNames);
            table.setModel(tableData);
        }
    }

    private void checkWonPlayers() {
        LinkedHashMap<String, Integer> map = Main.getAllWonPlayers();
        if (map.isEmpty()) {
            JOptionPane.showMessageDialog(null, "There is no player won.");
        } else {
            String[] columnNames = new String[]{"Email", "Won Times"};
            Object[][] data = new Object[map.size()][columnNames.length];
            int i = 0;

            for (Map.Entry<String, Integer> entry : map.entrySet()) {
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

    private void checkUnusedCourts() {
        ArrayList<Model> courts = Main.getUnUsedCourt();
        if (courts.isEmpty()) {
            JOptionPane.showMessageDialog(null, "There is no player won.");
        } else {
            String[] columnNames = new String[]{"Court Number", "Venue Name", "Address"};
            Object[][] data = convertToTableData(courts, columnNames);
            tableData.setDataVector(data, columnNames);
            table.setModel(tableData);
        }
    }

    private void checkLeague(String leagueName, int leagueYear) {
        ArrayList<Model> matches = Main.getLeagueMatches(leagueName, leagueYear);
        if (matches.isEmpty())
            JOptionPane.showMessageDialog(null, "There is no League");
        else {
            String[] columnNames = new String[]{"ID", "P1 Name", "P2 Name", "P1 Won Times",
                    "P2 Won Times", "Date Played", "Court Number", "Venue Name"};
            Object[][] data = convertToTableData(matches, columnNames);
            tableData.setDataVector(data, columnNames);
            table.setModel(tableData);
        }
    }

    private Object[][] convertToTableData(ArrayList<Model> dataList, String[] columnNames) {
        Object[][] data = new Object[dataList.size()][columnNames.length];
        for (int i = 0; i < dataList.size(); i++) {
            Model curModel = dataList.get(i);
            String[] fields = curModel.toStringArray();

            for (int j = 0; j < columnNames.length; j++) {
                data[i][j] = fields[j];
            }
        }
        return data;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() instanceof JMenuItem) {
            //for the popup menu
            JMenuItem menu = (JMenuItem) event.getSource();
            if (menu == checkAllPlayersItem) {
                checkAllPlayers();
            } else if (menu == checkWonPlayersItem) {
                checkWonPlayers();
            } else if (menu == checkUnusedCourtsItem) {
                checkUnusedCourts();
            } else if (menu == checkLeagueItem) {
//                // TODO: add two text fields to input league info
//                JFrame leagueFrame = new JFrame("Check a League");
//                ArrayList<String> leagueNameList = Main.getAllLeagueNames();
//                String[] leagueNames = new String[leagueNameList.size()];
//
//                for(int i = 0; i < leagueNames.length; i++){
//                    leagueNames[i] = leagueNameList.get(i);
//                }
//                JComboBox leagueNameCombo = new JComboBox(leagueNames);
//                JComboBox leagueYearCombo = new JComboBox();
////                ArrayList<Integer> leagueYears = Main.getAllLeagueYearsByName()
//
//                leagueNameCombo.set
//
//                leagueFrame.add(leagueNameCombo);
//                leagueFrame.add(leagueYearCombo);
//                leagueFrame.setSize(400, 400);
//                leagueFrame.setVisible(true);
////                checkLeague("Alexander McLintoch trophy", 2018);
            } else if (menu == addNewMatchItem) {


            }
        }
    }


}
