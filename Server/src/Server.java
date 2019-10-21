import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.List;
import java.util.Vector;

public class Server {

    private ServerSocket serverSocket;

    private List<ClientHandler> connections;

    public Server(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        connections = new Vector<>();
    }

    public void start() throws IOException {
        while (true) {
            var clientSocket = serverSocket.accept();
            var client = new ClientHandler(clientSocket,connections);
            connections.add(client);
            var clientThread = new Thread(client);
            System.out.println("New connection!");
            clientThread.start();
        }
    }
}
