package data;

import java.util.Map;

import static constants.Constants.GET;

public class GetRequest extends BaseRequest {

    public GetRequest(String uri, String httpVersion, Map<String, String> headers) {
        super(uri, httpVersion, headers);
    }

    @Override
    public String getMethod() {
        return GET;
    }
}
