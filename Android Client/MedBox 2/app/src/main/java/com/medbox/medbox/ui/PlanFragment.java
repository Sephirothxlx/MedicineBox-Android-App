package com.medbox.medbox.ui;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.medbox.medbox.cache.CacheUtil;
import com.melnykov.fab.FloatingActionButton;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.medbox.medbox.R;
import com.medbox.medbox.model.Medicine;
import com.medbox.medbox.model.MedicineRecord;
import com.medbox.medbox.model.Patient;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by luzhoutao on 2017/3/2.
 */

public class PlanFragment extends Fragment {

    private SimpleAdapter adapter;
    private ArrayList<HashMap<String, Object>> list;
    private List<MedicineRecord> records_list;
    private Patient patient;
    private String cookie;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_plan, container, false);

        ListView listView = (ListView) view.findViewById(R.id.plan_list);
        list = new ArrayList<HashMap<String, Object>>();
        records_list = new ArrayList<MedicineRecord>();

        adapter = new SimpleAdapter(getActivity(),
                list, R.layout.plan_item,
                new String[]{"name", "interval", "period", "does"},
                new int[]{R.id.medicine_name, R.id.record_interval, R.id.record_period, R.id.record_does});

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MedicineRecord record = records_list.get(position);
                Intent intent = new Intent(getActivity(), PlanDetailActivity.class)
                        .putExtra("record", record);
                startActivity(intent);
            }
        });

//        //fot test
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        map.put("name", "三九感冒灵");
//        map.put("interval", "用药间隔：6小时一次");
//        map.put("period", "用药周期：30天");
//        map.put("does", "用药剂量：一次一包");
//        list.add(map);
//        map = new HashMap<String, Object>();
//        map.put("name", "板蓝根");
//        map.put("interval", "用药间隔：6小时一次");
//        map.put("period", "用药周期：20天");
//        map.put("does", "用药剂量：一次一包");
//        list.add(map);
//        map = new HashMap<String, Object>();
//        map.put("name", "阿莫西林");
//        map.put("interval", "用药间隔：8小时一次");
//        map.put("period", "用药周期：20天");
//        map.put("does", "用药剂量：一次两片");
//        list.add(map);
//
//        //for test
//        records_list.add(new MedicineRecord());

        CacheUtil cu=new CacheUtil();
        try{
            JSONObject j=new JSONObject(cu.readAccount(getActivity()));
            cookie=j.getString("cookie");
        }catch (JSONException e){
            e.printStackTrace();
        }
        new MyAsyncTask().execute(cookie);

        listView.setAdapter(adapter);
        setListViewHeightBasedOnChildren(listView);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.create_plan);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PlanAddingActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    // update all the plan items on the list
    public void updateList(List<MedicineRecord> plans) {
        ListView listView = (ListView) this.getView().findViewById(R.id.plan_list);

        this.records_list.clear();
        this.records_list.addAll(plans);

        list.clear();
        for (MedicineRecord record : plans) {
            list.add(record.getPlanItem());
        }

        setListViewHeightBasedOnChildren(listView);
    }

    private void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
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
                    .url(""+cookie)
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
            GsonBuilder builder = new GsonBuilder();
            // Register an adapter to manage the date types as long values
            GsonBuilder gsonBuilder = builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                    Date date = null;
                    try {
                        DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        date = dateFormat2.parse(json.getAsJsonPrimitive().getAsString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return date;
                }
            });

            Gson gson = builder.create();
            try {
                JSONArray jsonArray = new JSONArray(result);
                MedicineRecord temp=null;
                for (int i = 0; i < jsonArray.length(); i++) {       //一个循环代表一个对象
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    temp = gson.fromJson(jsonObject.toString(), MedicineRecord.class);
                    records_list.add(temp);
                    list.add(temp.getPlanItem());
                }

            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
