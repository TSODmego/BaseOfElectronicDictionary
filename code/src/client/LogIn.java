//Zhiyuan Liu 1071288
package client;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JCheckBox;

public class LogIn {
    private JFrame frmLogin;
    private JPasswordField passwordField;
    private Client client;

    public LogIn(Client client) {
        this.client = client;
        initialize();
    }

    private void initialize() {
        frmLogin = new JFrame();
        frmLogin.setTitle("LogIn");
        frmLogin.setBounds(100, 100, 450, 300);
        frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmLogin.getContentPane().setLayout(null);

        JButton btnNewButton = new JButton("Home");
        btnNewButton.setFont(new Font("宋体", Font.PLAIN, 15));
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Home homeWindow = new Home(client);
                homeWindow.showWindow();
                frmLogin.dispose();
            }
        });
        btnNewButton.setBounds(10, 10, 81, 37);
        frmLogin.getContentPane().add(btnNewButton);

        JLabel lblNewLabel = new JLabel("ManagerPassward:");
        lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 16));
        lblNewLabel.setBounds(10, 57, 161, 45);
        frmLogin.getContentPane().add(lblNewLabel);

        JButton btnNewButton_1 = new JButton("Log in");
        btnNewButton_1.setFont(new Font("宋体", Font.PLAIN, 17));
        btnNewButton_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	    String password = new String(passwordField.getPassword());
        	    if (password.isEmpty()) {
        	        ErrorPage errorPage = new ErrorPage();
        	        errorPage.showWindow("Password can't be empty, guests please go to guest page");
        	    } else {
        	        String request = "{\"type\":\"LOGIN\",\"password\":\"" + password + "\"}";
        	        client.sendRequest(request);
        	        String response = client.getResponse();
        	        if (response.equals("0")) {
        	            ManagerPage managerWindow = new ManagerPage(client);
        	            managerWindow.showWindow();
        	            frmLogin.dispose();
        	        } else {
        	            ErrorPage errorPage = new ErrorPage();
        	            errorPage.showWindow("Password incorrect. Try again please.");
        	        }
        	    }
        	}
        });
        btnNewButton_1.setBounds(298, 192, 126, 59);
        frmLogin.getContentPane().add(btnNewButton_1);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("宋体", Font.PLAIN, 20));
        passwordField.setEchoChar('*');
        passwordField.setBounds(10, 99, 414, 66);
        frmLogin.getContentPane().add(passwordField);

        JCheckBox showPasswordButton = new JCheckBox("Show Password");
        showPasswordButton.setFont(new Font("宋体", Font.PLAIN, 15));
        showPasswordButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (showPasswordButton.isSelected()) {
                    passwordField.setEchoChar((char) 0);
                } else {
                    passwordField.setEchoChar('*');
                }
            }
        });
        showPasswordButton.setBounds(6, 178, 139, 23);
        frmLogin.getContentPane().add(showPasswordButton);
    }

    public void showWindow() {
        frmLogin.setVisible(true);
        frmLogin.setLocationRelativeTo(null);
    }
}
