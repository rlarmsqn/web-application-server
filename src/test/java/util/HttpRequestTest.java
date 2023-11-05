package util;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.http.HttpRequest;

public class HttpRequestTest {
    private String testDirectory = "./src/test/resources/";

    @Test
    public void request_GET() throws Exception {
        InputStream in = new FileInputStream(new File(testDirectory + "Http_GET.txt"));
    }
}
