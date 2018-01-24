package utils;

public class Sys {
    private static String path = "";
    private static String OS = "";
    private static int state =1; //0 is infant //1 is initialized

    public static int getState() {
        return state;
    }

    public static void setState(int state) {
        Sys.state = state;
    }

    public static String getPath() {
        return path;
    }

    public static void setPath(String path) {
        Sys.path = path;
    }

    public static String getOS() {
        return OS;
    }

    public static void setOS(String OS) {
        Sys.OS = OS;
    }


}
