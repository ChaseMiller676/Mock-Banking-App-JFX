import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {
    static PrintWriter serverWriter;
    static BufferedReader serverReader;
    static PrintWriter fileWriter;
    static BufferedReader fileReader;
    private static final HashMap<String, String> accounts = new HashMap<>();
    private static final ArrayList<String> keyRing = new ArrayList<>();

    public static void main(String[] args){

        try(
            ServerSocket server = new ServerSocket(4999);
            Socket client = server.accept()
        ){
            serverWriter = new PrintWriter(client.getOutputStream(), true);
            serverReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            fileWriter = new PrintWriter("src/main/resources/AccountBank.txt");

            loadAccountBank();
            String usernameFieldInput;
            String passwordFieldInput;
            String menuOption;

            while(client.isConnected()) {
                menuOption = serverReader.readLine();

                if(menuOption.equals("login")) {
                    usernameFieldInput = serverReader.readLine();
                    passwordFieldInput = serverReader.readLine();

                    if (clientLogin(usernameFieldInput, passwordFieldInput)) {
                        serverWriter.println(0);
                    } else {
                        serverWriter.println(1);
                    }
                }
                if(menuOption.equals("create")){
                    usernameFieldInput = serverReader.readLine();
                    passwordFieldInput = serverReader.readLine();

                    if(createAccount(usernameFieldInput, passwordFieldInput) == 0){
                        saveAccounts();
                        serverWriter.println(0);
                    }
                    else{
                        serverWriter.println(1);
                    }
                }
            }

            fileWriter.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static void loadAccountBank() throws IOException{
        fileReader = new BufferedReader(new FileReader("src/main/resources/AccountBank.txt"));

        String line;
        while((line = fileReader.readLine()) != null){
            String[] lineTokens = line.split("\\|");
            accounts.put(lineTokens[0], lineTokens[1]);
            keyRing.add(lineTokens[0]);
        }

        fileReader.close();
    }

    private static int createAccount(String usernameFieldText, String passwordFieldText){
        if(!usernameFieldText.isBlank() && !passwordFieldText.isBlank() && !keyRing.contains(usernameFieldText)){
            accounts.put(usernameFieldText, passwordFieldText);
            keyRing.add(usernameFieldText);
            return 0;
        }
        else{
            return 1;
        }
    }

    private static void saveAccounts(){
        for (String key : keyRing) {
            fileWriter.println(key + '|' + accounts.get(key));
        }

        fileWriter.flush();
    }

    private static boolean clientLogin(String usernameFieldInput, String passwordFieldInput){
       if(accounts.containsKey(usernameFieldInput)){
           return accounts.get(usernameFieldInput).equals(passwordFieldInput);
       }

       return false;
    }
}