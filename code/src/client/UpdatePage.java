//Zhiyuan Liu 1071288
package client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class UpdatePage {
    private JFrame frmUpdatepage;
    private Client client;
    private JTextField wordBox;
    private JTextField meaningBox;

    public UpdatePage(Client client) {
        this.client = client;
        initialize();
    }


	/**
	 * Initialize the contents of the frame.
	 */
    private void initialize() {
        frmUpdatepage = new JFrame(); 
        frmUpdatepage.setTitle("UpdatePage");
        frmUpdatepage.setBounds(100, 100, 450, 300);
        frmUpdatepage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmUpdatepage.getContentPane().setLayout(null);


    
		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                ManagerPage managerPage = new ManagerPage(client);
                managerPage.showWindow();
                frmUpdatepage.dispose();
			}
		});
		btnNewButton.setFont(new Font("宋体", Font.PLAIN, 15));
		btnNewButton.setBounds(10, 210, 93, 41);
		frmUpdatepage.getContentPane().add(btnNewButton);
		
		wordBox = new JTextField();
		wordBox.setBounds(116, 10, 286, 66);
		frmUpdatepage.getContentPane().add(wordBox);
		wordBox.setColumns(10);
		
		meaningBox = new JTextField();
		meaningBox.setColumns(10);
		meaningBox.setBounds(116, 84, 286, 80);
		frmUpdatepage.getContentPane().add(meaningBox);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String word = wordBox.getText().trim();
		        String meaning = meaningBox.getText().trim();
		        if (word.isEmpty()) {
		            ErrorPage errorPage = new ErrorPage();
		            errorPage.showWindow("Word cannot be empty.");
		        } else if (meaning.isEmpty()) {
		            ErrorPage errorPage = new ErrorPage();
		            errorPage.showWindow("Meaning cannot be empty.");
		        } else {
		            String request = "{\"type\":\"UPDATE\",\"word\":\"" + word + "\",\"meaning\":\"" + meaning + "\"}";
		            client.sendRequest(request);
		            String response = client.getResponse();
		            if (response.equals("0")) {
		                SuccessPage successPage = new SuccessPage();
		                successPage.showWindow("Meaning updated successfully.");
		                wordBox.setText("");
		                meaningBox.setText("");
		            } else if (response.equals("1")) {
		                ErrorPage errorPage = new ErrorPage();
		                errorPage.showWindow("Word not found in the dictionary.");
		            } else if (response.equals("DUPLICATE")) {
		                ErrorPage errorPage = new ErrorPage();
		                errorPage.showWindow("Meaning already exists for the word."); // 显示meaning重复的错误提示
		            } else {
		                ErrorPage errorPage = new ErrorPage();
		                errorPage.showWindow("Error occurred while updating the meaning.");
		            }
		        }
		    }
		});
		
		btnUpdate.setFont(new Font("宋体", Font.PLAIN, 18));
		btnUpdate.setBounds(274, 174, 128, 77);
		frmUpdatepage.getContentPane().add(btnUpdate);
		
		JLabel lblNewLabel = new JLabel("Word:");
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 15));
		lblNewLabel.setBounds(35, 30, 68, 25);
		frmUpdatepage.getContentPane().add(lblNewLabel);
		
		JLabel lblMeaning = new JLabel("Meaning:");
		lblMeaning.setFont(new Font("宋体", Font.PLAIN, 15));
		lblMeaning.setBounds(35, 98, 68, 25);
		frmUpdatepage.getContentPane().add(lblMeaning);
	}
    public void showWindow() {
    	frmUpdatepage.setVisible(true);
    	frmUpdatepage.setLocationRelativeTo(null);
    }
}
