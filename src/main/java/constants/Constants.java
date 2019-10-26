package constants;

import java.nio.file.Paths;

public abstract class Constants {

    public static String GET = "get";
    public static String POST = "post";
    public static String HTTP10 = "HTTP/1.0";

    public final static String CONTENT_LENGTH = "Content-Length";
    public final static String CONTENT_TYPE = "Content-Type";

    public static String CWD = Paths.get(".").toAbsolutePath().normalize().toString();
}
