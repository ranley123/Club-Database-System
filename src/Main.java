import java.sql.*;

public class Main {
    Connection dbConnection = null;
    public static void main(String[] args){

        System.out.println("Database Loading...");
    }

    private void initDatabase() throws SQLException{
        dbConnection = null;

        try{
            String dbUrl = "jdbc:mariadb:club";
            dbConnection = DriverManager.getConnection(dbUrl);
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }

        Statement statement  = null;

        try{
            statement = dbConnection.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS club");
            statement.executeUpdate("CREATE TABLE club (id int, question VARCHAR(100))");
            statement.close();
        }
    }
}
