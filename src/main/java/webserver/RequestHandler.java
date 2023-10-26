package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.IOUtils;
import util.SeparationUrl;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // TODO 사용자 요청에 대한 처리는 이 곳에 구현하면 된다.
            InputStreamReader reader = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(reader);
            DataOutputStream dos = new DataOutputStream(out);
            String line;
            String url = null;
            String params = null;
            int contentLength = 0;
            int cnt = 0;
            while((line = br.readLine()) != null) {
                log.info("line => {}", line);
                if(cnt == 0) {
                    url = line.split(" ")[1].equals("/") ? "/index.html" : line.split(" ")[1];
                    cnt += 1;
                }
                if(line.contains("Content-Length")) {
                    contentLength = Integer.parseInt(line.split("\\:")[1].trim());
                    log.info("contentLength => {}", contentLength);
                }
                if(line.isEmpty()) {
                    if(contentLength != 0) {
                        params = IOUtils.readData(br, contentLength);
                        User user = SeparationUrl.getUserFromQueryString(params);
                        log.info("user => {}", user);
                    }
                    break;
                }
            }

            byte[] body = Files.readAllBytes(new File("./webapp" + url).toPath());
            response302Header(dos, body.length);
            responseBody(dos, body);

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 302 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}