import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class httpfs {

    public static void main(String[] args) {
        try {
            SocketAddress bindAddress = new InetSocketAddress("127.0.0.1", 8080);
            ServerSocket server = new ServerSocket();

            server.bind(bindAddress);

            System.err.println(server);
            // TODO need interrupt here I suppose
            while (true) {
                Socket client = server.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter out = new PrintWriter(client.getOutputStream(), true);

                // in.ready is magic idk
                String line;
                while ((line = in.readLine()) != null && in.ready()) {
                    System.out.println(line);
                }

                String body = "Im sending this from httpfs. Hello.";
                out.println("HTTP/1.0 200 OK\r\nContent-Type: text/html\r\nContent-Length: " + body.length() + "\r\n\r\n" + body);

                in.close();
                out.close();
                client.close();
            }
            // TODO how to close this safely?
//            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
