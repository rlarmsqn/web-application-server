package util;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class SeparationUrl {
    private static final Logger log = LoggerFactory.getLogger(SeparationUrl.class);
    public static String splitUrl(String str) {
        return str.split(" ")[1];
    }

    public static User getUserFromQueryString(String queryString) {
        String[] splitParams = queryString.split("&");
        log.info("splitParams => {}", Arrays.toString(splitParams));
        String id = splitParams[0].split("=")[1];
        String pw = splitParams[1].split("=")[1];
        String name = null;
        String email = null;
        if(splitParams.length > 2) {
            if (splitParams[2] != null) {
                name = splitParams[2].split("=")[1];
            }

            if (splitParams[3] != null) {
                email = splitParams[3].split("=")[1];
            }
        }

        return new User(id, pw, name, email);
    }
}