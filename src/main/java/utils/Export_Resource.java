package utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Exporting files from resources folder within JAR
 */
public class Export_Resource{
    public Export_Resource(String from, String to) throws IOException {
        URL from_u = getClass().getResource(from);
        File to_u = new File(to);
        FileUtils.copyURLToFile(from_u, to_u);
    }
}