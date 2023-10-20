package util;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SeparationUrlTest {

    @Test
    public void URL_요청_분리() {
        String str = "GET /index.html HTTP/1.1";
        String[] tokens = str.split(" ");
        assertThat(tokens[1], is("/index.html"));
    }
}
