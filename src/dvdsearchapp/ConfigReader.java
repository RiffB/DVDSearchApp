/* Riff Ahmad Bazlee
 * P465225
 * 22.05.2019
 * Software Deployment Project
 */
package dvdsearchapp;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ConfigReader {

    InputStream inputStream;
    Properties prop;
    String propFileName;
    String path;

    public ConfigReader() throws IOException {

        try {
            prop = new Properties();
            propFileName = "/config.properties";

            File jarPath = new File(ConfigReader.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            String propertiesPath = jarPath.getParentFile().getAbsolutePath();

            path = propertiesPath + propFileName;
            path = path.replaceAll("%20", " ");//replace %20 with spaces

            inputStream = new FileInputStream(path);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found.");
            }

        } catch (IOException ex) {
            System.out.println("Exception: " + ex);
        }

    }

    public String getURL() {
        return prop.getProperty("url");
    }

    public String getUser() {
        return prop.getProperty("user");
    }

    public String getPass() {
        return prop.getProperty("pass");
    }

}
