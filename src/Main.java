import Exceptions.PhoneTypeOutOfDomainException;
import Model.*;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;


public class Main {
    Connection dbConnection = null;

    public static void main(String[] args) {
        System.out.println("Database Loading...");
        Main main = new Main();
        main.initDatabase();
//        main.initInterface();
        printDatabase("League_Player");
//        getAllPlayers();
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

    private void initDatabase() {
        Connection connection = makeConnection();
//        initPlayer(connection, "src/People.csv");
//        initVenues(connection, "src/Venues.csv");
//        initCourts(connection, "src/Courts.csv");
//        initMatches(connection, "src/Matches.csv");
//        initLeague(connection, "src/Leagues.csv");
        initLeaguePlayer(connection, "src/LeaguePlayer.csv");
//        try {
//            Statement statement = null;
//            statement = dbConnection.createStatement();
//            statement.executeUpdate("DROP TABLE IF EXISTS club");
//            statement.executeUpdate("CREATE TABLE club (id int, question VARCHAR(100))");
//            statement.close();
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }

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

    public static void initPlayer(Connection connection, String filename){
        try{
            FileReader input = new FileReader(filename);
            BufferedReader br = new BufferedReader(input);
            String line = br.readLine(); // read the column names
            Statement statement = connection.createStatement();
            PreparedStatement preparedStatement = null;

            // init Player table
            statement.executeUpdate("DROP TABLE IF EXISTS Player");
            String sql = "CREATE TABLE Player"
                    + "(forename VARCHAR(50), "
                    + "middlenames VARCHAR(50), "
                    + "surname VARCHAR(50), "
                    + "date_of_birth DATE, "
                    + "email VARCHAR(50) not NULL, "
                    + " PRIMARY KEY (email))";
            statement.executeUpdate(sql);
            System.out.println("Table Player is created ");

            // init Player_Phone table
            statement.executeUpdate("DROP TABLE IF EXISTS Player_Phone");
            sql = "CREATE TABLE Player_Phone"
                    + "(email VARCHAR(50) not NULL, "
                    + "phone_number VARCHAR(20), "
                    + "phone_type VARCHAR(10), "
                    + "PRIMARY KEY (email, phone_number))";
            statement.executeUpdate(sql);
            System.out.println("Table Player_Phone is created ");

            sql = "INSERT INTO Player VALUES (?, ?, ?, ?, ?)";

            while((line = br.readLine()) != null) {
                String[] attributes = line.split(",");
                String forename = attributes[0];
                String middlename = attributes[1];
                String surname = attributes[2];

                // prepare birthOfDate
                String[] birthdayStr = attributes[3].split("/");
                String birthFormat = "";
                for(int i = birthdayStr.length - 1; i >= 0; i--){
                    birthFormat += birthdayStr[i];
                    if(i > 0)
                        birthFormat += "-";
                }
                Date dateOfBirth = Date.valueOf(birthFormat);

                String email = attributes[4];

                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, forename);
                preparedStatement.setString(2, middlename);
                preparedStatement.setString(3, surname);
                preparedStatement.setDate(4, dateOfBirth);
                preparedStatement.setString(5, email);
                preparedStatement.executeUpdate();
                preparedStatement.close();

                String newsql = "INSERT INTO Player_Phone VALUES (?, ?, ?)";
                for(int i = 5; i < attributes.length; i++){
                    String curPhone = attributes[i];
                    curPhone = curPhone.replaceAll("\\p{Punct}","");
                    curPhone = curPhone.trim();
                    int index = curPhone.lastIndexOf(' ');
                    String phoneNumber = curPhone.substring(0, index).trim();
                    String phoneType = curPhone.substring(index, curPhone.length());
                    phoneType = phoneType.trim();

                    // need to add more to handle issues

                    if(phoneType.compareTo("home") != 0 && phoneType.compareTo("mobile") != 0 && phoneType.compareTo("work") != 0)
                        throw new PhoneTypeOutOfDomainException(phoneType);

                    preparedStatement = connection.prepareStatement(newsql);
                    preparedStatement.setString(1, email);
                    preparedStatement.setString(2, phoneNumber);
                    preparedStatement.setString(3, phoneType);
                    preparedStatement.executeUpdate();
                    preparedStatement.close();
                };
            }

            statement.close();
        }
        catch (IOException | SQLException | PhoneTypeOutOfDomainException e){
            e.printStackTrace();
        }
    }

    public static void initVenues(Connection connection, String filename){
        try{
            FileReader input = new FileReader(filename);
            BufferedReader br = new BufferedReader(input);
            String line = br.readLine(); // read the column names
            Statement statement = connection.createStatement();
            PreparedStatement preparedStatement = null;

            // init Player table
            statement.executeUpdate("DROP TABLE IF EXISTS Venue");
            String sql = "CREATE TABLE Venue"
                    + "(name VARCHAR(50) not NULL, "
                    + "address VARCHAR(50), "
                    + " PRIMARY KEY (name))";
            statement.executeUpdate(sql);
            System.out.println("Table Venue is created ");

            sql = "INSERT INTO Venue VALUES (?, ?)";

            while((line = br.readLine()) != null) {
                int index = line.indexOf(",");
                String name = line.substring(0, index).trim();
                String address = line.substring(index, line.length()).trim();
                address = address.replaceAll("\\p{Punct}","");
                preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setString(1, name);
                preparedStatement.setString(2, address);
                preparedStatement.executeUpdate();
                preparedStatement.close();

            }
            statement.close();
            connection.close();

        }
        catch (IOException | SQLException e){
            e.printStackTrace();
        }
    }

    public static void initCourts(Connection connection, String filename){
        try{
            FileReader input = new FileReader(filename);
            BufferedReader br = new BufferedReader(input);
            String line = br.readLine(); // read the column names
            Statement statement = connection.createStatement();
            PreparedStatement preparedStatement = null;

            // init Player table
            statement.executeUpdate("DROP TABLE IF EXISTS Court");
            String sql = "CREATE TABLE Court"
                    + "(number INTEGER not NULL, "
                    + "venue_name VARCHAR(50) not NULL, "
                    + "PRIMARY KEY (number, venue_name))";
            statement.executeUpdate(sql);
            System.out.println("Table Court is created ");

            sql = "INSERT INTO Court VALUES (?, ?)";

            while((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String venue_name = parts[0];
                int number = Integer.parseInt(parts[1]);
                preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setInt(1, number);
                preparedStatement.setString(2, venue_name);
                preparedStatement.executeUpdate();
                preparedStatement.close();

            }
            statement.close();
            connection.close();

        }
        catch (IOException | SQLException e){
            e.printStackTrace();
        }
    }

    public static void initMatches(Connection connection, String filename){
        try{
            FileReader input = new FileReader(filename);
            BufferedReader br = new BufferedReader(input);
            String line = br.readLine(); // read the column names
            Statement statement = connection.createStatement();
            PreparedStatement preparedStatement = null;

            // init Player table
            statement.executeUpdate("DROP TABLE IF EXISTS Played_Match");
            String sql = "CREATE TABLE Played_Match" +
                    "(" +
                    "p1_email VARCHAR(50), " +
                    "p2_email VARCHAR(50), " +
                    "p1_games_won INTEGER, " +
                    "p2_games_won INTEGER," +
                    "date_played DATE, " +
                    "court_number INTEGER, " +
                    "venue_name VARCHAR(50), " +
                    "league_name VARCHAR(50), " +
                    "league_year INTEGER, " +
                    "id INTEGER not NULL, " +
                    "PRIMARY KEY (id))";
            statement.executeUpdate(sql);
            System.out.println("Table Played_Match is created.");

            sql = "INSERT INTO Played_Match VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            int id = 1;
            while((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String p1Email = parts[0];
                int p1GamesWon = Integer.parseInt(parts[1]);
                int p2GamesWon = Integer.parseInt(parts[2]);
                String p2Email = parts[3];
                String venueName = parts[4];
                int courtNumber = Integer.parseInt(parts[5]);

                String[] dateStr = parts[6].split("/");
                String dateFormat = "";
                for(int i = dateStr.length - 1; i >= 0; i--){
                    dateFormat += dateStr[i];
                    if(i > 0)
                        dateFormat += "-";
                }
                Date datePlayed = Date.valueOf(dateFormat);
                int leagueYear = Integer.parseInt(parts[7]);
                String leagueName = parts[8];

                preparedStatement = connection.prepareStatement(sql);

                preparedStatement.setString(1, p1Email);
                preparedStatement.setString(2, p2Email);
                preparedStatement.setInt(3, p1GamesWon);
                preparedStatement.setInt(4, p2GamesWon);
                preparedStatement.setDate(5, datePlayed);
                preparedStatement.setInt(6, courtNumber);
                preparedStatement.setString(7, venueName);
                preparedStatement.setString(8, leagueName);
                preparedStatement.setInt(9, leagueYear);
                preparedStatement.setInt(10, id);
                id++;
                preparedStatement.executeUpdate();
                preparedStatement.close();

            }
            statement.close();
            connection.close();

        }
        catch (IOException | SQLException e){
            e.printStackTrace();
        }
    }

    public static void initLeague(Connection connection, String filename){
        try{
            FileReader input = new FileReader(filename);
            BufferedReader br = new BufferedReader(input);
            String line = br.readLine(); // read the column names
            Statement statement = connection.createStatement();
            PreparedStatement preparedStatement = null;

            // init Player table
            statement.executeUpdate("DROP TABLE IF EXISTS League");
            String sql = "CREATE TABLE League" +
                    "(" +
                    "name VARCHAR(50) not NULL, " +
                    "year INTEGER not NULL, " +
                    "prize_money FLOAT, " +
                    "winner_email VARCHAR(50), " +
                    "PRIMARY KEY (name, year))";
            statement.executeUpdate(sql);
            System.out.println("Table League is created ");

            sql = "INSERT INTO League VALUES (?, ?, ?, ?)";

            while((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0];
                int year = Integer.parseInt(parts[1]);
                float prizeMoney = Float.parseFloat(parts[2]);
                String winnerEmail = parts[3];

                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, name);
                preparedStatement.setInt(2, year);
                preparedStatement.setFloat(3, prizeMoney);
                preparedStatement.setString(4, winnerEmail);
                preparedStatement.executeUpdate();
                preparedStatement.close();
            }
            statement.close();
            connection.close();

        }
        catch (IOException | SQLException e){
            e.printStackTrace();
        }
    }

    public static void initLeaguePlayer(Connection connection, String filename){
        try{
            FileReader input = new FileReader(filename);
            BufferedReader br = new BufferedReader(input);
            String line = br.readLine(); // read the column names
            Statement statement = connection.createStatement();
            PreparedStatement preparedStatement = null;

            // init Player table
            statement.executeUpdate("DROP TABLE IF EXISTS League_Player");
            String sql = "CREATE TABLE League_Player" +
                    "(" +
                    "email VARCHAR(50) not NULL, " +
                    "league_name VARCHAR(50) not NULL, " +
                    "league_year INTEGER not NULL, " +
                    "PRIMARY KEY (email, league_name, league_year))";
            statement.executeUpdate(sql);
            System.out.println("Table League_Player is created ");

            sql = "INSERT INTO League_Player VALUES (?, ?, ?)";

            while((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String email = parts[0];
                String leagueName = parts[1];
                int leagueYear = Integer.parseInt(parts[2]);

                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, leagueName);
                preparedStatement.setInt(3, leagueYear);
                preparedStatement.executeUpdate();
                preparedStatement.close();
            }
            statement.close();
            connection.close();

        }
        catch (IOException | SQLException e){
            e.printStackTrace();
        }
    }

    public static void printDatabase(String tableName){
        Connection connection = makeConnection();
        try{
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM " + tableName;
            ResultSet rs = statement.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();

            while(rs.next()){
                System.out.println(rs.getString(1));
            }

            connection.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }


    public static ArrayList<Model> getAllPlayers(){
        ArrayList<Model> playerLists = new ArrayList<>();
        Connection connection = makeConnection();
        Statement statement = null;

        try{
            statement = connection.createStatement();
            String query ="SELECT\n" +
                    "Player.email,\n" +
                    "Player.forename,\n" +
                    "Player.middlenames,\n" +
                    "Player.surname,\n" +
                    "Player_Phone.phone_number\n" +
                    "FROM\n" +
                    "Player, Player_Phone\n" +
                    "where Player.email = Player_Phone.email;";

            ResultSet playerResults  = statement.executeQuery(query);

            while(playerResults.next()){
                // Retrieve by column label
                String email = playerResults.getString("email");
                String forename = playerResults.getString("forename");
                String middlename = playerResults.getString("middlenames");
                String surname = playerResults.getString("surname");
                String phoneNumber = playerResults.getString("phone_number");

                Player player = new Player(email, forename, middlename, surname, null, phoneNumber);

                playerLists.add(player);

            }
            playerResults.close();

        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return playerLists;
    }
}
