package utils;

/**
 * Class to verify operating system
 */
public class Verify_OS {

    private static String OS = System.getProperty("os.name").toLowerCase();

    private static boolean linux() {
        return (OS.contains("nix") || OS.contains("aix") || OS.contains("nux"));
    }

    private static boolean microsoft() {
        return (OS.contains("win"));
    }

    private static boolean apple() {
        return (OS.contains("mac"));
    }
    
    public static void set_os(){
        if (microsoft()) {
            Sys.setOS("windows");
            Sys.setPath(System.getProperty("user.home"));
        } else if (apple()) {
            Sys.setOS("mac");
            Sys.setPath(System.getProperty("user.home"));
        } else if (linux()) {
            Sys.setOS("linux");
            Sys.setPath(System.getProperty("user.home"));
        } else {
            //OS not supported
            System.exit(1);
        }
    }
}