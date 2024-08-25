//Zhiyuan Liu 1071288
package client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ManageCurrent {

	private JFrame frmManagecurrent;
	private JTextField wordBox;
	private Client client;
	private JButton btnNewButton;
	private JButton btnSearch;
	private JButton btnDelete;


	/**
	 * Create the application.
	 */
	public ManageCurrent(Client client) {
        this.client = client;
        initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmManagecurrent = new JFrame();
		frmManagecurrent.setTitle("ManageCurrent");
		frmManagecurrent.setBounds(100, 100, 450, 300);
		frmManagecurrent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmManagecurrent.getContentPane().setLayout(null);
		
		wordBox = new JTextField();
		wordBox.setBounds(82, 10, 342, 51);
		frmManagecurrent.getContentPane().add(wordBox);
		wordBox.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Words:");
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 15));
		lblNewLabel.setBounds(20, 19, 68, 33);
		frmManagecurrent.getContentPane().add(lblNewLabel);
		
		btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                ManagerPage managerPage = new ManagerPage(client);
                managerPage.showWindow();
                frmManagecurrent.dispose();
			}
		});
		btnNewButton.setFont(new Font("宋体", Font.PLAIN, 15));
		btnNewButton.setBounds(0, 213, 78, 38);
		frmManagecurrent.getContentPane().add(btnNewButton);
		
		JTextArea meaningBox = new JTextArea();
		meaningBox.setBounds(82, 71, 341, 134);
		frmManagecurrent.getContentPane().add(meaningBox);
		
		btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
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
		btnSearch.setFont(new Font("宋体", Font.PLAIN, 15));
		btnSearch.setBounds(92, 213, 89, 38);
		frmManagecurrent.getContentPane().add(btnSearch);
		
		btnDelete = new JButton("Remove");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String word = wordBox.getText().trim();
				if (!word.isEmpty()) {
				    String request = "{\"type\":\"REMOVE\",\"word\":\"" + word + "\"}";
				    client.sendRequest(request);
				    String response = client.getResponse();
				    if (response.equals("0")) {
				        meaningBox.setText("");
				        wordBox.setText("");
				        SuccessPage successPage = new SuccessPage();
				        successPage.showWindow("The word \"" + word + "\" has been successfully removed from the dictionary.");
				    } else if (response.equals("1")) {
				        meaningBox.setText("");
				        wordBox.setText("");
				        ErrorPage errorPage = new ErrorPage();
				        errorPage.showWindow("The word \"" + word + "\" was not found in the dictionary.");
				    } else {
				        meaningBox.setText("");
				        wordBox.setText("");
				        ErrorPage errorPage = new ErrorPage();
				        errorPage.showWindow("An error occurred while removing the word \"" + word + "\" from the dictionary.");
				    }
				}
			}
		});
		btnDelete.setFont(new Font("宋体", Font.PLAIN, 15));
		btnDelete.setBounds(323, 213, 89, 38);
		frmManagecurrent.getContentPane().add(btnDelete);
	}
	
    public void showWindow() {
    	frmManagecurrent.setVisible(true);
    	frmManagecurrent.setLocationRelativeTo(null);
    }
}
