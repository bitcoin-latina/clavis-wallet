package rpc;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.scene.control.Alert;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.UUID;

public class RPC {

    public static void rpc_call(String method, Params params) {
        //Create the json request object
        JSONObject jsonRequest = new JSONObject();
        try {
            jsonRequest.put("jsonrpc", "2.0");
            jsonRequest.put("method", method);
            jsonRequest.put("params", params.getJsonArray());
            jsonRequest.put("id", UUID.randomUUID().hashCode());
        } catch (JSONException e1) {
            System.err.println("Invalid Json Error");
        }
        System.out.println(jsonRequest.toString());
        sendRPC(jsonRequest);
    }

    private static String sendRPC(JSONObject j) {
        URL url = null;
        OutputStream out = null;
        try {
            url = new URL("http://127.0.0.1:8545");

            HttpURLConnection connection = null;
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.connect();
            out = connection.getOutputStream();

            out.write(j.toString().getBytes());
            out.flush();
            out.close();

            int statusCode = connection.getResponseCode();
            if (statusCode != HttpURLConnection.HTTP_OK) {
                System.err.println("Status is not okay");
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
            if(result!=null)
                createAlert(result.toString());
            if(error!=null)
                createErrorAlert(error.toString());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
