package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Credentials {
    private final String email;
    private final String password;

    public Credentials() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("credentials.properties")) {
            Properties properties = new Properties();
            properties.load(is);
            email = properties.getProperty("email");
            password = properties.getProperty("password");
        } catch ( IOException e) {
            throw new RuntimeException("Failed to load credentials", e);
        }
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
