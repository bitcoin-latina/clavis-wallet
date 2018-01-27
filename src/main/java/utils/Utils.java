package utils;

import org.apache.commons.io.FileUtils;
import ui.Global;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.util.Objects;

public class Utils {

    public static void set_os(String os){
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

    public static boolean Is_Empty_File(String path){
        File file = new File(Global.getPath()+path);
        if(!file.isDirectory() && file.exists()){
            return false;
        }else{
            return true;
        }
    }
    public static void export_resource(String from, String to){
        URL from_u = Utils.class.getClass().getResource(from);
        File to_u = new File(to);
        try {
            FileUtils.copyURLToFile(from_u, to_u);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static BigInteger conversion = BigInteger.valueOf(1000000000000000000l);
    public static String Wei_To_Eth(BigInteger wei){
        BigInteger eth = wei.divide(conversion);
        return eth.toString();
    }

    public static boolean Is_Empty_Directory(String path)
    {
        File file = new File(Global.getPath()+path);
        if(file.isDirectory()){
            if(Objects.requireNonNull(file.list()).length>0){
                return false;
            }else{
                return true;
            }

        }else{
            return true;
        }
    }
    public static String toHex(BigInteger b){
        return "0x"+b.toString(16);
    }
}
