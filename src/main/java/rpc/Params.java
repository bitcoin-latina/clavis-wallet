package rpc;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Params {
    public List<String> fields = new ArrayList<>();
    public List<String> values = new ArrayList<>();
    public int length = 0;

    public void addParam(String field, String value) {
        fields.add(field);
        values.add(value);
        ++length;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public JSONArray getJsonArray() {
        JSONObject params = new JSONObject();
        for (int i = 0; i < fields.size(); ++i) {
            params.put(fields.get(i), values.get(i));
        }
        JSONArray jsonParams = new JSONArray();
        jsonParams.put(params);
        return jsonParams;
    }
}
