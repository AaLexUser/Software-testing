package utils;

import java.net.URI;
import java.net.URISyntaxException;

public class PrettifyUrl {
    public static String prettify(String url) throws URISyntaxException {
        URI tempUri = new URI(url);
        return  tempUri.getScheme() + "://" + tempUri.getHost() + tempUri.getPath();
    }
}
