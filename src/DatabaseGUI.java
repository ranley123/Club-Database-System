import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import java.awt.event.*;

public class DatabaseGUI {
    JFrame mainFrame;

    public DatabaseGUI(){
        initInterface();
    }

    private void initInterface(){
        mainFrame = new JFrame("Database");
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
                usernameText.setText("Welcome to Javatpoint.");
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
}
