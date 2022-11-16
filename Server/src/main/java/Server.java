import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Server {
    static PrintWriter serverWriter;
    static BufferedReader serverReader;
    static FileWriter fileWriter;
    static BufferedReader fileReader;
    private static final HashMap<String, String> accounts = new HashMap<>();

    public static void main(String[] args){

        try(
            ServerSocket server = new ServerSocket(4999);
            Socket client = server.accept()
        ){
            serverWriter = new PrintWriter(client.getOutputStream(), true);
            serverReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            fileWriter = new FileWriter("AccountBank.txt");

            loadAccountBank();
            String usernameFieldInput = "";
            String passwordFieldInput = "";
            String menuOption = "";

            while(client.isConnected()) {
                menuOption = serverReader.readLine();

                if(menuOption.equals("login")) {
                    usernameFieldInput = serverReader.readLine();
                    passwordFieldInput = serverReader.readLine();

                    if (clientLogin(usernameFieldInput, passwordFieldInput)) {
                        serverWriter.println("0");
                    } else {
                        serverWriter.println("1");
                    }
                }
                else if(menuOption.equals("create")){
                    if(createAccount(usernameFieldInput, passwordFieldInput) == 0){
                        saveAccounts();
                        serverWriter.println("2");
                    }
                    else{
                        serverWriter.println("3");
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static void loadAccountBank() throws IOException{
        fileReader = new BufferedReader(new FileReader(Objects.requireNonNull(Server.class.getResource("AccountBank.txt")).getPath()));

        String line;
        while((line = fileReader.readLine()) != null){
            String[] lineTokens = line.split("\\|");
            accounts.put(lineTokens[0], lineTokens[1]);
        }

        fileReader.close();
    }

    private static int createAccount(String usernameFieldText, String passwordFieldText){
        if(!accounts.containsKey(usernameFieldText)){
            accounts.put(usernameFieldText, passwordFieldText);
            return 0;
        }
        else{
            return 1;
        }
    }

    private static void saveAccounts() throws IOException{
        String[] keyRing = accounts.keySet().toArray(new String[0]);

        for (String key : keyRing) {
            fileWriter.write(key + '|' + accounts.get(key) + '\n');
        }

        fileWriter.close();
    }

    private static boolean clientLogin(String usernameFieldInput, String passwordFieldInput){
       if(accounts.containsKey(usernameFieldInput)){
           return accounts.get(usernameFieldInput).equals(passwordFieldInput);
       }

       return false;
    }
}