package io.github.jylab.otplib.server;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Map;


public class AsyncOTPRequest extends AsyncTask<String, Void, String> {

    public static String cookie;

    @Override
    protected String doInBackground(String... arg0) {
        try {
            HttpURLConnection conn = null;
            URL url = new URL(arg0[0]);

            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            cookie = getCookie(conn);
            String token = br.readLine();

            return token;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }


    public String getCookie(HttpURLConnection conn){
        String cookies = "";
        Map<String, List<String>> imap = conn.getHeaderFields( ) ;
        if( imap.containsKey( "Set-Cookie" ) )
        {
            List<String> lString = imap.get( "Set-Cookie" ) ;

            for( int i = 0 ; i < lString.size() ; i++ ) {
                cookies += lString.get( i ) ;
            }
        }
        return cookies;
    }


    @Override
    protected void onPostExecute(String otp){

        try {

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
