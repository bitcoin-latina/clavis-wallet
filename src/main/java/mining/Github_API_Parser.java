package mining;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import com.google.gson.JsonObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ui.Global;

public class Github_API_Parser {

    public static String getDownloadURL(String url){
        try {
            JSONObject json = readJsonFromUrl(url);
            JSONArray assets = json.getJSONArray("assets");
            if(Global.getOS().contains("win")){
                //windows
                for (int i=0; i<assets.length(); ++i) {
                    if(assets.getJSONObject(i).getString("name").toLowerCase().contains("window")){
                        return assets.getJSONObject(i).getString("browser_download_url");
                    }
                }
            }
            else if(Global.getOS().contains("mac")){
                //mac
                for (int i=0; i<assets.length(); ++i) {
                    if(assets.getJSONObject(i).getString("name").toLowerCase().contains("darwin")){
                        return assets.getJSONObject(i).getString("browser_download_url");
                    }
                }
            }
            else if(Global.getOS().contains("lin")){
                //mac
                for (int i=0; i<assets.length(); ++i) {
                    if(assets.getJSONObject(i).getString("name").toLowerCase().contains("linux")){
                        return assets.getJSONObject(i).getString("browser_download_url");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    private static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            return new JSONObject(jsonText);
        }
    }
}