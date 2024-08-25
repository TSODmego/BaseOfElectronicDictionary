//Zhiyuan Liu 1071288

package server;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import java.awt.Font;

public class ServerPannel {
    private JFrame frmServerpannel;
    private JTextArea dicDisplay;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ServerPannel window = new ServerPannel();
                    window.frmServerpannel.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public ServerPannel() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmServerpannel = new JFrame();
        frmServerpannel.setTitle("ServerPannel");
        frmServerpannel.setBounds(100, 100, 586, 534);
        frmServerpannel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmServerpannel.getContentPane().setLayout(null);

        dicDisplay = new JTextArea();
        dicDisplay.setEditable(false);
        dicDisplay.setBounds(23, 100, 524, 385);
        frmServerpannel.getContentPane().add(dicDisplay);

        JLabel lblNewLabel = new JLabel("Dictionary List:");
        lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 18));
        lblNewLabel.setBounds(29, 33, 213, 54);
        frmServerpannel.getContentPane().add(lblNewLabel);
    }

    public void showWindow() {
        frmServerpannel.setVisible(true);
        frmServerpannel.setLocationRelativeTo(null);
    }


    public void setTextAreaContent(String content) {
        dicDisplay.setText(content);
    }
}