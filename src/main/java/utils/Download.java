package utils;

import mining.Github_API_Parser;
import org.apache.commons.io.FileUtils;
import org.rauschig.jarchivelib.*;
import ui.Global;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Logger;

public class Download {
    private static final Logger LOGGER = Logger.getLogger(Download.class.getName());
    final private static String ETHMINER = "https://api.github.com/repos/ethereum-mining/ethminer/releases/latest";

    public static void ethminer() throws IOException {
        String filename = null;
        String archivename = null;
        if (Global.getOS().contains("win")) {
            download("ethminer.zip", Github_API_Parser.getDownloadURL(ETHMINER));
            archivename = "ethminer.zip";
            unwrap(new File(Global.getPath() + File.separator + archivename),
                    new File(Global.getPath() + File.separator + "ethminerfol"));
            filename = "ethminer.exe";
        } else if (Global.getOS().contains("mac")) {
            archivename = "ethminer.tar.gz";
            download(archivename, Github_API_Parser.getDownloadURL(ETHMINER));
            unwrap(new File(Global.getPath() + File.separator + archivename),
                    new File(Global.getPath() + File.separator + "ethminerfol"));
            filename = "ethminer";
        }
        //Copy Binary To BCL_CL
            File ethminer = new File(Global.getPath() + File.separator + "ethminerfol" + File.separator + "bin" + File.separator + filename);
            ethminer.renameTo(new File(Global.getPath() + File.separator + filename));
            //Cleanup Uneeded Files
            FileUtils.forceDelete(new File(Global.getPath() + File.separator + "ethminerfol"));
            FileUtils.forceDelete(new File(Global.getPath() + File.separator + archivename));
    }

    private static void geth() {
        //TODO
    }

    private static void unwrap(File a, File o) throws IOException {
        Archiver compressor = ArchiverFactory.createArchiver(a);
            compressor.extract(a, o);
    }

    private static void download(String filename, String download_url) throws IOException {
        LOGGER.addHandler(Global.getLog_fh());
        LOGGER.info("Download String Provided" + download_url);
        URL url;
        URLConnection con;
        DataInputStream dis;
        FileOutputStream fos;
        byte[] fileData;
            url = new URL(download_url); //File Location goes here
            con = url.openConnection(); // open the url connection.
            dis = new DataInputStream(con.getInputStream());
            fileData = new byte[con.getContentLength()];
            for (int q = 0; q < fileData.length; q++) {
                fileData[q] = dis.readByte();
            }
            dis.close(); // close the data input stream
            fos = new FileOutputStream(new File(Global.getPath() + File.separator + filename));
            fos.write(fileData);  // write out the file we want to save.
            fos.close(); // close the output stream writer
    }
}
