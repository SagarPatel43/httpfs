package constants;

import java.nio.file.Paths;

public abstract class Constants {

    public static String GET = "get";
    public static String POST = "post";
    public static String HTTP10 = "HTTP/1.0";

    public final static String CONTENT_LENGTH = "Content-Length";
    public final static String CONTENT_TYPE = "Content-Type";

    public final static String HELP = "help";

    public final static String GENERAL_HELP =
            "\nhttpfs is a simple file server.\n" + "Usage:\n\thttfs [-v] [-p port] [-d PATH-TO-DIR]\n" + "\nThe flags are:\n" +
            "\t-v \tPrints debugging messages.\n" +
            "\t-p \tSpecifies the port number that the server will listen and serve at.\n\t    Default is 8080.\n" +
            "\t-d \tSpecifies the directory that the server will use to read/write requested files.\n\t    Default is the current directory when launching the application.";

    public static String CWD = Paths.get(".").toAbsolutePath().normalize().toString();
}
