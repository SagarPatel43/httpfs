package data;

import java.util.Map;

public abstract class BaseRequest {

    private String uri;
    private String httpVersion;
    private Map<String, String> headers;

    BaseRequest(String uri, String httpVersion, Map<String, String> headers) {
        this.uri = uri;
        this.httpVersion = httpVersion;
        this.headers = headers;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public abstract String getMethod();
}
