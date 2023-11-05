package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);
    private String url;
    private Boolean login = false;
    private Boolean css = false;
    private Integer contentLength = 0;
    private String content;
    private Map<String, String> params = new HashMap<>();

    public HttpRequest(InputStream in) {
        try {
            InputStreamReader reader = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(reader);
            String line;
            String method = null;
            int cnt = 0;

            while (!(line = br.readLine()).isEmpty()) {
                log.info(line);
                if (cnt == 0) {
                    url = line.split(" ")[1].equals("/") ? "/index.html" : line.split(" ")[1];
                    method = line.split(" ")[0].trim();
                    cnt += 1;
                }
                if (line.contains("Content-Length")) {
                    contentLength = Integer.parseInt(line.split(":")[1].trim());
                }
                if (line.contains("Cookie")) {
                    login = Boolean.parseBoolean(line.split(":")[1].split("=")[1]);
                }
            }
            if (contentLength != 0 && method.equals("POST")) {
                content = IOUtils.readData(br, contentLength);
                log.info("content => {}", content);
            }
        } catch (IOException io) {
            log.error(io.getMessage());
        }
    }


    public String getUrl() {
        return url;
    }

    public Integer getContentLength() {
        return contentLength;
    }

    public Boolean getLogin() {
        return login;
    }

    public Boolean getCss() {
        return css;
    }

    public String getContent() {
        return content;
    }

}
