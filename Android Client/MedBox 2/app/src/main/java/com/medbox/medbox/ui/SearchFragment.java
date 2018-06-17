package com.medbox.medbox.ui;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.medbox.medbox.R;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Sephiroth on 17/3/9.
 */

public class SearchFragment extends Fragment {
    private LinearLayout qrcode;
    private ListView l;
    private SimpleAdapter adapter;
    private ArrayList<HashMap<String,String>> al;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view=inflater.inflate(R.layout.fragment_search, container, false);
//        qrcode=(LinearLayout)view.findViewById(R.id.qrcode_search);
//        qrcode.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(getActivity(),QRSearchActivity.class);
//                startActivity(intent);
//            }
//        });

        l=(ListView)view.findViewById(R.id.search_listview);

        return view;
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
            //创建一个Request
            final Request request = new Request.Builder()
                    .url("http://baidu.com")
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

        }
    }
}
