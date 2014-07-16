package com.example.newaa.lib;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.json.JSONException;

import android.content.Context;
import android.util.Log;

public class ApiHandle {
	private Context mContext;
	private String baseURL; //访问接口的路径
	public ApiHandle(Context context, String url){
		mContext = context;
		baseURL = url;
	}
	/*
	*@ mDataMap(要post提交的map数据集)
	*return  Map<r,msg> 提交的返回状态和提示信息
	*/
	public Map<String,String> userLogin(List<NameValuePair> pairList)
	{		
		NetWorkProcess mNetWorkProcess = new NetWorkProcess(mContext,baseURL+"r=user/login&");
		String jsonStr = mNetWorkProcess.submitPostData(pairList, "UTF-8");
        try{
            JsonParse jsonParse = new JsonParse(jsonStr);
            //封装返回的数据信息
            Map<String,String> result = new HashMap();
            result.put("r",jsonParse.getStringByKey("result"));
            result.put("msg",jsonParse.getStringByKey("msg"));    
            return result;
        }catch (JSONException e){
            Log.i("JSON", e.toString());
            return null;
        }
	}	

}