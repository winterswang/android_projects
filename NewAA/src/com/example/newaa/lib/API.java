package com.example.newaa.lib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;


import android.content.Context;

public class API {
	private Context context;
	public API(){

	}
	public API(Context context){
		this.context = context;
	}
	/*
	*@param device_id(设备号),username(用户名),password(密码)
	*return map<r(状态码),msg(提示信息)> r = 0/1/2 成功，失败，已存在
	*/
	public Map LoginApi(String device_id,String username,String password){
        ApiHandle ah = new ApiHandle(context,Config.BASE_URL);	
        //post数据初始化
		List<NameValuePair> pairList = new ArrayList<NameValuePair>();
		pairList.add(new BasicNameValuePair("device_id", device_id));
		pairList.add(new BasicNameValuePair("username", username));
		pairList.add(new BasicNameValuePair("passwd", password));		
        return ah.userLogin(pairList);
	}
	
}