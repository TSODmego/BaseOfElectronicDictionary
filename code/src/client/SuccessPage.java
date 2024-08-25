//Zhiyuan Liu 1071288
package client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextArea;

public class SuccessPage {

	private JFrame frmsuccess;
    private JTextArea successMessageField;


	/**
	 * Create the application.
	 */
	public SuccessPage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmsuccess = new JFrame();
		frmsuccess.setTitle("success");
		frmsuccess.setBounds(100, 100, 366, 218);
		frmsuccess.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmsuccess.setAlwaysOnTop(true);
		frmsuccess.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("success");
		lblNewLabel.setFont(new Font("华文琥珀", Font.PLAIN, 26));
		lblNewLabel.setBounds(131, 0, 117, 33);
		frmsuccess.getContentPane().add(lblNewLabel);
		
		successMessageField = new JTextArea();
		successMessageField.setEditable(false);
		successMessageField.setLineWrap(true);
		successMessageField.setWrapStyleWord(true);
		successMessageField.setFont(new Font("Monospaced", Font.PLAIN, 18));
		successMessageField.setBounds(10, 28, 330, 141);
		frmsuccess.getContentPane().add(successMessageField);
	}
	
	public void showWindow(String successMessage) {
		successMessageField.setText(successMessage);
		frmsuccess.setVisible(true);
		frmsuccess.setLocationRelativeTo(null);
	}
}

