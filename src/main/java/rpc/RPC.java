package rpc;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.scene.control.Alert;
import org.json.JSONException;
import org.json.JSONObject;
import ui.Global;
import ui.Init;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Arrays;
import java.util.UUID;
import java.util.logging.Logger;

public class RPC {
    private static final Logger LOGGER = Logger.getLogger(RPC.class.getName());
    public static String rpc_call(String method, Params params, Boolean popups) {
        LOGGER.addHandler(Global.getLog_fh());
        LOGGER.info("Attempting RPC Call");
        //Create the json request object
        JSONObject jsonRequest = new JSONObject();
        try {
            jsonRequest.put("jsonrpc", "2.0");
            jsonRequest.put("method", method);
            jsonRequest.put("params", params.getJsonArray());
            jsonRequest.put("id", UUID.randomUUID().hashCode());
        } catch (JSONException e1) {
            LOGGER.warning("INVALID JSON ERROR");
        }
        return sendRPC(jsonRequest, popups);
    }

    //TODO handle "ERROR" return
    private static String sendRPC(JSONObject j, Boolean popups) {
        URL url;
        OutputStream out = null;
        try {
            url = new URL("http://127.0.0.1:8545");
            HttpURLConnection connection;
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "application/json");
            connection.setDoOutput(true);
            connection.connect();
            out = connection.getOutputStream();

            out.write(j.toString().getBytes());
            out.flush();
            out.close();

            int statusCode = connection.getResponseCode();
            if (statusCode != HttpURLConnection.HTTP_OK) {
                createAlert("Status is not okay STATUS CODE:"+ statusCode);
            }

            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder response = new StringBuilder();
            String line;
            while((line = reader.readLine()) != null) {
                response.append(line);
            }

            JsonParser parser = new JsonParser();
            JsonObject resp = (JsonObject) parser.parse(new StringReader(response.toString()));

            JsonElement result = resp.get("result");
            JsonElement error = resp.get("error");
            if(popups) {
                if (result != null)
                    createAlert(result.toString());
                if (error != null)
                    createErrorAlert(error.toString());
            }
            assert result != null;
            return result.toString();
        } catch (IOException e) {
            LOGGER.warning("COULDN'T COMPLETE RPC CALL \n\n"+ Arrays.toString(e.getStackTrace()));
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return "ERROR";
    }
    private static void createErrorAlert(String s) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(s);
        alert.setContentText("Try Again");
        alert.show();
    }

    private static void createAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setHeaderText(message);
        alert.show();
    }
}
