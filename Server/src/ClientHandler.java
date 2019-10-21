import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable {

    private Socket clientSocket;

    private List<ClientHandler> otherClients;

    private final BufferedReader serverInput;

    private final PrintWriter serverOutput;

    @Override
    public void run() {

        try {

            while (true) {
                var message = serverInput.readLine();

                if (message.equalsIgnoreCase("quit")) {
                    clientSocket.close();
                    System.out.println("closed");
                    break;
                }

                System.out.println(clientSocket.getInetAddress().toString() + ": " + message);

                otherClients.forEach(
                        otherClient -> otherClient
                                .serverOutput.println(
                                        clientSocket.getInetAddress().toString()
                                                + ":" + clientSocket.getPort() + ": " + message));

            }
        } catch (IOException e) {
            System.err.println("Client has disconnected, closing this thread");
        } finally {
            serverOutput.close();
            try {
                serverInput.close();
            } catch (IOException e) {
                System.err.println("Error while closing client output");
            }
        }
    }

    public ClientHandler(Socket client, List<ClientHandler> otherClients) throws IOException {
        this.clientSocket = client;
        this.otherClients = otherClients;
        System.out.println("client = " + client.getInetAddress().toString() + ":" + client.getPort());

        serverInput = new BufferedReader(new InputStreamReader(client.getInputStream()));

        serverOutput = new PrintWriter(client.getOutputStream(), true);
    }
}
