package data;

import constants.Status;

import java.util.HashMap;
import java.util.Map;

public class Response {
    private String httpVersion;
    private Status status;
    private Map<String, String> headers;
    private String body;

    public Response(String httpVersion, Status status) {
        this.httpVersion = httpVersion;
        this.status = status;
        this.headers = new HashMap<>();
        this.body = "";
    }

    public Response(String httpVersion, Status status, Map<String, String> headers) {
        this.httpVersion = httpVersion;
        this.status = status;
        this.headers = new HashMap<>(headers);
        this.body = "";
    }

    public Response(String httpVersion, Status status, Map<String, String> headers, String body) {
        this.httpVersion = httpVersion;
        this.status = status;
        this.headers = new HashMap<>(headers);
        this.body = body;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = new HashMap<>(headers);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void appendBody(String body) {
        this.body += body;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    private String getHeadersResponse() {
        StringBuilder headersResponse = new StringBuilder();
        headers.forEach((header, headerValue) -> headersResponse.append(header)
                .append(": ")
                .append(headerValue)
                .append("\r\n"));

        return headersResponse.toString();
    }

    public String getSimpleOutput() {
        return httpVersion + " " + status.getStatusCode() + " " + status.getStatusPhrase();
    }

    @Override
    public String toString() {
        return getSimpleOutput() + "\r\n" + getHeadersResponse() + "\r\n" + body;
    }
}
