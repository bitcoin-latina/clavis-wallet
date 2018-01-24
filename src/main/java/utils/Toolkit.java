package utils;

import ui.Global;

import java.io.File;
import java.util.Objects;

public class Toolkit {
    public static boolean Is_Empty_File(String path){
        File file = new File(Global.getPath()+path);
        if(!file.isDirectory() && file.exists()){
            return false;
        }else{
            return true;
        }
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
}

