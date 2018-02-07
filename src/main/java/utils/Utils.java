package utils;

import org.apache.commons.io.FileUtils;
import rpc.RPC;
import ui.Global;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Logger;

public class Utils {
    private static final Logger LOGGER = Logger.getLogger(Utils.class.getName());

    public static void set_os(String os) {
        LOGGER.addHandler(Global.getLog_fh());
        LOGGER.info("Setting UP Operation System");
        if (os.contains("win")) {
            Global.setOS("windows");
            Global.setPath(System.getProperty("user.home"));
        } else if (os.contains("mac")) {
            Global.setOS("mac");
            Global.setPath(System.getProperty("user.home"));
        } else {
            //OS not supported
            System.exit(1);
        }
    }

    public static boolean Is_Empty_File(String path) {
        File file = new File(Global.getPath() + path);
        if (!file.isDirectory() && file.exists()) {
            return false;
        } else {
            return true;
        }
    }
    public static void export_resource(String from, String to){
        // this is the path within the jar file
        InputStream input = Utils.class.getResourceAsStream("/resources/" + from);
        if (input == null) {
            // this is how we load file within editor (eg eclipse)
            input = Utils.class.getClassLoader().getResourceAsStream(from);
        }
        try {
            Files.copy(input, Paths.get(to));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//    public static void export_resource(String from, String to) {
//        LOGGER.addHandler(Global.getLog_fh());
//        URL from_u = Utils.class.getResource(File.separator+from);
//        File to_u = new File(to);
//        LOGGER.warning("\n\nFrom U "+ File.separator+from_u + "\n\n");
//        try {
//            FileUtils.copyURLToFile(from_u, to_u);
//        } catch (IOException e) {
//            LOGGER.warning("UNABLE TO EXPORT " + from_u + "\n\n" + Arrays.toString(e.getStackTrace()));
//            e.printStackTrace();
//        }
//    }

    static BigInteger conversion = BigInteger.valueOf(1000000000000000000l);

    public static String Wei_To_Eth(BigInteger wei) {
        BigInteger eth = wei.divide(conversion);
        return eth.toString();
    }

    public static boolean Is_Empty_Directory(String path) {
        LOGGER.addHandler(Global.getLog_fh());
        File file = new File(Global.getPath() + path);
        if (file.isDirectory()) {
            return Objects.requireNonNull(file.list()).length <= 0;
        } else {
            return true;
        }
    }

    public static String toHex(BigInteger b) {
        return "0x" + b.toString(16);
    }
}
