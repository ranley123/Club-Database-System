import Model.*;

import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Main {
    Connection dbConnection = null;

    public static void main(String[] args) {
        System.out.println("Database Loading...");
        Main main = new Main();
//        main.initDatabase();
        main.initInterface();
        ODReader reader = new ODReader("src/squash_club_data.ods");
    }

    private void initInterface() {
        JFrame mainFrame = new JFrame("Button Example");
        JLabel usernameLabel = new JLabel("username");
        JLabel passwordLabel = new JLabel("password");
        JTextField usernameText = new JTextField();
        JTextField passwordText = new JTextField();
        JButton loginBtn = new JButton("Login");

        usernameLabel.setBounds(50, 50, 150, 20);
        passwordLabel.setBounds(50, 90, 150, 20);
        usernameText.setBounds(210, 50, 150, 20);
        passwordText.setBounds(210, 90, 150, 20);
        loginBtn.setBounds(150, 150, 95, 30);


        loginBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new DatabaseGUI();
                mainFrame.setVisible(false);
            }
        });

        mainFrame.add(loginBtn);
        mainFrame.add(usernameLabel);
        mainFrame.add(passwordLabel);
        mainFrame.add(usernameText);
        mainFrame.add(passwordText);
        mainFrame.add(usernameText);

        mainFrame.setSize(400, 400);
        mainFrame.setLayout(null);
        mainFrame.setVisible(true);
    }

    public static Connection makeConnection() {
        Connection connection = null;

        try {
            String dbUrl = "jdbc:sqlite:test";
            String uname = "hy30";
            String passwd = ".611MKA73dHitM";

            System.out.println("Connecting to Database ...");
            connection = DriverManager.getConnection(dbUrl);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private void initDatabase() {
        makeConnection();

        try {
            Statement statement = null;
            statement = dbConnection.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS club");
            statement.executeUpdate("CREATE TABLE club (id int, question VARCHAR(100))");
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<Model> getAllPlayers(){
        ArrayList<Model> playerLists = new ArrayList<>();
        Connection connection = makeConnection();
        Statement statement = null;

        try{
            statement = connection.createStatement();
            String query ="select\n" +
                    "player.email,\n" +
                    "player.forename,\n" +
                    "player.middlename,\n" +
                    "player.surname,\n" +
                    "player_phone.phoneNumber\n" +
                    "from\n" +
                    "player, player_phone\n" +
                    "where player.email = player_phone.email;";

            ResultSet playerResults  = statement.executeQuery(query);
            while(playerResults.next()){
                // Retrieve by column label
                String email = playerResults.getString("email");
                String forename = playerResults.getString("forename");
                String middlename = playerResults.getNString("middlename");
                String surname = playerResults.getString("surname");
                String phoneNumber = playerResults.getString("phoneNumber");

                Player player = new Player(email, forename, middlename, surname, null, phoneNumber);
                if(playerLists.contains(player)){
//                    Player oldPlayer = playerLists.get()
                }
                else{
                    playerLists.add(player);
                }


            }
            playerResults.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }



        return playerLists;
    }
}
