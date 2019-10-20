import exception.HttpfsException;
import server.HttpServer;

public class httpfs {

    public static void main(String[] args) {
        try {
            HttpServer.start();
        } catch (HttpfsException e) {
            e.printStackTrace();
        }
    }
}
