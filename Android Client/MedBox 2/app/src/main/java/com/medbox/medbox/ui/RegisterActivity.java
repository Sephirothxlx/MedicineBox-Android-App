package com.medbox.medbox.ui;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.medbox.medbox.R;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.InputStream;

/**
 * Created by Sephiroth on 17/3/10.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    // 登陆按钮
    private Button registerbutton;
    // 调试文本，注册文本
    // 显示用户名和密码
    EditText username, password, name, age;
    RadioGroup gender;
    String gender_str;
    // 创建等待框
    private ProgressDialog dialog;

    private String url;

    private static Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // 获取控件
        username = (EditText) findViewById(R.id.register_email);
        password = (EditText) findViewById(R.id.register_password);
        name = (EditText) findViewById(R.id.register_name);
        age = (EditText) findViewById(R.id.register_age);
        gender = (RadioGroup) findViewById(R.id.register_gender);
        gender_str = ((RadioButton) findViewById(gender.getCheckedRadioButtonId())).getText().toString();

        registerbutton = (Button) findViewById(R.id.register_registerbutton);
        // 设置按钮监听器
        registerbutton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String[] params = new String[5];
        params[0] = username.getText().toString();
        params[1] = password.getText().toString();
        params[2] = name.getText().toString();
        params[3] = age.getText().toString();
        params[4] = gender_str;
        if (params[0].equals("") || params[1].equals("") || params[2].equals("") || params[3].equals("") || params[4].equals("")) {
            Toast.makeText(RegisterActivity.this, "Information incomplete!", Toast.LENGTH_LONG).show();
        } else {
            dialog = new ProgressDialog(this);
            dialog.setTitle("提示");
            dialog.setMessage("正在注册，请稍后...");
            dialog.setCancelable(true);
            dialog.show();
            new MyAsyncTask().execute(params);
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
            //way 1
//            MediaType JSON = MediaType.parse("application/json; charset=utf-8");//数据类型为json格式，
//            String jsonContent = "{\"username\":\"lisi\",\"nickname\":\"李四\"}";//json数据.
//            RequestBody rb=RequestBody.create(JSON,jsonContent);

            //way 2
            RequestBody rb = new FormEncodingBuilder()
                    .add("username", params[0])
                    .add("password", params[1])
                    .add("name", params[2])
                    .add("age", params[3])
                    .add("gender", params[4])
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
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dialog.dismiss();
            if (result.equals("1")) {
                Toast.makeText(RegisterActivity.this, "Register successfully!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_LONG).show();
            }
        }
    }
}