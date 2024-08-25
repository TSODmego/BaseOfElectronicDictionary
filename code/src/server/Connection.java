//Zhiyuan Liu 1071288
package server;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;



public class Connection extends Thread {
    private Socket threadSocket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private Gson gson;

    public Connection(Socket socket) {
        this.threadSocket = socket;
        gson = new Gson();
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(threadSocket.getInputStream(), StandardCharsets.UTF_8));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(threadSocket.getOutputStream(), StandardCharsets.UTF_8));
        } catch (IOException e) {
        	System.out.println("Failled create reader and writer.");
        }
    }

    public void run() {
        String request;
        try {
            while ((request = bufferedReader.readLine()) != null) {
                JsonObject jsonObject = gson.fromJson(request, JsonObject.class);
                String type = jsonObject.get("type").getAsString();
                // check which type of json recieved from the client:
                switch (type) {
                
                    case "ADD":
                        handleAddRequest(jsonObject);
                        break;
                        
                    case "LOGIN":
                        handleLoginRequest(jsonObject);
                        break;

                    case "SEARCH":
                        handleSearchRequest(jsonObject);
                        break;
                        
                    case "REMOVE":
                        handleRemoveRequest(jsonObject);
                        break;
                        
                    case "UPDATE":
                        handleUpdateRequest(jsonObject);
                        break;
                        
                    default:
                        sendResponse("");
                }

                Server.writeDictionary(); 

                
                System.out.println("Dictionary updated and saved to file.");
            }
        } catch (IOException e) {
            System.out.println("Lost the connection to the client");
        } catch (Exception e) {
            System.out.println("Exception Failed, try later.");
        } finally {
            closeConnection();
        }
    }

    //Add function:
    private void handleAddRequest(JsonObject jsonObject) {
        String word = jsonObject.get("word").getAsString();
        JsonArray meaningsArray = jsonObject.get("meanings").getAsJsonArray();
        List<String> meanings = new ArrayList<>();
        int index = 1;
        for (JsonElement element : meaningsArray) {
            meanings.add(index + ": " + element.getAsString());
            index++;
        }

        if (Server.getDictionary().containsKey(word)) {
            sendResponse(String.valueOf(Server.FAIL)); 
        } else {
            String meaningString = String.join("___", meanings);
            System.out.print(word+ ":" + meaningString+"\n");
            Server.getDictionary().put(word, meaningString);
            sendResponse(String.valueOf(Server.SUCCESS)); 
        }
    }
    
    //log in function:
    private void handleLoginRequest(JsonObject jsonObject) {
        String password = jsonObject.get("password").getAsString();
        if (Server.checkPassword(password)) {
        	sendResponse(String.valueOf(Server.SUCCESS)); 
        } else {
        	sendResponse(String.valueOf(Server.FAIL)); 
        }
    }
    
    //Search function:
    private void handleSearchRequest(JsonObject jsonObject) {
        String word = jsonObject.get("word").getAsString();
        if (Server.getDictionary().containsKey(word)) {
            sendResponse(Server.getDictionary().get(word));
        } else {
        	sendResponse(String.valueOf(Server.FAIL)); 
        }
    }
    
    //Remove function:
    private void handleRemoveRequest(JsonObject jsonObject) {
        String word = jsonObject.get("word").getAsString();
        if (Server.getDictionary().containsKey(word)) {
            Server.getDictionary().remove(word);
            Server.writeDictionary();
            sendResponse(String.valueOf(Server.SUCCESS)); 
        } else {
        	sendResponse(String.valueOf(Server.FAIL)); 
        }
    }
    
    //Update function:
    private void handleUpdateRequest(JsonObject jsonObject) {
        String word = jsonObject.get("word").getAsString().toLowerCase();
        String meaning = jsonObject.get("meaning").getAsString();
        if (Server.getDictionary().containsKey(word)) {
            String existingMeaning = Server.getDictionary().get(word);
            String[] meanings = existingMeaning.split("___");
            int newMeaningIndex = meanings.length + 1;
            boolean found = false;
            for (String m : meanings) {
                if (m.substring(m.indexOf(": ") + 2).equalsIgnoreCase(meaning)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                existingMeaning += "___" + newMeaningIndex + ": " + meaning;
                Server.getDictionary().put(word, existingMeaning);
                Server.writeDictionary();
                sendResponse(String.valueOf(Server.SUCCESS)); 
            } else {
                sendResponse("DUPLICATE"); 
            }
        } else {
            sendResponse(String.valueOf(Server.FAIL)); 
        }
    }
    
    //a way for server to send the response to the client:
    private void sendResponse(String response) {
        try {
            bufferedWriter.write(response + "\n");
            bufferedWriter.flush();
        } catch (IOException e) {
        	System.out.print("Failed to send the response");
        }
    }

    private void closeConnection() {
        try {
            bufferedReader.close();
            bufferedWriter.close();
            threadSocket.close();
        } catch (IOException e) {
        	System.out.print("connection bet client closed");
        }
    }
}