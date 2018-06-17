package com.medbox.medbox.ui;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.medbox.medbox.R;
import com.medbox.medbox.cache.CacheUtil;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A login screen that offers login via email/password.
 */
public class PlanAddingActivity extends AppCompatActivity{

    private TextView start_result;
    private Button button_start;
    private Button button_end;
    private TextView end_result;

    private EditText medname;
    private EditText interval;

    private Button add;

    private int year = 2017;
    private int month = 1;
    private int day = 1;

    private String cookie;

    static final int DATE_DIALOG_ID = 999;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_adding);

        button_start = (Button) findViewById(R.id.addplan_select_start_date);
        start_result = (TextView) findViewById(R.id.addplan_show_start_date);
        button_end = (Button) findViewById(R.id.addplan_select_end_date);
        end_result = (TextView) findViewById(R.id.addplan_show_end_date);

        medname=(EditText)findViewById(R.id.plan_add_medname);
        interval=(EditText)findViewById(R.id.plan_add_interval);

        add=(Button)findViewById(R.id.addplan_ok_button);

        button_start.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                /**
                 * 实例化一个DatePickerDialog的对象
                 * 第二个参数是一个DatePickerDialog.OnDateSetListener匿名内部类，当用户选择好日期点击done会调用里面的onDateSet方法
                 */
                DatePickerDialog datePickerDialog = new DatePickerDialog(PlanAddingActivity.this, new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth)
                    {
                       start_result.setText(year + "年 " + (monthOfYear + 1) + "月 " + dayOfMonth+"日");
                    }
                }, year, month, day);

                datePickerDialog.show();
            }
        });

        button_end.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                /**
                 * 实例化一个DatePickerDialog的对象
                 * 第二个参数是一个DatePickerDialog.OnDateSetListener匿名内部类，当用户选择好日期点击done会调用里面的onDateSet方法
                 */
                DatePickerDialog datePickerDialog = new DatePickerDialog(PlanAddingActivity.this, new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth)
                    {
                        end_result.setText(year + "年 " + (monthOfYear + 1) + "月 " + dayOfMonth+"日");
                    }
                }, year, month, day);

                datePickerDialog.show();
            }
        });
        ActionBar mActionBar=getSupportActionBar();
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle("Add Plan");

        CacheUtil cu=new CacheUtil();
        try{
            JSONObject j=new JSONObject(cu.readAccount(this));
            cookie=j.getString("cookie");
        }catch (JSONException e){
            e.printStackTrace();
        }

        new MyAsyncTask().execute(new String[]{cookie,medname.getText().toString(),
                start_result.getText().toString(),end_result.getText().toString(),interval.getText().toString()});
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    protected Dialog onCreateDialog(int id) {
//        switch (id) {
//            case DATE_DIALOG_ID:
//                // set date picker as current date
//                return new DatePickerDialog(this, datePickerListener,
//                        year, month,day);
//        }
//        return null;
//    }
//
//    private DatePickerDialog.OnDateSetListener datePickerListener
//            = new DatePickerDialog.OnDateSetListener() {
//
//        // when dialog box is closed, below method will be called.
//        public void onDateSet(DatePicker view, int selectedYear,
//                              int selectedMonth, int selectedDay) {
//            year = selectedYear;
//            month = selectedMonth;
//            day = selectedDay;
//
//            // set selected date into textview
//            dpResult.setText(new StringBuilder().append(month + 1)
//                    .append("-").append(day).append("-").append(year)
//                    .append(" "));
//        }
//    };

public class MyAsyncTask extends AsyncTask<String, Integer, String> {

    private String responseData = null;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder fb=new FormBody.Builder();
        fb.add("cookie",params[0]);
        fb.add("medname",params[1]);
        fb.add("begin_time",params[2]);
        fb.add("end_time",params[3]);
        fb.add("interval",params[4]);
        //创建一个Request
        final Request request = new Request.Builder()
                .url(""+cookie)
                .post(fb.build())
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
        if(result.equals("1")){
            Toast.makeText(PlanAddingActivity.this,"Add sucessfully!",Toast.LENGTH_LONG);
        }else {
            Toast.makeText(PlanAddingActivity.this, "Adding failed!", Toast.LENGTH_LONG);
        }
    }

    //oncancelled if need do something special please override it
    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}

}

