import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import java.awt.event.*;

public class Main {
    Connection dbConnection = null;

    public static void main(String[] args) {
        System.out.println("Database Loading...");
        Main main = new Main();
//        main.initDatabase();
        main.initInterface();
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

    private void makeConnection() {
        dbConnection = null;

        try {
            String dbUrl = "jdbc:sqlite:test";
            String uname = "hy30";
            String passwd = ".611MKA73dHitM";

            System.out.println("Connecting to Database ...");
            dbConnection = DriverManager.getConnection(dbUrl);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
}
