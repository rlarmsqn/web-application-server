package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SeparationUrl {
    private static final Logger log = LoggerFactory.getLogger(SeparationUrl.class);
    public String splitUrl(String str) {
        log.info("str => {}", str);
        return str.split(" ")[1];
    }
}
