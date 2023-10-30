package webserver;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.IOUtils;
import util.SeparationUrl;

import javax.xml.crypto.Data;

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
            String status = null;
            int cnt = 0;
            boolean login = false;
            int contentLength = 0;

            while(!(line = br.readLine()).isEmpty()) {
                log.info("line => {}", line);
                if(cnt == 0) {
                    url = line.split(" ")[1].equals("/") ? "/index.html" : line.split(" ")[1];
                    cnt += 1;
                }
                if(line.contains("Content-Length")) {
                    contentLength = Integer.parseInt(line.split(":")[1].trim());
                }
            }

            log.info("contentLength => {}", contentLength);
            byte[] body;

            if(url.equals("/user/create")) {
                status = "302";
                body = Files.readAllBytes(new File("./webapp/index.html").toPath());
                if(contentLength != 0) {
                    String content = IOUtils.readData(br, contentLength);
                    User user = SeparationUrl.getUserFromQueryString(content);
                    DataBase.addUser(user);
                }
                url = "/index.html";
            } else if(url.equals("/user/login")) {
                String content = IOUtils.readData(br, contentLength);
                User user = SeparationUrl.getUserFromQueryString(content);
                User loginUser = DataBase.findUserById(user.getUserId());
                if(user.getUserId().equals(loginUser.getUserId()) && user.getPassword().equals(loginUser.getPassword())) {
                    status = "200";
                    body = Files.readAllBytes(new File("./webapp/index.html").toPath());
                    responseLoginSuccessHeader(dos);
                    url = "/index.html";
                } else {
                    status = "400";
                    body = Files.readAllBytes(new File("./webapp/user/login_failed.html").toPath());
                    responseResource(out, "/user/login_failed.html");
                }
            } else {
                status = "200";
                body = Files.readAllBytes(new File("./webapp" + url).toPath());
            }

            responseHeader(dos, body.length, status, url);
            responseBody(dos, body);

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseHeader(DataOutputStream dos, int lengthOfBodyContent, String status, String url) {
        try {
            if(!status.equals("302")) {
                dos.writeBytes("HTTP/1.1 " + status + " OK \r\n");
                dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
                dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
                dos.writeBytes("\r\n");
            } else {
                dos.writeBytes("HTTP/1.1 302 Redirect \r\n");
                dos.writeBytes("Location: " + url + " \r\n");
                dos.writeBytes("\r\n");
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseLoginSuccessHeader(DataOutputStream dos) {
        try {
            dos.writeBytes("HTTP/1.1 302 Redirect \r\n");
            dos.writeBytes("Set-Cookie: logined=true \r\n");
            dos.writeBytes("Location: /index.html \r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseResource(OutputStream out, String url) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        byte[] body = Files.readAllBytes(new File("./webapp" + url).toPath());
        responseHeader(dos, body.length, "200", url);
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