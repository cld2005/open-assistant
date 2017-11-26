import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class Config {

    private static Config instance = null;

    int serverPort;

    private Config() {

        Properties properties = new Properties();
        InputStream inputStream = null;

        try {

            inputStream = new FileInputStream("config.properties");
            properties.load(inputStream);

            serverPort = Integer.parseInt(properties.getProperty("server_port"));

        } catch (IOException e) {

            e.printStackTrace();

        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * Singleton pattern is used to provide one shared configuration among different classes.
     * @return Configuration instance
     */
    static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

}
