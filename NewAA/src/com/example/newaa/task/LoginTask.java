package com.example.newaa.task;

import java.util.Map;
import com.example.newaa.lib.API;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class LoginTask extends AsyncTask<String, Integer, Map<String,String>> {
	
	private Context context;
	public LoginTask(Context context){
		this.context = context;
	}

	@Override
	protected Map<String,String> doInBackground(String... params) {
		// TODO Auto-generated method stub
        API api = new API(this.context);
        return api.LoginApi(params[0], params[1], params[2]);
	}      
	@Override  
    protected void onPreExecute() {
		
	}
	@Override
	protected void onPostExecute(Map<String,String> result) { 
		Log.v("context", context.toString());
		Toast.makeText(context, "xxxxxxxx",Toast.LENGTH_LONG).show();	
		if(null == result){
			Log.v("login", "result is null");
		}else if(result.get("r") == "0")
		{
        	//注册成功
			Log.v("login", "successful");
        }else{
        	Log.v("login", result.get("msg"));
        }		
	}
}
