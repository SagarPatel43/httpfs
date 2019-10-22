package data;

import java.util.HashMap;
import java.util.Map;

public class Response {
    private String httpVersion;
    private String statusCode;
    private String reasonPhrase;
    private Map<String, String> headers;
    private String body;

    public Response() {
        this.httpVersion = "";
        this.statusCode = "";
        this.reasonPhrase = "";
        this.headers = null;
        this.body = "";
    }

    public Response(String httpVersion, String statusCode, String reasonPhrase, Map<String, String> headers, String body) {
        this.httpVersion = httpVersion;
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
        this.headers = new HashMap<>(headers);
        this.body = body;
    }

    public Response(String httpVersion, String statusCode, String reasonPhrase, Map<String, String> headers) {
        this.httpVersion = httpVersion;
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
        this.headers = new HashMap<>(headers);
        this.body = "";
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = new HashMap<>(headers);
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

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    public void setReasonPhrase(String reasonPhrase) {
        this.reasonPhrase = reasonPhrase;
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

    @Override
    public String toString() {
        return httpVersion + " " + statusCode + " " + reasonPhrase + "\r\n" + getHeadersResponse() + "\r\n" + body;
    }
}
