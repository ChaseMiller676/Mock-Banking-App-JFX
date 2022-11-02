import java.net.Socket;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Client{
	private static final String end = "ENDMSG";
	private static final String close = "CLOSEPROGRAM";

	public static void main(String[] args) {
    	try(
			Socket client = new Socket("localhost", 4999);
			BufferedReader clientReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter clientWriter = new PrintWriter(client.getOutputStream());
			Scanner clientScanner = new Scanner(System.in);
		){
			String clientInput;

			while(true){
						if(printServerOutput(clientReader)){	
						clientInput = clientScanner.nextLine();
						clientWriter.println(clientInput);
						clientWriter.flush();
					}
					else{
						break;
					}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private static boolean printServerOutput(BufferedReader clientReader) throws IOException{
		String serverOutput;

		while((serverOutput = clientReader.readLine()) != null){
			if(serverOutput.equals(end)){
				break;
			}
			if(serverOutput.equals(close)){
				return false;
			}

			System.out.println(serverOutput);
		}

		return true;
	}
}
