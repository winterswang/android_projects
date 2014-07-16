package com.example.newaa.lib;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;


public class NetWorkProcess {
    private  static  final String LOG_TAG = "NewWorkProcess";

    private static String mURL;
    private HttpURLConnection mHttpURLConnection;
    private Context mContext;

    NetWorkProcess(Context context, String url){
        mURL = url;
        mContext = context;
    }

    public boolean isNetConnected()
    {
        ConnectivityManager connMgr = (ConnectivityManager)
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){
            return true;
        }
        return false;
    }
   
    public String submitGetData(Map<String, String> map, String encode){
        String json = null;
        InputStream is = null;
        try{
            String getURL = mURL + getRequestData(map, encode).toString();
            Log.i(LOG_TAG, getURL);
            URL url = new URL(getURL);
            mHttpURLConnection = (HttpURLConnection)url.openConnection();

            //mHttpURLConnection.setReadTimeout(10000);
            mHttpURLConnection.setConnectTimeout(1000);
            mHttpURLConnection.setRequestMethod("GET");

            mHttpURLConnection.setDoInput(true);
            mHttpURLConnection.connect();
            int response  = mHttpURLConnection.getResponseCode();
            Log.i(LOG_TAG,"Response Code : " + response);
            if(response == 200){
                Log.i(LOG_TAG, "suceed");
                is = mHttpURLConnection.getInputStream();
                if(is != null){
                    byte[] data = readStream(is);
                    json = new String(data);
                    Log.i(LOG_TAG, json);
                    is.close();
                }
                else{
                    Log.i(LOG_TAG, "InputStream read null");
                }

            }
            else{
                Log.i(LOG_TAG, "netWork response error! " + "The response is " + response);
            }

        }catch (IOException e){
            Log.i(LOG_TAG,e.toString());
        }

        return json;
    }
    public  String submitPostData(List<NameValuePair> pairList, String encode){
        String json = null;
      /*  NameValuePair pair1 = new BasicNameValuePair("User[phonenumber]", "98");
        NameValuePair pair2 = new BasicNameValuePair("User[password]", "445");
        NameValuePair pair3 = new BasicNameValuePair("User[gender]","fdf");
        NameValuePair pair4 = new BasicNameValuePair("User[info]", "fasf");
        List<NameValuePair> pairList = new ArrayList<NameValuePair>();
        pairList.add(pair1);
        pairList.add(pair2);
        pairList.add(pair3);
        pairList.add(pair4);*/
        try
        {
            HttpEntity requestHttpEntity = new UrlEncodedFormEntity(
                    pairList);
           
            HttpPost httpPost = new HttpPost(mURL);
            
            httpPost.setEntity(requestHttpEntity);
            
            HttpClient httpClient = new DefaultHttpClient();

            HttpResponse response = httpClient.execute(httpPost);
            if(null == response){
                Log.i(LOG_TAG, "no response");
            } else {
                HttpEntity httpEntity = response.getEntity();
                InputStream inputStream = httpEntity.getContent();
                byte[] data = readStream(inputStream);
                json = new String(data);
                Log.i(LOG_TAG, json);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return json;
    }





    private static byte[] readStream(InputStream inputStream) throws IOException{
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            bout.write(buffer, 0, len);
        }
        bout.close();

        return bout.toByteArray();
    }



    public static StringBuffer getRequestData(Map<String, String> params, String encode) {
        StringBuffer stringBuffer = new StringBuffer();        //�???��??�?好�??请�??�?信�??
        try {
            for(Map.Entry<String, String> entry : params.entrySet()) {
                stringBuffer.append(URLEncoder.encode(entry.getKey(), encode))
                        .append("=")
                        .append(URLEncoder.encode(entry.getValue(), encode))
                        .append("&");
            }
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);    //?????��????????�?�?"&"
        } catch (Exception e) {
            Log.i(LOG_TAG,e.getMessage());
        }
        return stringBuffer;
    }
}
