package com.medbox.medbox.ui;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.medbox.medbox.R;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.Inflater;

/**
 * Created by luzhoutao on 2017/3/2.
 */

public class FamilyFragment extends Fragment {

    private ListView listView;
    private SimpleAdapter adapter;
    private ArrayList<HashMap<String, Object>> data0 = new ArrayList();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.fragment_family, container, false);
        listView=(ListView)view.findViewById(R.id.family_listview);
        HashMap<String, Object> hm = new HashMap();
        hm.put("name", "father");
        hm.put("account", "100000");
        data0.add(hm);
        adapter = new SimpleAdapter(getActivity(), data0, R.layout.family_item, new String[]{"name", "account"}, new int[]{R.id.family_name, R.id.family_account});
        listView.setAdapter(adapter);
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
