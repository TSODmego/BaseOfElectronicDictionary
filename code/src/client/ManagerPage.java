//Zhiyuan Liu 1071288
package client;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class ManagerPage {
    private JFrame frmManagerPage;
    private Client client;

    public ManagerPage(Client client) {
        this.client = client;
        initialize();
    }

    private void initialize() {
        frmManagerPage = new JFrame();
        frmManagerPage.setTitle("ManagerPage");
        frmManagerPage.setBounds(100, 100, 450, 300);
        frmManagerPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmManagerPage.getContentPane().setLayout(null);

        JButton btnNewButton = new JButton("LogOut");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Home homeWindow = new Home(client);
                homeWindow.showWindow();
                frmManagerPage.dispose();
            }
        });
        btnNewButton.setBounds(10, 10, 100, 50);
        frmManagerPage.getContentPane().add(btnNewButton);

        JButton btnNewButton_1 = new JButton("Search or remove");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ManageCurrent manageCurrent = new ManageCurrent(client);
                manageCurrent.showWindow();
                frmManagerPage.dispose();
            }
        });
        btnNewButton_1.setFont(new Font("宋体", Font.PLAIN, 16));
        btnNewButton_1.setBounds(196, 10, 228, 104);
        frmManagerPage.getContentPane().add(btnNewButton_1);

        JButton btnNewButton_2 = new JButton("Add new words");
        btnNewButton_2.setFont(new Font("宋体", Font.PLAIN, 20));
        btnNewButton_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddWordsPage addWordsPage = new AddWordsPage(client);
                addWordsPage.showWindow();
                frmManagerPage.dispose();
            }
        });
        btnNewButton_2.setBounds(213, 124, 211, 104);
        frmManagerPage.getContentPane().add(btnNewButton_2);
        
        JButton btnNewButton_2_1 = new JButton("Update words");
        btnNewButton_2_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                UpdatePage updatePage = new UpdatePage(client);
                updatePage.showWindow();
                frmManagerPage.dispose();
        	}
        });
        btnNewButton_2_1.setFont(new Font("宋体", Font.PLAIN, 20));
        btnNewButton_2_1.setBounds(10, 124, 193, 104);
        frmManagerPage.getContentPane().add(btnNewButton_2_1);
    }

    public void showWindow() {
        frmManagerPage.setVisible(true);
        frmManagerPage.setLocationRelativeTo(null);
    }
}