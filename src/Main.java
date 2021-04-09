import java.sql.*;

public class Main {
    Connection dbConnection = null;
    public static void main(String[] args){
        System.out.println("Database Loading...");
        Main main = new Main();
        main.initDatabase();
    }

    private void initDatabase(){
        dbConnection = null;

        try{
            String dbUrl = "jdbc:mariadb://localhost/club";
//            String uname = "hy30";
//            String passwd = "Yh20000109000537";
            dbConnection = DriverManager.getConnection(dbUrl);
            Statement statement  = null;
            statement = dbConnection.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS club");
            statement.executeUpdate("CREATE TABLE club (id int, question VARCHAR(100))");
            statement.close();
            System.out.println("hi");
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
