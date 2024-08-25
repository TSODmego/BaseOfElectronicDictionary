//Zhiyuan Liu 1071288

package server;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.BindException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import javax.swing.SwingUtilities;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int DEFAULT_PORT = 8899;
    private static final String DEFAULT_DICT_PATH = "src/test1.txt";
    private static final String MANAGER_PASSWORD = "abc123";
    
    private static ConcurrentHashMap<String, String> dictionary = new ConcurrentHashMap<>();
    public static int SUCCESS = 0;
    public static int FAIL = 1;

    private static int port = DEFAULT_PORT;
    private static String dictPath = DEFAULT_DICT_PATH;

    private ServerPannel serverPannel;
    private static Server instance;

    public Server() {
        instance = this;
    }

    public static ConcurrentHashMap<String, String> getDictionary() {
        return dictionary;
    }

    public static void main(String[] args) {
        if (args.length == 2) {
            try {
                port = Integer.parseInt(args[0]);
                if (port > 65535 || port < 1024) {
                    System.out.println("Invalid port number: " + port);
                    System.exit(0);
                }
                dictPath = args[1];
            } catch (NumberFormatException e) {
                System.out.println("The input of address is invalid, will start by default address");
            }
        }

        Server server = new Server();
        server.readFile();

        CountDownLatch latch = new CountDownLatch(1);
        SwingUtilities.invokeLater(() -> {
        	//start the UI of server:
            server.serverPannel = new ServerPannel();
            server.updateUIFromFile();
            server.serverPannel.showWindow();
            latch.countDown();
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            System.out.println("System interrupted");
        }

        server.runServer();
    }

    public void readFile() {
        try {
            File file = new File(dictPath);
            if (!file.isFile() || !file.exists()) {
                System.out.println("The input file is not a file");
                dictPath = DEFAULT_DICT_PATH;
                file = new File(dictPath);
            } else {
                InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] lines = line.split("___");
                    String word = lines[0];
                    String meanings = String.join("___", Arrays.copyOfRange(lines, 1, lines.length));
                    dictionary.put(word, meanings);
                }
                System.out.println("Successfully read");
                reader.close();
                inputStreamReader.close();
            }
        } catch (Exception e) {
            System.out.println("read failed");
        }
    }

    public void runServer() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.print("Server is running on port: " + port +"\n");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                Connection connection = new Connection(clientSocket);
                connection.start();
            }

        } catch (BindException e) {
            System.out.println("Port is already in use. Try another port...");

        } catch (IOException e) {
            System.out.print("Failed to start server on port " + port + ": " + e.getMessage());

        } catch (Exception e) {
            System.out.print("Unexpected exception occurred in connection thread: " + e.getMessage());

        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                    System.out.println("Server has been shut down.");
                } catch (IOException e) {
                    System.out.println("Error closing server socket.");
                }
            }
        }
    }

    public void closeServerSocket(ServerSocket serverSocket) {
        if (serverSocket != null) {
            try {
                serverSocket.close();
                System.out.println("Server socket closed.");
            } catch (IOException e) {
                System.out.print("Failed to close the server socket.");
            }
        }
    }

    public static void addWordToDictionary(String word, String meaningString) {
        dictionary.put(word, meaningString);
        System.out.println("Added word to dictionary: " + word + " - " + meaningString);
    }
    //write new word into the dic, also upadte the data in UI:
    public static void writeDictionary() {
        File file = new File(dictPath);
        try (BufferedWriter note = new BufferedWriter(new FileWriter(file))) {
            for (Map.Entry<String, String> entry : dictionary.entrySet()) {
                note.write(entry.getKey() + "___" + entry.getValue() + "\n");
            }
            System.out.println("Dictionary written to file successfully!");
            instance.updateUIFromFile();
        } catch (IOException e) {
            System.out.println("Failed to write dictionary to file: " + e.getMessage());
        }
    }
    
    //update the UI:
    public void updateUIFromFile() {
        StringBuilder content = new StringBuilder();
        for (Map.Entry<String, String> entry : dictionary.entrySet()) {
            String word = entry.getKey();
            String meanings = entry.getValue();
            content.append(word).append(" : ").append(meanings).append("\n");
        }
        if (serverPannel != null) {
            serverPannel.setTextAreaContent(content.toString());
        }
    }

    
    public static boolean checkPassword(String password) {
        if (password.equals(MANAGER_PASSWORD)) {
            return true;
        } else {
            return false;
        } 
    }

}