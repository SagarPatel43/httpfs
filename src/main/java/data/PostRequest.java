package data;

import java.util.Map;

import static constants.Constants.POST;

public class PostRequest extends BaseRequest {

    private String body;

    public PostRequest(String uri, String httpVersion, Map<String, String> headers, String body) {
        super(uri, httpVersion, headers);
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String getMethod() {
        return POST;
    }

    @Override
    public String toString() {
        return super.toString() + "\r\n" + body;
    }
}
