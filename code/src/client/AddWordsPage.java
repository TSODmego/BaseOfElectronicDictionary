//Zhiyuan Liu 1071288

package client;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import com.google.gson.*;
import java.awt.Color;

public class AddWordsPage {
    private JFrame frmAddwordpage;
    private JTextField wordField;
    private JTextField meaningField;
    private JPanel meaningsList;
    private ArrayList<String> meanings = new ArrayList<>();
    private ArrayList<JCheckBox> meaningCheckboxes = new ArrayList<>();
    private Client client;

    public AddWordsPage(Client client) {
        this.client = client;
        initialize();
    }

    private void initialize() {
        frmAddwordpage = new JFrame();
        frmAddwordpage.setTitle("AddWordPage");
        frmAddwordpage.setBounds(100, 100, 450, 300);
        frmAddwordpage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmAddwordpage.getContentPane().setLayout(null);

        JButton btnUpload = new JButton("Upload");
        btnUpload.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String word = wordField.getText().trim();
                if (word.isEmpty()) {
                    ErrorPage errorPage = new ErrorPage();
                    errorPage.showWindow("Word field cannot be empty.");
                } else if (meanings.isEmpty()) {
                    ErrorPage errorPage = new ErrorPage();
                    errorPage.showWindow("At least one meaning must be added.");
                } else {
                    try {
                        Map<String, Object> data = new HashMap<>();
                        data.put("type", "ADD");
                        data.put("word", word.toLowerCase());
                        ArrayList<String> lowercaseMeanings = new ArrayList<>();
                        for (String meaning : meanings) {
                            lowercaseMeanings.add(meaning.toLowerCase());
                        }
                        data.put("meanings", lowercaseMeanings);
                        Gson gson = new Gson();
                        String json = gson.toJson(data);
                        System.out.println("Sending JSON to server: " + json + "\n");

                        client.sendRequest(json);
                        String resp = client.getResponse();

                        System.out.print("res:" + resp+ "\n");
                        if (resp == null) {
                            ErrorPage errorPage = new ErrorPage();
                            errorPage.showWindow("Connection Lost!.");
                        } else if (resp.equals("1")) {
                            ErrorPage errorPage = new ErrorPage();
                            errorPage.showWindow("Duplicate word in the dictionary!");
                        } else if (resp.equals("0")) {
                            SuccessPage successPage = new  SuccessPage();
                            successPage.showWindow("Add word successfully.");
                        } else {
                            ErrorPage errorPage = new ErrorPage();
                            errorPage.showWindow("Added failed.");
                        }

                        wordField.setText("");
                        meaningField.setText("");
                        clearMeaningsPanel();
                    } catch (Exception ex) {
                        ErrorPage errorPage = new ErrorPage();
                        errorPage.showWindow("Didn't add word to the dictionary successfully!");
                    }
                }
            }
        });
        btnUpload.setFont(new Font("宋体", Font.PLAIN, 15));
        btnUpload.setBounds(323, 10, 101, 50);
        frmAddwordpage.getContentPane().add(btnUpload);

        wordField = new JTextField();
        wordField.setBounds(83, 10, 242, 52);
        frmAddwordpage.getContentPane().add(wordField);
        wordField.setColumns(10);

        JLabel lblNewLabel = new JLabel("Meaning:");
        lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 15));
        lblNewLabel.setBounds(10, 64, 78, 52);
        frmAddwordpage.getContentPane().add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Word:");
        lblNewLabel_1.setFont(new Font("宋体", Font.PLAIN, 15));
        lblNewLabel_1.setBounds(25, 17, 63, 37);
        frmAddwordpage.getContentPane().add(lblNewLabel_1);

        meaningField = new JTextField();
        meaningField.setBounds(83, 64, 242, 52);
        frmAddwordpage.getContentPane().add(meaningField);
        meaningField.setColumns(10);

        JButton btnAdd = new JButton("Add ");
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String meaning = meaningField.getText().trim();
                if (!meaning.isEmpty()) {
                    addMeaning(meaning);
                    meaningField.setText("");
                }
            }
        });
        btnAdd.setFont(new Font("宋体", Font.PLAIN, 15));
        btnAdd.setBounds(323, 64, 101, 50);
        frmAddwordpage.getContentPane().add(btnAdd);

        meaningsList = new JPanel();
        meaningsList.setBackground(new Color(255, 255, 255));
        meaningsList.setBounds(83, 120, 245, 131);
        meaningsList.setLayout(new java.awt.GridLayout(0, 1, 0, 5));
        frmAddwordpage.getContentPane().add(meaningsList);

        JButton btnDelete = new JButton("Delete");
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteMeanings();
            }
        });
        btnDelete.setFont(new Font("宋体", Font.PLAIN, 15));
        btnDelete.setBounds(333, 120, 91, 54);
        frmAddwordpage.getContentPane().add(btnDelete);

        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ManagerPage managerPage = new ManagerPage(client);
                managerPage.showWindow();
                frmAddwordpage.dispose();
            }
        });
        btnBack.setFont(new Font("宋体", Font.PLAIN, 15));
        btnBack.setBounds(0, 201, 78, 50);
        frmAddwordpage.getContentPane().add(btnBack);

        JLabel lblLists = new JLabel("Lists:");
        lblLists.setFont(new Font("宋体", Font.PLAIN, 15));
        lblLists.setBounds(10, 122, 78, 52);
        frmAddwordpage.getContentPane().add(lblLists);
    }


    private void addMeaning(String meaning) {
        String lowercaseMeaning = meaning.toLowerCase();
      //add check whether the meanings are the same:
        if (meanings.contains(lowercaseMeaning)) {
            ErrorPage errorPage = new ErrorPage();
            errorPage.showWindow("Meaning \"" + meaning + "\" already exists.");
        } else {
            meanings.add(lowercaseMeaning);
            JCheckBox checkbox = new JCheckBox(lowercaseMeaning);
            meaningsList.add(checkbox);
            meaningCheckboxes.add(checkbox);
            meaningsList.revalidate();
            meaningsList.repaint();
        }
    }

    private void deleteMeanings() {
        for (int i = 0; i < meaningCheckboxes.size(); i++) {
            if (meaningCheckboxes.get(i).isSelected()) {
                meanings.remove(i);
                meaningsList.remove(meaningCheckboxes.get(i));
                meaningCheckboxes.remove(i);
                i--;
            }
        }
        meaningsList.revalidate();
        meaningsList.repaint();
    }

    private void clearMeaningsPanel() {
        meaningsList.removeAll();
        meaningCheckboxes.clear();
        meanings.clear();
        meaningsList.revalidate();
        meaningsList.repaint();
    }

    public void showWindow() {
        frmAddwordpage.setVisible(true);
        frmAddwordpage.setLocationRelativeTo(null);
    }
}
