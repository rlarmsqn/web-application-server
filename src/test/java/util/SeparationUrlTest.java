package util;

import db.DataBase;
import model.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SeparationUrlTest {

    private static final Logger log = LoggerFactory.getLogger(SeparationUrlTest.class);

    @Test
    public void URL_요청_분리() {
        String str = "GET /index.html HTTP/1.1";
        String[] tokens = str.split(" ");
        assertThat(tokens[1], is("/index.html"));
    }

    @Test
    public void 쿼리스트링_분리() {
        String queryString = "GET /user/create?userId=boo&password=123&name=%EA%B9%80%EA%B7%BC%EB%B6%80&email=srmsqn%40naver.com HTTP/1.1";
        String one = queryString.split(" ")[1];
        String[] two = one.split("\\?");
        String url = two[0];
        String params = two[1];

        String[] splitParams = params.split("&");
        User user = new User(splitParams[0].split("=")[1], splitParams[1].split("=")[1], splitParams[2].split("=")[1]
                            ,splitParams[3].split("=")[1]);

        log.info("user => {}", user);

    }
}
