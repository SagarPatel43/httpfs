package data;

public class ServerConfiguration {

    private boolean verbose;
    private int port;
    private String dataDirectory;

    public ServerConfiguration(boolean verbose, int port, String dataDirectory) {
        this.verbose = verbose;
        this.port = port;
        this.dataDirectory = dataDirectory;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDataDirectory() {
        return dataDirectory;
    }

    public void setDataDirectory(String dataDirectory) {
        this.dataDirectory = dataDirectory;
    }
}
