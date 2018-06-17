package com.medbox.medbox.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.medbox.medbox.MainActivity;
import com.medbox.medbox.R;
import com.medbox.medbox.cache.CacheUtil;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Sephiroth on 17/3/10.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    // 登陆按钮
    private Button loginbutton;
    private Button registbutton;
    // 调试文本，注册文本
    // 显示用户名和密码
    private EditText username, password;
    // 创建等待框
    private ProgressDialog dialog;
    private ArrayList<HashMap<String, String>> info;
    private ArrayList<HashMap<String, String>> data0;
    private MyAsyncTask ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 获取控件
        username = (EditText) findViewById(R.id.login_email);
        password = (EditText) findViewById(R.id.login_password);
        loginbutton = (Button) findViewById(R.id.login_loginbutton);
        registbutton = (Button) findViewById(R.id.login_registerbutton);
        // 设置按钮监听器
        loginbutton.setOnClickListener(this);
        registbutton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_loginbutton:
                String username_str = username.getText().toString();
                String password_str = password.getText().toString();
                if (username_str.equals("") || password_str.equals(""))
                    Toast.makeText(LoginActivity.this, "Information incomplete!", Toast.LENGTH_LONG).show();
                else {
                    // 提示框
                    dialog = new ProgressDialog(this);
                    dialog.setTitle("提示");
                    dialog.setMessage("正在登陆，请稍后...");
                    dialog.setCancelable(true);
                    dialog.show();
                    ma = new MyAsyncTask();
                    ma.execute(new String[]{username_str, password_str});
                    //you have to finish this activity
                    finish();
                }
                break;
            case R.id.login_registerbutton:
                Intent registerActivity = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerActivity);
                break;
        }
    }

    //onPause destroy aysntask in case that memory leaks
    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            //true: thread will be set to cancelled for your manual check
            ma.cancel(true);
        }

    }

    public class MyAsyncTask extends AsyncTask<String, Integer, String> {

        private InputStream is = null;
        private String responseData = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient();
            RequestBody rb = new FormEncodingBuilder()
                    .add("username", params[0])
                    .add("password", params[1])
                    .build();
            //创建一个Request
            final Request request = new Request.Builder()
                    .url("")
                    .post(rb)
                    .build();
            Response response = null;
            try {
                response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    responseData = response.body().string();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return responseData;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //no progressbar
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //进行存入cookie等操作
            dialog.dismiss();
            try {
                JSONObject j = new JSONObject(result);
                if (j.get("ifSuccessful").toString().equals("1")) {
                    CacheUtil cu=new CacheUtil();
                    cu.writeJson(getApplicationContext(),j.toString(),"account",false);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Login Failed!", Toast.LENGTH_LONG);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(LoginActivity.this, "Login Failed!", Toast.LENGTH_LONG);
            }

        }

        //oncancelled if need do something special please override it
        @Override
        protected void onCancelled() {
            super.onCancelled();
            dialog.dismiss();
        }
    }
}
