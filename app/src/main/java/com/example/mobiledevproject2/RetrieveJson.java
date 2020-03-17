package com.example.mobiledevproject2;

import android.os.AsyncTask;

import com.loopj.android.http.HttpGet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class RetrieveJson extends AsyncTask<Integer, Void, JSONArray> {

    @Override
    protected JSONArray doInBackground(Integer... integers) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet("http://www.vizgr.org/historical-events/search.php?format=json&begin_date=20000000&end_date=20151231&lang=en");
        HttpResponse httpresponse;
        try{
            httpresponse = httpclient.execute(httpget);
            HttpEntity entity = httpresponse.getEntity();
            if(entity != null){
                InputStream instream = entity.getContent();
                String result = convertStreamToString(instream);
                JSONObject json = new JSONObject(result);
                JSONArray nameArray = json.names();
                JSONArray valArray = json.toJSONArray(nameArray);
                JSONObject json2 = valArray.getJSONObject(0);
                JSONArray nameArray2 = json2.names();
                JSONArray valArray2 = json2.toJSONArray(nameArray2);

                return valArray2;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JSONArray();
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
