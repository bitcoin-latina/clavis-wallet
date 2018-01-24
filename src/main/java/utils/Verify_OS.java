package utils;

import ui.Global;

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
            Global.setOS("windows");
            Global.setPath(System.getProperty("user.home"));
        } else if (apple()) {
            Global.setOS("mac");
            Global.setPath(System.getProperty("user.home"));
        } else if (linux()) {
            Global.setOS("linux");
            Global.setPath(System.getProperty("user.home"));
        } else {
            //OS not supported
            System.exit(1);
        }
    }
}