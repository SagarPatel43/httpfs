package constants;

public enum Status {
    OK(200, "OK"),
    CREATED(201, "Created"),
    BAD_REQUEST(400, "Bad Request"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found");

    private int statusCode;
    private String statusPhrase;

    Status(int statusCode, String statusPhrase) {
        this.statusCode = statusCode;
        this.statusPhrase = statusPhrase;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusPhrase() {
        return statusPhrase;
    }
}

