//Zhiyuan Liu 1071288
package client;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextArea;

public class Home {
    private JFrame frmHome;
    private Client client;

    public Home(Client client) {
        this.client = client;
        initialize();
    }

    private void initialize() {
        frmHome = new JFrame();
        frmHome.setTitle("Home");
        frmHome.setBounds(100, 100, 450, 300);
        frmHome.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmHome.getContentPane().setLayout(null);

        JButton btnNewButton = new JButton("Guest");
        btnNewButton.setFont(new Font("宋体", Font.PLAIN, 30));
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SearchPage guestWindow = new SearchPage(client);
                guestWindow.showWindow();
                frmHome.dispose();
            }
        });
        btnNewButton.setBounds(38, 142, 155, 86);
        frmHome.getContentPane().add(btnNewButton);

        JButton btnNewButton_1 = new JButton("Manager");
        btnNewButton_1.setFont(new Font("宋体", Font.PLAIN, 26));
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LogIn logWindow = new LogIn(client);
                logWindow.showWindow();
                frmHome.dispose();
            }
        });
        btnNewButton_1.setBounds(241, 142, 155, 86);
        frmHome.getContentPane().add(btnNewButton_1);

        JTextArea txtrWelcomeToMy = new JTextArea();
        txtrWelcomeToMy.setEditable(false);
        txtrWelcomeToMy.setFont(new Font("Monospaced", Font.PLAIN, 13));
        txtrWelcomeToMy.setText("Welcome to Platypus Dictionary!\r\nYou can search words by guest, \r\nbut if you want to change anything of directionary, \r\nplease log in as manager.");
        txtrWelcomeToMy.setBounds(10, 46, 414, 86);
        frmHome.getContentPane().add(txtrWelcomeToMy);

        JLabel lblNewLabel = new JLabel("Platypus Dictionary");
        lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 17));
        lblNewLabel.setBounds(119, 10, 220, 26);
        frmHome.getContentPane().add(lblNewLabel);

        frmHome.setLocationRelativeTo(null);
    }

    public void showWindow() {
        frmHome.setVisible(true);
        frmHome.setLocationRelativeTo(null);
    }
}