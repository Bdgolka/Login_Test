package com.bdglka.login_test;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/* http://www.tutorialspoint.com/android/android_login_screen.htm */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    Button loginButton, button1;
    EditText editText1, editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        loginButton = (Button)findViewById(R.id.loginButton);

        loginButton.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        String login = "Nikolas";
        String password = "Nikolas";
        String guid = "4613948b-80c2-4cc9-b406-2fd1ab4c87ff";

        String data = login+";"+password+";"+guid;

        postData(data);
        //new SendPostReqAsynkTask().doInBackground(data);
        //sendPostRequest(login, password, guid);
    }


    class  SendPostReqAsynkTask extends AsyncTask <String, Void, String>{

    @Override
    protected String doInBackground(String... params) {
        postData(params[0]);
        return null;
    }
}

    public void postData (String data){
    //Define URL where to  send data

        String text = "";
        BufferedReader reader = null;

        try {
            URL url = new URL("http://webapigpsmobilegroup.cloudapp.net/");
            //send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            //Read Server Response
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;

            //Read Server Response
            while((line=reader.readLine())!=null){
                //Append server responce in string
                sb.append(line+"\n");
            }
            text = sb.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            /*try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }*/

            }
        switch(text){
            case"OK":
                Toast.makeText(getApplicationContext(), "Successful!",Toast.LENGTH_LONG).show();
                break;
            case"Conflict":
                Toast.makeText(getApplicationContext(), "User already exist",Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(getApplicationContext(), "Check connection",Toast.LENGTH_LONG).show();

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
