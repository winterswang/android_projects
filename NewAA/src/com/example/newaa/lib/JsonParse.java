package com.example.newaa.lib;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonParse {
    private final String mStrJson;
    private JSONObject mJsonObject;

    public JsonParse(String string) throws JSONException{
        mStrJson = string;
        mJsonObject = new JSONObject(mStrJson);
    }

    public String getStringByKey (String key) throws JSONException{
        String value = null;
        value = mJsonObject.getString(key);
        return value;
    }
    
    public String getStringByKeys(String key_1, String key_2)throws JSONException{
    	String result = null;
    	JSONObject contentObj = mJsonObject.getJSONObject(key_1);
        result = contentObj.getString(key_2);
        return result;
    }

    public int getIntByKey(String key) throws JSONException{
        int value = -1;
        value = mJsonObject.getInt(key);
        return value;
    }

    public JSONObject getJsonObjectByKey(String key) throws JSONException{
        JSONObject contentObj = null;
        contentObj = mJsonObject.getJSONObject(key);
        return contentObj;
    }

    public List<String> getListByKey(String arrayKey, String key) throws JSONException{
        JSONArray contentArray = mJsonObject.getJSONArray(arrayKey);
        List<String> list = new ArrayList<String>();
        for(int i = 0; i < contentArray.length(); i++){
            JSONObject item = contentArray.getJSONObject(i);
            String string = item.getString(key);
            list.add(string);
        }

        return list;
    }

    public List<Map<String, Object>> getListMapByKey(String arrayKey,List<String> keys)
        throws JSONException{
        JSONArray contentArray = mJsonObject.getJSONArray(arrayKey);
        HashMap<String, Object> map;
        List<Map<String, Object> > list = new ArrayList<Map<String, Object>>();
        for(int i = 0; i < contentArray.length(); i++){
            JSONObject item = contentArray.getJSONObject(i);
            map = new HashMap<String, Object>();
            for(int j = 0; j < keys.size(); j++){
                String value = item.getString(keys.get(j));
                map.put(keys.get(j), value);
            }
            list.add(map);
        }

        return list;
    }
}
