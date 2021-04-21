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

public class DatabaseConnection {

    /**
     * This function mainly initialise the database based on existing files
     */
    public static void initDatabase() {
        Connection connection = makeConnection();
//        initPlayer("src/People.csv");
//        initVenues("src/Venues.csv");
//        initCourts("src/Courts.csv");
//        initLeague("src/Leagues.csv");
//        initLeaguePlayer("src/LeaguePlayer.csv");
        initMatches("src/Matches.csv");
//        addProcAddVenue("New Club", "North Street", 3);
    }

    /**
     * This function mainly makes a connection and returns it
     * @return  Connection  - a usable connection to manipulate the database
     */
    public static Connection makeConnection() {
        Connection connection = null;

        try {
//            String dbUrl = "jdbc:sqlite:test";
            String dbUrl = "jdbc:mariadb://localhost/hy30_db";
//            String uname = "hy30";mysqld_safe --skip-grant-tables &
//            String passwd = ".611MKA73dHitM";

            System.out.println("Connecting to Database ...");
            connection = DriverManager.getConnection(dbUrl, "root", "buzhidao");
            System.out.println("Database Connected!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * This function mainly initialise Player and Player_Phone tables
     * @param filename  - the file to be read
     */
    public static void initPlayer(String filename){
        try{
            Connection connection = makeConnection();

            FileReader input = new FileReader(filename);
            BufferedReader br = new BufferedReader(input);
            String line = br.readLine(); // read the column names
            Statement statement = connection.createStatement();
            PreparedStatement preparedStatement = null;

            // init Player table
            statement.executeUpdate("DROP TABLE IF EXISTS Player");
            String sql = "CREATE TABLE Player\n"
                    + "(forename VARCHAR(50), \n"
                    + "middlenames VARCHAR(50), \n"
                    + "surname VARCHAR(50), \n"
                    + "date_of_birth DATE, \n"
                    + "email VARCHAR(50) not NULL, \n"
                    + "PRIMARY KEY (email))";
            statement.executeUpdate(sql);
            System.out.println("Table Player is created!");

            // init Player_Phone table
            statement.executeUpdate("DROP TABLE IF EXISTS Player_Phone");
            sql = "CREATE TABLE Player_Phone" +
                    "(email VARCHAR(50) not NULL, " +
                    "CONSTRAINT valid_email " +
                    "CHECK (email not like \"@%\" AND email not like \"%@\" " +
                        " AND email like \"%@%\"), " +
                    "phone_number VARCHAR(20), " +
                    "phone_type VARCHAR(10), " +
                    "CONSTRAINT phone_types \n" +
                    "CHECK (phone_type in (\"work\", \"home\", \"mobile\")), " +
                    "PRIMARY KEY (email, phone_number), " +
                    "FOREIGN KEY (email) REFERENCES Player(email)" +
                    ")";
            statement.executeUpdate(sql);
            System.out.println("Table Player_Phone is created!");

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
        catch (IOException | SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * This function mainly initialise Venue
     * @param filename  - the file to be read
     */
    public static void initVenues(String filename){
        try{
            Connection connection = makeConnection();
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
                    + "PRIMARY KEY (name))";
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

    /**
     * This function mainly initialise Court
     * @param filename  - the file to be read
     */
    public static void initCourts(String filename){
        try{
            Connection connection = makeConnection();
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
                    + "PRIMARY KEY (number, venue_name), " +
                    "FOREIGN KEY (venue_name) REFERENCES Venue(name)" +
                    ")";
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

    /**
     * This function mainly initialise Match
     * @param filename  - the file to be read
     */
    public static void initMatches(String filename){
        try{
            Connection connection = makeConnection();

            FileReader input = new FileReader(filename);
            BufferedReader br = new BufferedReader(input);
            String line = br.readLine(); // read the column names
            Statement statement = connection.createStatement();
            PreparedStatement preparedStatement = null;

            // init Player table
            statement.executeUpdate("DROP TABLE IF EXISTS Played_Match");
            String sql = "CREATE TABLE Played_Match" +
                    "(" +
                    "p1_email VARCHAR(50) NOT NULL, " +
                    "p2_email VARCHAR(50) NOT NULL, " +
                    "p1_games_won INTEGER, " +
                    "p2_games_won INTEGER, " +
                    "CONSTRAINT valid_games " +
                        "CHECK ((p1_games_won = 3 AND p2_games_won >= 0 AND p2_games_won < 3) " +
                        "OR (p2_games_won = 3 AND p1_games_won >= 0 AND p1_games_won < 3)), " +
                    "date_played DATE, " +
                    "CONSTRAINT valid_year " +
                        "CHECK (year(date_played) = league_year), " +
                    "court_number INTEGER NOT NULL, " +
                    "venue_name VARCHAR(50) NOT NULL, " +
                    "league_name VARCHAR(50) NOT NULL, " +
                    "league_year INTEGER NOT NULL, " +
                    "id INTEGER not NULL AUTO_INCREMENT, " +
                    "PRIMARY KEY (id), " +
                    "FOREIGN KEY (p1_email) REFERENCES Player(email), " +
                    "FOREIGN KEY (p2_email) REFERENCES Player(email), " +
                    "FOREIGN KEY (venue_name) REFERENCES Venue(name), " +
                    "FOREIGN KEY (court_number, venue_name) REFERENCES Court(number, venue_name), " +
                    "FOREIGN KEY (league_name, league_year) REFERENCES League(name, year), " +
                    "FOREIGN KEY (p1_email, league_name, league_year) REFERENCES League_Player(email, league_name, league_year), " +
                    "FOREIGN KEY (p2_email, league_name, league_year) REFERENCES League_Player(email, league_name, league_year)" +
                    ")";
            statement.executeUpdate(sql);
            System.out.println("Table Played_Match is created.");

            sql = "INSERT INTO Played_Match (p1_email, p2_email, p1_games_won, p2_games_won, date_played, court_number," +
                    " venue_name, league_name, league_year) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
//            int id = 1;
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
//                preparedStatement.setInt(10, id);
//                id++;
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

    /**
     * This function mainly initialise League
     * @param filename  - the file to be read
     */
    public static void initLeague(String filename){
        try{
            Connection connection = makeConnection();

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
                    "PRIMARY KEY (name, year), " +
                    "FOREIGN KEY (winner_email) REFERENCES Player(email)" +
                    ")";
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

    /**
     * This function mainly initialise League_Player
     * @param filename  - the file to be read
     */
    public static void initLeaguePlayer(String filename){
        try{
            Connection connection = makeConnection();

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
                    "PRIMARY KEY (email, league_name, league_year), " +
                    "FOREIGN KEY (email) REFERENCES Player(email), " +
                    "FOREIGN KEY (league_name, league_year) REFERENCES League(name, year) " +
                    ")";
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

    /**
     * This function mainly get all players information
     * @return
     */
//    create view contact_details as select player.email, concat_ws(' ', player.forename, player.middlenames,player.surname), group_concat(player_phone.phone_number separator ',') from player, player_phone where player.email = player_phone.email group by player.email order by player.surname, concat_ws(',', player.forename, player.middlenames, player.surname), player.email;
    public static ArrayList<Model> getAllPlayers(){
        ArrayList<Model> playerLists = new ArrayList<>();
        LinkedHashMap<String, Player> map = new LinkedHashMap<>();

        Connection connection = makeConnection();
        Statement statement = null;

        try{
            statement = connection.createStatement();
            String query =
//                    "CREATE VIEW view_contact_details AS \n" +
                    "SELECT CONCAT_WS(' ', player.forename, Player.middlenames, player.surname) AS 'Full name', player.email AS 'Email address',  \n" +
                    "GROUP_CONCAT(player_phone.phone_number separator ',') AS 'Phone number(s)' \n" +
                    "FROM player, player_phone \n" +
                    "WHERE player.email = player_phone.email \n" +
                    "GROUP BY player.email \n" +
                    "ORDER BY player.surname, CONCAT_WS(' ', player.forename, Player.middlenames, player.surname), player.email;";

            ResultSet playerResults  = statement.executeQuery(query);

            while(playerResults.next()){
                // Retrieve by column label
                String fullname = playerResults.getString(1);
                String email = playerResults.getString(2);
                String phoneNumbers = playerResults.getString(3);
//                System.out.println(email);
                Player player = new Player(email, fullname, null, phoneNumbers);
                playerLists.add(player);
            }
            playerResults.close();
        }
        catch (SQLException e){
            e.printStackTrace();
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
            String query =
//                    "CREATE VIEW view_never_played AS\n" +
                    "SELECT a.number AS 'Court number', a.venue_name AS 'Venue name', Venue.address AS 'Address'\n" +
                    "FROM Venue, \n" +
                    "(SELECT DISTINCT Court.number, Court.venue_name \n" +
                    "FROM Court \n" +
                    "LEFT JOIN Played_Match \n" +
                    "ON Court.venue_name = Played_Match.venue_name\n" +
                    "and Court.number = Played_Match.court_number\n" +
                    "WHERE Played_Match.id is NULL\n" +
                    "ORDER BY Court.venue_name ASC, Court.number ASC) a \n" +
                    "WHERE a.venue_name = Venue.name; ";
            ResultSet resultSet  = statement.executeQuery(query);

            while(resultSet.next()){
                String venueName = resultSet.getString(2);
                int courtNumber = resultSet.getInt(1);
                String venueAddress = resultSet.getString(3);

                Court court = new Court(courtNumber, venueName, venueAddress);
                res.add(court);

            }
            resultSet.close();
            statement.close();
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
                    "SELECT Winner.winner_email AS 'Email address', COUNT(Winner.id) AS 'Number of matches won'\n" +
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
            String query =
                    "SELECT CONCAT_WS(' ', Player.forename, Player.middlenames, Player.surname)\n" +
                    "FROM Player\n" +
                    "WHERE Player.email = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                fullname = resultSet.getString(1);
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

        try{
            String sql = "SELECT DISTINCT year FROM League WHERE name = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();

            while(rs.next()){
                leagueList.add(Integer.parseInt(rs.getString(1)));
            }

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
            String query = "INSERT INTO Played_Match (p1_email, p2_email, p1_games_won, p2_games_won, date_played, court_number," +
                    " venue_name, league_name, league_year) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(query);

            // TODO: error string and exceptions
            preparedStatement.setString(1, p1Email);
            preparedStatement.setString(2, p2Email);
            preparedStatement.setInt(3, p1GamesWon);
            preparedStatement.setInt(4, p2GamesWon);
            preparedStatement.setDate(5, datePlayed);
            preparedStatement.setInt(6, courtNumber);
            preparedStatement.setString(7, venueName);
            preparedStatement.setString(8, leagueName);
            preparedStatement.setInt(9, leagueYear);
            preparedStatement.executeUpdate();

            connection.close();
            preparedStatement.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    // TODO: new view to rank players based on their prize money
    public static LinkedHashMap<String, String> rankPlayers(){
        Connection connection = null;
        Statement statement = null;
        LinkedHashMap<String, String> map = new LinkedHashMap<>();

        try{
            connection = makeConnection();
            statement = connection.createStatement();
            String query =
//                    "CREATE VIEW view_rank_players AS\n" +
                    "SELECT CONCAT_WS(' ', Player.forename, Player.middlenames, Player.surname) AS 'Full name', SUM(League.prize_money) AS 'Total Prize' FROM Player, League \n" +
                    "WHERE Player.email = League.winner_email\n" +
                    "GROUP BY League.winner_email\n" +
                    "ORDER BY SUM(League.prize_money) DESC";
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                map.put(resultSet.getString(1), "" + resultSet.getFloat(2));
            }

            connection.close();
            statement.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return map;
    }

//    create procedure proc_delete_venue(IN venue_name VARCHAR(50)) begin start transaction; delete from court where court.venue_name = venue_name; delete from venue where venue.name = venue_name; commit; end;//
}
