package services;

import data.BaseRequest;
import data.GetRequest;
import data.PostRequest;
import exception.HttpfsException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static constants.Constants.*;

public class RequestParsingService {

    private RequestParsingService() { }

    public static BaseRequest parseRequest(BufferedReader in) throws IOException, HttpfsException {
        String method;
        String uri;
        String httpVersion;
        Map<String, String> headers = new HashMap<>();
        StringBuilder body = new StringBuilder();

        String requestLine = in.readLine();
        if (requestLine != null) {
            if (requestLine.split(" ").length < 3) {
                throw new HttpfsException("Invalid request-line in request");
            }
            String[] requestLineSplit = requestLine.split(" ", 3);
            method = requestLineSplit[0];
            uri = requestLineSplit[1];
            httpVersion = requestLineSplit[2];
        } else {
            throw new HttpfsException("Empty request provided");
        }

        String line;
        while ((line = in.readLine()) != null) {
            if (line.trim().isEmpty()) break;
            if (line.split(": ").length < 2) {
                throw new HttpfsException("Invalid header returned in response");
            }
            String[] headerValue = line.split(": ");
            headers.put(headerValue[0], headerValue[1]);
        }

        if (method.equalsIgnoreCase(POST)) {
            if(!headers.containsKey(CONTENT_LENGTH)) {
                throw new HttpfsException("Content-length header was not provided for POST request");
            }
            int contentLength = Integer.parseInt(headers.get(CONTENT_LENGTH));
            int c;
            while ((c = in.read()) != -1) {
                body.append((char) c);
                // This signals to server that client is done sending data
                if (body.length() == contentLength) break;
            }
            return new PostRequest(uri, httpVersion, headers, body.toString());
        } else if (method.equalsIgnoreCase(GET)) {
            return new GetRequest(uri, httpVersion, headers);
        } else {
            throw new HttpfsException("Only GET/POST methods are supported");
        }
    }
}
