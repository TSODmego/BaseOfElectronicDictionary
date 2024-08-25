//Zhiyuan Liu 1071288
package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.LinkedBlockingQueue;
import java.awt.EventQueue;

public class Client extends Thread {
    private static final int DEFAULT_PORT = 8899;
    private static final String DEFAULT_ADDRESS = "localhost";

    private BufferedReader reader;
    private BufferedWriter writer;
    private Socket client;
    private String address;
    private int port;

    public LinkedBlockingQueue<String> requestQueue = new LinkedBlockingQueue<>(1);

    public Client(String addressInput, int portInput) {
        address = addressInput;
        port = portInput;
    }

    @Override
    public void run() {
        try {
            client = new Socket(address, port);
            reader = new BufferedReader(new InputStreamReader(client.getInputStream(), "UTF-8"));
            writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream(), "UTF-8"));
            System.out.println("Connected!");

            String reqString;
            while ((reqString = requestQueue.take()) != null) {
                writer.write(reqString + "\n");
                writer.flush();
            }
        } catch (UnknownHostException e) {
            ErrorPage errorPage = new ErrorPage();
            errorPage.showWindow("Unknown host: " + address + ", please check the address again");
        } catch (IOException e) {
            ErrorPage errorPage = new ErrorPage();
            errorPage.showWindow("Connection failed! Please check the address and try again later. \nCurrent address:" + address + " , port: " + port);
        } catch (InterruptedException e) {
            ErrorPage errorPage = new ErrorPage();
            errorPage.showWindow("Thread was interrupted");
        } finally {
            try {
                if (client != null) {
                    client.close();
                }
            } catch (IOException e) {
                ErrorPage errorPage = new ErrorPage();
                errorPage.showWindow("Error closing socket: " + e.getMessage());
            }
        }
    }

    //receive the response:
    public String getResponse() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            ErrorPage errorPage = new ErrorPage();
            errorPage.showWindow("Error reading response: " + e.getMessage());
            return null;
        }
    }
    
    //send the request to the sever:
    public void sendRequest(String request) {
        try {
            requestQueue.put(request);
        } catch (InterruptedException e) {
            ErrorPage errorPage = new ErrorPage();
            errorPage.showWindow("Error sending request: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String address = DEFAULT_ADDRESS;
        int port = DEFAULT_PORT;

        if (args.length == 2) {
            try {
                address = args[0];
                port = Integer.parseInt(args[1]);
                if (port > 65535 || port < 1024) {
                    ErrorPage errorPage = new ErrorPage();
                    errorPage.showWindow("Invalid port number: " + port);
                    System.out.print("Invalid port number: " + port);
                    System.exit(0);
                }
            } catch (NumberFormatException e) {
                ErrorPage errorPage = new ErrorPage();
                errorPage.showWindow("Invalid port number: " + args[1]);
                System.out.print("Invalid port number");
                System.exit(1);
            }
        }

        try {
            Client client = new Client(address, port);
            client.start();

            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    try {
                        Home window = new Home(client);
                        window.showWindow();
                    } catch (Exception e) {
                        ErrorPage errorPage = new ErrorPage();
                        errorPage.showWindow("Failed to initialize GUI: " + e.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            ErrorPage errorPage = new ErrorPage();
            errorPage.showWindow("Failed to start client: " + e.getMessage());
            System.exit(1);
        }
    }
}
