import Exceptions.*;
import Models.*;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.Date;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
//        main.initDatabase();
        main.initInterface();
//        printDatabase("view_win_count");
//        System.out.println(getLeagueMatches("Alexander McLintoch trophy", 2018));
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
        initMatches(connection, "src/Matches.csv");
        initLeague(connection, "src/Leagues.csv");
        initLeaguePlayer(connection, "src/LeaguePlayer.csv");
    }

    public static Connection makeConnection() {
        Connection connection = null;

        try {
            String dbUrl = "jdbc:sqlite:test";
//            String dbUrl = "jdbc:mariadb://localhost/hy30_db";
//            String uname = "hy30";
//            String passwd = ".611MKA73dHitM";
            String uname = "ranley";
            String password = "buzhidao";

            System.out.println("Connecting to Database ...");
            connection = DriverManager.getConnection(dbUrl);
            System.out.println("Database Connected!");
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
                System.out.println(rs.getInt(2));
            }

            connection.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }

    public static ArrayList<Model> getAllPlayers(){
        ArrayList<Model> playerLists = new ArrayList<>();
        HashMap<String, Player> map = new HashMap<>();

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

                if(map.containsKey(email)){
                    Player player = map.get(email);
                    player.addPhoneNumber(phoneNumber);
                }
                else{
                    Player player = new Player(email, forename, middlename, surname, null, phoneNumber);
                    map.put(email, player);
                }
            }
            playerResults.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        for (Map.Entry<String, Player> entry : map.entrySet()){
            Player player = entry.getValue();
            playerLists.add(player);
        }
        return playerLists;
    }

    public static ArrayList<Model> getUnUsedCourt(){
        ArrayList<Model> res = new ArrayList<>();
        Connection connection = makeConnection();
        Statement statement = null;
        PreparedStatement preparedStatement = null;


        try{
            statement = connection.createStatement();
            String query = "SELECT DISTINCT Court.number, Court.venue_name\n" +
                    "FROM Court \n" +
                    "LEFT JOIN Played_Match \n" +
                    "ON Court.venue_name = Played_Match.venue_name\n" +
                    "and Court.number = Played_Match.court_number\n" +
                    "WHERE Played_Match.id is NULL\n" +
                    "ORDER BY Court.venue_name ASC, Court.number ASC";
            ResultSet resultSet  = statement.executeQuery(query);
            query = "SELECT address FROM Venue\n" +
                    "WHERE name = ?";
            preparedStatement = connection.prepareStatement(query);

            while(resultSet.next()){
                String venueName = resultSet.getString(2);
                int courtNumber = resultSet.getInt(1);
                String venueAddress = "";

                preparedStatement.setString(1, venueName);
                ResultSet curRes = preparedStatement.executeQuery();

                while(curRes.next()){
                    venueAddress = curRes.getString(1);
                }
                curRes.close();

                Court court = new Court(courtNumber, venueName, venueAddress);
                res.add(court);

                preparedStatement.clearParameters();
            }
            resultSet.close();
            statement.close();
            preparedStatement.close();
            connection.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return res;
    }

    public static LinkedHashMap<String, Integer> getAllWonPlayers(){
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        Connection connection = makeConnection();
        Statement statement = null;

        try{
            statement = connection.createStatement();
            String query =
//                    "CREATE VIEW view_win_count AS \n" +
                    "SELECT Winner.winner_email, COUNT(Winner.id) \n" +
                    "FROM (SELECT id, \n" +
                            "(CASE " +
                            "WHEN p1_games_won > p2_games_won THEN p1_email\n" +
                            "ELSE p2_email\n" +
                            "END) AS winner_email FROM Played_Match)\n" +
                    "Winner\n" +
                    "GROUP BY Winner.winner_email\n" +
                    "ORDER BY Winner.winner_email";

            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                String curWinner = resultSet.getString(1);
                int wonTimes = resultSet.getInt(2);
                map.put(curWinner, wonTimes);
            }
            resultSet.close();
            statement.close();
            connection.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return map;
    }

    public static String getNameByEmail(String email){
        Connection connection = makeConnection();
        PreparedStatement preparedStatement = null;
        String fullname = "";

        try{
//            String query = "SELECT CONCAT_WS(\",\",\"Player.forename\",\"Player.middlenames\",\"Player.surname\") " +
//                    "FROM Player\n" +
//                    "WHERE Player.email = ?";
            String query = "SELECT Player.forename,Player.middlenames, Player.surname\n" +
                    "FROM Player\n" +
                    "WHERE Player.email = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                String forename = resultSet.getString("forename");
                String middlename = resultSet.getString("middlenames");
                String surname = resultSet.getString("surname");
                fullname = forename + " " + middlename + " " + surname;
            }

            preparedStatement.close();
            resultSet.close();
            connection.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return fullname;
    }


    public static ArrayList<Model> getLeagueMatches(String leagueName, int leagueYear){
        ArrayList<Model> matches = new ArrayList<>();
        Connection connection = makeConnection();
        PreparedStatement preparedStatement = null;
        HashMap<String, String> emailToNameMap = new HashMap<>();

        try {
            String query = "SELECT * FROM Played_Match\n" +
                    "WHERE Played_Match.league_name = ? AND " +
                    "Played_Match.league_year = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, leagueName);
            preparedStatement.setInt(2, leagueYear);

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String p1Email = resultSet.getString("p1_email");
                String p2Email = resultSet.getString("p2_email");
                String p1Name = "";
                String p2Name = "";
                if(emailToNameMap.containsKey(p1Email))
                    p1Name = emailToNameMap.get(p1Email);
                else{
                    p1Name = getNameByEmail(p1Email);
                    emailToNameMap.put(p1Email, p1Name);
                }

                if(emailToNameMap.containsKey(p2Email))
                    p2Name = emailToNameMap.get(p2Email);
                else{
                    p2Name = getNameByEmail(p2Email);
                    emailToNameMap.put(p2Email, p2Name);
                }

                int p1GamesWon = resultSet.getInt("p1_games_won");
                int p2GamesWon = resultSet.getInt("p2_games_won");
                Date datePlayed = resultSet.getDate("date_played");
                int courtNumber = resultSet.getInt("court_number");
                String venueName = resultSet.getString("venue_name");

                Match match = new Match(id, p1Name, p2Name, p1GamesWon, p2GamesWon, datePlayed, courtNumber, venueName);
                matches.add(match);
            }
            preparedStatement.close();
            resultSet.close();
            connection.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return matches;
    }

    public static ArrayList<String> getAllLeagueNames(){
        ArrayList<String> leagueList = new ArrayList<>();
        Connection connection = makeConnection();
        Statement statement = null;

        try{
            statement = connection.createStatement();
            String query ="SELECT DISTINCT name FROM League\n";
            ResultSet resultSet  = statement.executeQuery(query);

            while(resultSet.next()){
                // Retrieve by column label
                String leagueName = resultSet.getString("name");
                leagueList.add(leagueName);
            }
            resultSet.close();
            statement.close();
            connection.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return leagueList;
    }

    public static ArrayList<Integer> getAllLeagueYearsByName(String name){
        ArrayList<Integer> leagueList = new ArrayList<>();
        Connection connection = makeConnection();
        PreparedStatement preparedStatement = null;

        try{
            String query ="SELECT DISTINCT year FROM League WHERE name = ?\n";
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet  = preparedStatement.executeQuery(query);

            while(resultSet.next()){
                // Retrieve by column label
                int year = Integer.parseInt(resultSet.getString("year"));
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return leagueList;
    }

    public static void insertMatch(String p1Email, String p2Email, int p1GamesWon, int p2GamesWon,
                                   Date datePlayed, int courtNumber, String venueName, String leagueName,
                                   int leagueYear){
        Connection connection = makeConnection();
        PreparedStatement preparedStatement = null;
        try{
            String query ="INSERT INTO Played_Match VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)\n";
            preparedStatement = connection.prepareStatement(query);

            // TODO generate id
            int id = 1000;
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, p1Email);
            preparedStatement.setString(3, p2Email);
            preparedStatement.setInt(4, p1GamesWon);
            preparedStatement.setInt(5, p2GamesWon);
            preparedStatement.setDate(6, datePlayed);
            preparedStatement.setInt(7, courtNumber);
            preparedStatement.setString(8, venueName);
            preparedStatement.setString(9, leagueName);
            preparedStatement.setInt(10, leagueYear);
            preparedStatement.executeUpdate();

            connection.close();
            preparedStatement.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
