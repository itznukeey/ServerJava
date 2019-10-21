import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        var server = new Server(1234);
        server.start();
    }
}
