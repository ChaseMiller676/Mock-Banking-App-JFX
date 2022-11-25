import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Server {
    static PrintWriter serverWriter;
    static BufferedReader serverReader;
    static final File accountBank = new  File("src/main/resources/AccountBank.txt");
    static final HashMap<String, String> accounts = new HashMap<>();
    static final ArrayList<String> keyRing = new ArrayList<>();

    public static void main(String[] args){

        try(
            ServerSocket server = new ServerSocket(4999);
            Socket client = server.accept()
        ){
            serverWriter = new PrintWriter(client.getOutputStream(), true);
            serverReader = new BufferedReader(new InputStreamReader(client.getInputStream()));

            loadAccountBank();
            String usernameFieldInput;
            String passwordFieldInput;
            String menuOption = "";

            while(client.isConnected()){
                menuOption = serverReader.readLine();

                switch(menuOption){
                    case "login":
                        usernameFieldInput = serverReader.readLine();
                        passwordFieldInput = serverReader.readLine();

                        if (clientLogin(usernameFieldInput, passwordFieldInput)) {
                            serverWriter.println(0);
                        } else {
                           serverWriter.println(1);
                        }
                        break;
                    case "create":
                        usernameFieldInput = serverReader.readLine();
                        passwordFieldInput = serverReader.readLine();

                        if(createAccount(usernameFieldInput, passwordFieldInput) == 0){
                            saveAccounts();
                            serverWriter.println(0);
                        }
                        else{
                            serverWriter.println(1);
                        }
                        break;
                    case "open":
                        ;
                        break;
                    case "close": break;
                    default: System.out.println("Client Disconnected");
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /*
    Login Prompt Methods
     */
    static void loadAccountBank() throws FileNotFoundException{
        Scanner fileReader = new Scanner(accountBank);

        int i = 1;
        while(fileReader.hasNextLine()){
            String[] lineTokens = fileReader.nextLine().split("\\|");
            keyRing.add(lineTokens[0]);
            accounts.put(lineTokens[0], lineTokens[1]);
            i++;
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

    private static void saveAccounts() throws IOException {

        PrintWriter fileWriter = new PrintWriter(new FileWriter(accountBank), true);

        for (String key : keyRing) {
            fileWriter.println(key + '|' + accounts.get(key));
        }

        fileWriter.close();
    }

    private static boolean clientLogin(String usernameFieldInput, String passwordFieldInput){
       if(accounts.containsKey(usernameFieldInput)){
           return accounts.get(usernameFieldInput).equals(passwordFieldInput);
       }

       return false;
    }

    /*
    Deposit Management Methods
    */


}