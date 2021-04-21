import Models.Model;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.crypto.Data;
import java.awt.event.*;
import java.sql.Date;
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
    private JMenuItem checkRankPlayersItem;
    private JComboBox leagueNameCombo;
    private JComboBox leagueYearCombo;
    JTextField p1EmailTf;
    JTextField p2EmailTf;
    JTextField p1GamesWonTf;
    JTextField p2GamesWonTf;
    JTextField datePlayedTf;
    JTextField courtNumberTf;
    JTextField venueNameTf;
    JTextField leagueNameTf;
    JTextField leagueYearTf;

    public DatabaseGUI() {
        initInterface();
//        DatabaseConnection.getAllPlayers();
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
        checkRankPlayersItem = new JMenuItem("Check Player Ranking");

        tableData = new DefaultTableModel() {
            public Class<String> getColumnClass(int columnIndex) {
                return String.class;
            }

            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ArrayList<String> leagueNameList = DatabaseConnection.getAllLeagueNames();
        String[] leagueNames = new String[leagueNameList.size()];

        for (int i = 0; i < leagueNames.length; i++) {
            leagueNames[i] = leagueNameList.get(i);
        }

        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);

        JLabel leagueNameLabel = new JLabel("Select League Name：");
        JLabel leagueYearLabel = new JLabel("Select League Year: ");
        JLabel p1EmailLabel = new JLabel("Player 1 Email: ");
        JLabel p2EmailLabel = new JLabel("Player 2 Email: ");
        JLabel p1GamesWonLabel = new JLabel("Player 1 Games Won: ");
        JLabel p2GamesWonLabel = new JLabel("Player 2 Games Won: ");
        JLabel datePlayedLabel = new JLabel("Date Played: ");
        JLabel courtNumberLabel = new JLabel("Court Number: ");
        JLabel venueNameLabel = new JLabel("Venue Name: ");
        JLabel insertLeagueNameLabel = new JLabel("League Name: ");
        JLabel insertLeagueYearLabel = new JLabel("League Year: ");
        p1EmailTf = new JTextField();
        p2EmailTf = new JTextField();
        p1GamesWonTf = new JTextField();
        p2GamesWonTf = new JTextField();
        datePlayedTf = new JTextField();
        courtNumberTf = new JTextField();
        venueNameTf = new JTextField();
        leagueNameTf = new JTextField();
        leagueYearTf = new JTextField();

        JButton searchBtn = new JButton("Search");
        JButton insertBtn = new JButton("Insert Match");
        leagueNameCombo = new JComboBox(leagueNames);
        ArrayList<Integer> leagueYearList = DatabaseConnection.getAllLeagueYearsByName(leagueNameCombo.getSelectedItem().toString());
        String[] leagueYears = new String[leagueYearList.size()];
        for (int i = 0; i < leagueYears.length; i++) {
            leagueYears[i] = leagueYearList.get(i) + "";
        }
        leagueYearCombo = new JComboBox(leagueYears);

        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGap(5);
        hGroup.addGroup(layout.createParallelGroup()
                .addComponent(leagueNameLabel)
                .addComponent(leagueYearLabel)
                .addComponent(p1EmailLabel)
                .addComponent(p2EmailLabel)
                .addComponent(p1GamesWonLabel)
                .addComponent(p2GamesWonLabel)
                .addComponent(datePlayedLabel)
                .addComponent(courtNumberLabel)
                .addComponent(venueNameLabel)
                .addComponent(insertLeagueNameLabel)
                .addComponent(insertLeagueYearLabel)
        );
        hGroup.addGap(5);
        hGroup.addGroup(layout.createParallelGroup()
                .addComponent(leagueYearCombo)
                .addComponent(leagueNameCombo)
                .addComponent(searchBtn)
                .addComponent(p1EmailTf)
                .addComponent(p2EmailTf)
                .addComponent(p1GamesWonTf)
                .addComponent(p2GamesWonTf)
                .addComponent(datePlayedTf)
                .addComponent(courtNumberTf)
                .addComponent(venueNameTf)
                .addComponent(leagueNameTf)
                .addComponent(leagueYearTf)
                .addComponent(insertBtn)
        );
        hGroup.addGap(5);
        layout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGap(10);
        vGroup.addGroup(layout.createParallelGroup());
        vGroup.addGap(10);
        vGroup.addGroup(layout.createParallelGroup()
                .addComponent(leagueNameLabel)
                .addComponent(leagueNameCombo));
        vGroup.addGap(5);
        vGroup.addGroup(layout.createParallelGroup()
                .addComponent(leagueYearLabel)
                .addComponent(leagueYearCombo));
        vGroup.addGap(10);
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                .addComponent(searchBtn));
        vGroup.addGap(10);
        vGroup.addGroup(layout.createParallelGroup()
                .addComponent(p1EmailLabel)
                .addComponent(p1EmailTf));
        vGroup.addGap(5);
        vGroup.addGroup(layout.createParallelGroup()
                .addComponent(p2EmailLabel)
                .addComponent(p2EmailTf));
        vGroup.addGap(5);
        vGroup.addGroup(layout.createParallelGroup()
                .addComponent(p1GamesWonLabel)
                .addComponent(p1GamesWonTf));
        vGroup.addGap(5);
        vGroup.addGroup(layout.createParallelGroup()
                .addComponent(p2GamesWonLabel)
                .addComponent(p2GamesWonTf));
        vGroup.addGap(5);
        vGroup.addGroup(layout.createParallelGroup()
                .addComponent(datePlayedLabel)
                .addComponent(datePlayedTf));
        vGroup.addGap(5);
        vGroup.addGroup(layout.createParallelGroup()
                .addComponent(courtNumberLabel)
                .addComponent(courtNumberTf));
        vGroup.addGap(5);
        vGroup.addGroup(layout.createParallelGroup()
                .addComponent(venueNameLabel)
                .addComponent(venueNameTf));
        vGroup.addGap(5);
        vGroup.addGroup(layout.createParallelGroup()
                .addComponent(insertLeagueNameLabel)
                .addComponent(leagueNameTf));
        vGroup.addGap(5);
        vGroup.addGroup(layout.createParallelGroup()
                .addComponent(insertLeagueYearLabel)
                .addComponent(leagueYearTf));
        vGroup.addGap(5);
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                .addComponent(insertBtn));

        layout.setVerticalGroup(vGroup);
        searchBtn.addActionListener(this);
        insertBtn.addActionListener(this);

        popupMenu.add(checkAllPlayersItem);
        popupMenu.add(checkWonPlayersItem);
        popupMenu.add(checkUnusedCourtsItem);
        popupMenu.add(checkRankPlayersItem);

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
        checkRankPlayersItem.addActionListener(this);

        table.setComponentPopupMenu(popupMenu);
        table.setFillsViewportHeight(true);
        table.add(popupMenu);

        mainFrame.add(panel, BorderLayout.NORTH);
        mainFrame.add(scrollPane, BorderLayout.SOUTH);
//
        mainFrame.setSize(800, 600);
        mainFrame.setVisible(true);
    }

    public void checkAllPlayers() {
        ArrayList<Model> players = DatabaseConnection.getAllPlayers();
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
        LinkedHashMap<String, Integer> map = DatabaseConnection.getAllWonPlayers();
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
        ArrayList<Model> courts = DatabaseConnection.getUnUsedCourt();
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
        ArrayList<Model> matches = DatabaseConnection.getLeagueMatches(leagueName, leagueYear);
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

    private void checkRankingPlayers(){
        LinkedHashMap<String, String> map = DatabaseConnection.rankPlayers();
        if (map.isEmpty()) {
            JOptionPane.showMessageDialog(null, "There is no player won.");
        } else {
            String[] columnNames = new String[]{"Player Name", "Won Prize Money"};
            Object[][] data = new Object[map.size()][columnNames.length];
            int i = 0;

            for (Map.Entry<String, String> entry : map.entrySet()) {
                String email = entry.getKey();
                String money = entry.getValue();
                data[i][0] = email;
                data[i][1] = money;
                i++;
            }
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
        Object src = event.getSource();
        if (src instanceof JMenuItem) {
            //for the popup menu
            JMenuItem menu = (JMenuItem) event.getSource();
            if (menu == checkAllPlayersItem) {
                checkAllPlayers();
            } else if (menu == checkWonPlayersItem) {
                checkWonPlayers();
            } else if (menu == checkUnusedCourtsItem) {
                checkUnusedCourts();
            } else if (menu == checkRankPlayersItem) {
                checkRankingPlayers();
            }
        } else if (src instanceof JButton) {
            if (((JButton) src).getText().compareTo("Search") == 0) {
                String leagueName = leagueNameCombo.getSelectedItem().toString();
                int leagueYear = 0;
                try{
                    leagueYear = Integer.parseInt(leagueYearCombo.getSelectedItem().toString());
                }
                catch (NumberFormatException e){
                    JOptionPane.showMessageDialog(null, "Please enter valid numbers");
                    return;
                }
                checkLeague(leagueName, leagueYear);
            } else {
                String p1Email = p1EmailTf.getText();
                String p2Email = p1EmailTf.getText();
                int p1GamesWon = 0;
                int p2GamesWon = 0;
                int courtNumber = 0;
                int leagueYear = 0;
                Date datePlayed;
                try{
                    p1GamesWon = Integer.parseInt(p1GamesWonTf.getText());
                    p2GamesWon = Integer.parseInt(p2GamesWonTf.getText());
                    courtNumber = Integer.parseInt(courtNumberTf.getText());
                    leagueYear = Integer.parseInt(leagueYearTf.getText());
                    datePlayed = Date.valueOf(datePlayedTf.getText());
                }
                catch(NumberFormatException e){
                    JOptionPane.showMessageDialog(null, "Please enter valid numbers");
                    return;
                }
                catch(IllegalArgumentException e){
                    JOptionPane.showMessageDialog(null, "Please enter valid date yy-mm-dd");
                    return;
                }
                String venueName = venueNameTf.getText();
                String leagueName = leagueNameTf.getText();

//                p1Email = "ranley0109@gmail.com";
//                p2Email = "yuhuan0109@gmail.com";
//                p1GamesWon = 1;
//                p2GamesWon = 3;
//                datePlayed = Date.valueOf("2020-01-09");
//                courtNumber = 1;
//                venueName = "University Sports Centre";
//                leagueName = "Alexander McLintoch trophy";
//                leagueYear = 2020;

                DatabaseConnection.insertMatch(p1Email, p2Email, p1GamesWon, p2GamesWon, datePlayed, courtNumber, venueName, leagueName, leagueYear);
            }
        }
    }

    public static void main(String[] args) {
        DatabaseGUI gui = new DatabaseGUI();
//        printDatabase("League");
//        getAllLeagueYearsByName("Alexander McLintoch trophy");
//        addProcAddVenue();
//        rankPlayers();
//        DatabaseConnection.getUnUsedCourt();
    }

}
