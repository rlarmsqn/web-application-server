package util;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SeparationUrl {
    private static final Logger log = LoggerFactory.getLogger(SeparationUrl.class);
    public static String splitUrl(String str) {
        return str.split(" ")[1];
    }

    public static User getUserFromQueryString(String queryString) {
        String[] splitParams = queryString.split("&");

        return new User(splitParams[0].split("=")[1], splitParams[1].split("=")[1], splitParams[2].split("=")[1]
                ,splitParams[3].split("=")[1]);
    }
}