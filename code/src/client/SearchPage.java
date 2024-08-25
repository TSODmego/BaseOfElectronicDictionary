//Zhiyuan Liu 1071288
package client;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JLabel;

public class SearchPage {
    private JFrame frmSearchpage;
    private JTextField wordBox;
    private JTextArea meaningBox;
    private Client client;

    public SearchPage(Client client) {
        this.client = client;
        initialize();
    }

    private void initialize() {
        frmSearchpage = new JFrame();
        frmSearchpage.setTitle("SearchPage");
        frmSearchpage.setBounds(100, 100, 450, 300);
        frmSearchpage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton btnNewButton = new JButton("ReturnHome");
        btnNewButton.setBounds(10, 10, 125, 32);
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Home homeWindow = new Home(client);
                homeWindow.showWindow();
                frmSearchpage.dispose();
            }
        });
        frmSearchpage.getContentPane().setLayout(null);
        frmSearchpage.getContentPane().add(btnNewButton);

        JButton btnNewButton_1 = new JButton("Search");
        btnNewButton_1.setBounds(315, 50, 109, 41);
        btnNewButton_1.setFont(new Font("宋体", Font.PLAIN, 15));
        btnNewButton_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	    String word = wordBox.getText().trim();
        	    if (!word.isEmpty()) {
        	        String request = "{\"type\":\"SEARCH\",\"word\":\"" + word + "\"}";
        	        client.sendRequest(request);
        	        String response = client.getResponse();
        	        if (response.equals("1")) {
        	            ErrorPage errorPage = new ErrorPage();
        	            
        	            meaningBox.setText("");
        	            errorPage.showWindow("The word \"" + word + "\" was not found in the dictionary.");
        	            wordBox.setText("");
        	        } else {
        	            meaningBox.setText(response.replace("___", "\n"));
        	        }
        	    }
        	}
        });
        frmSearchpage.getContentPane().add(btnNewButton_1);

        wordBox = new JTextField();
        wordBox.setFont(new Font("宋体", Font.PLAIN, 15));
        wordBox.setBounds(63, 50, 254, 41);
        frmSearchpage.getContentPane().add(wordBox);
        wordBox.setColumns(10);

        meaningBox = new JTextArea();
        meaningBox.setBounds(63, 101, 361, 150);
        meaningBox.setEditable(false);
        frmSearchpage.getContentPane().add(meaningBox);
        
        JLabel lblNewLabel = new JLabel("Word:");
        lblNewLabel.setBounds(24, 63, 54, 15);
        frmSearchpage.getContentPane().add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("Meaning:");
        lblNewLabel_1.setBounds(10, 116, 54, 15);
        frmSearchpage.getContentPane().add(lblNewLabel_1);
    }

    public void showWindow() {
        frmSearchpage.setVisible(true);
        frmSearchpage.setLocationRelativeTo(null);
    }
}
