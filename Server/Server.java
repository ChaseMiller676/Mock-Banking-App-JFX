import java.net.ServerSocket;
import java.net.Socket;

public class Server{
    public static void main(String[] args) {
        try (
            ServerSocket server = new ServerSocket(4999);
            Socket client = server.accept();
        ) {
			Menu menu = new Menu(client);
        } catch (Exception e) {
            System.err.println("Exception: " + e);
        }
    }
}
