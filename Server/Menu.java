import java.net.Socket;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Menu{
	
	private static ArrayList<String> accountBank = new ArrayList<>();
	private static File bankPath;

	static BufferedReader serverReader;
	static PrintWriter serverWriter;

	static BufferedReader fileReader;
	static PrintWriter fileWriter;
	
	private static final String end = "ENDMSG";
	private static final String close = "CLOSEPROGRAM";

	public Menu(Socket client) throws IOException{
		serverReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
    	serverWriter = new PrintWriter(client.getOutputStream(), true);
		
		fileReader = new BufferedReader(new FileReader("./AccountBank.txt"));

		loadAccountBank();

		mainMenu();

		serverReader.close();
		serverWriter.close();
		fileReader.close();
		fileWriter.close();
	}

	public static void  mainMenu() throws IOException{
		serverWriter.println("---Main Menu---");
		if(accountBank.size() > 0){
			serverWriter.println("Login");
		}
		serverWriter.println("Create Account");
		serverWriter.println("Exit");
		serverWriter.println(end);
		serverWriter.flush();

		switch (serverReader.readLine()){
			case "Login" -> {
				login();	
			}
			case "Create Account" -> {
				createAccount();
				mainMenu();
			}
			case "Exit" -> { 
				serverWriter.println("Exiting...");
				serverWriter.println(close);
				serverWriter.println(close);
				serverWriter.flush();
			}
			default -> {
				serverWriter.println("\nInvalid Input\n");
				serverWriter.flush();
				mainMenu();
			}
		}
	}

	private static void loadAccountBank() throws IOException{
		String line;
		
		while((line = fileReader.readLine()) != null){
			accountBank.add(line);
		}
	}

	public static void login() throws IOException{
		serverWriter.println("Enter Account Name:");
		serverWriter.println(end);
		serverWriter.flush();

		String accountName = serverReader.readLine();

		serverWriter.println("Enter Password:");
		serverWriter.println(end);
		serverWriter.flush();

		String password = serverReader.readLine();

		if(!accountExists(accountName, password)){
			serverWriter.println("Exit login screen?");
			serverWriter.println(end);
			serverWriter.flush();
			switch(serverReader.readLine()){
				case "Yes", "YES", "yes", "y", "Y" -> mainMenu();
				default -> login(); 
			}
		}
		else{
			serverWriter.printf("You've logged in as %s\n", accountName);
			serverWriter.println(close);
			serverWriter.flush();
		}
	}

	private static boolean accountExists(String accountName, String password) throws IOException{
		
		String[] accountData;	
			
		for(int i = 0;i < accountBank.size();i++){
			accountData = accountBank.get(i).split("\\|");		
			
			if(accountName.equals(accountData[0])){
				if(password.equals(accountData[1])){
					return true;
				}
				else{
					serverWriter.println("Incorrect Password!");
					serverWriter.flush();
					return false;
				}
			}
		}

		serverWriter.println("Account does not exist");
		return false;
	}

	public static void createAccount() throws IOException{
		serverWriter.println("Enter Account Name:");
		serverWriter.println(end);
		serverWriter.flush();

		String name = serverReader.readLine();

		serverWriter.println("Enter Password:");
		serverWriter.println(end);
		serverWriter.flush();

		String password = serverReader.readLine();

		saveAccount(name + "|" + password);

		serverWriter.println("Account Created");
		serverWriter.flush();
	}

	public static void saveAccount(String accountData) throws IOException{
		accountBank.add(accountData);
		fileWriter = new PrintWriter("./AccountBank.txt");

		for(int i = 0;i < accountBank.size();i++){
			fileWriter.println(accountBank.get(i));
			fileWriter.flush();
		}
	}
}
