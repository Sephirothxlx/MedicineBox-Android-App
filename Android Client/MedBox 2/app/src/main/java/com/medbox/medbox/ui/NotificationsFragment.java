package com.medbox.medbox.ui;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.medbox.medbox.R;
import com.medbox.medbox.cache.CacheUtil;
import com.medbox.medbox.model.MedicineNotification;
import com.medbox.medbox.ui.util.MyProgressDialog;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Sephiroth on 17/3/4.
 */
public class NotificationsFragment extends Fragment {

    private SwipeRefreshLayout swiper;
    private ListView listView;
    private MyProgressDialog dialog;
    private int processTag;
    private SimpleAdapter adapter;
    private ArrayList<HashMap<String, Object>> data0 = new ArrayList();
    private int itemTag = -1;
    private Listener2 listener2 = new Listener2();
    private JSONObject ac;
    private String cookie;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        //初始化SwipeRefreshLayout
        swiper = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        //为SwipeRefreshLayout设置监听事件
        swiper.setOnRefreshListener(listener2);
        //为SwipeRefreshLayout设置刷新时的颜色变化，最多可以设置4种
        swiper.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        listView = (ListView) view.findViewById(R.id.listview);
        listView.setOnItemClickListener(new Listener0());
        dialog = new MyProgressDialog(getActivity(), R.style.MyDialog);

        swiper.post(new Runnable() {
            @Override
            public void run() {
                swiper.setRefreshing(true);
            }
        });
        listener2.onRefresh();

        CacheUtil cu = new CacheUtil();
        try {
            ac = new JSONObject(cu.readAccount(getActivity()));
            cookie = ac.getString("cookie");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }

    public class Listener0 implements AdapterView.OnItemClickListener {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            HashMap item = (HashMap) parent.getItemAtPosition(position);
            String hiden = String.valueOf(item.get("hiden").toString());
            String time = String.valueOf(item.get("time").toString());
            String medicine = String.valueOf(item.get("medicine").toString());
            String status = String.valueOf(item.get("status").toString());
            Intent intent = new Intent(getActivity(), NotificationItemActivity.class);
            intent.putExtra("notification", hiden);
            intent.putExtra("time", time);
            intent.putExtra("medicine", medicine);
            intent.putExtra("status", status);
            intent.putExtra("tag", "0");
            startActivity(intent);
        }
    }


    public class Listener2 implements SwipeRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    processTag = 1;
                    new MyAsyncTask().execute(cookie);
                    swiper.setRefreshing(false);
                }
            });
        }
    }

    public class MyAsyncTask extends AsyncTask<String, Integer, String> {

        private InputStream is = null;
        private String responseData = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            if (processTag == 0)
//                dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient();
            //创建一个Request
            final Request request = new Request.Builder()
                    .url("http://192.168.1.107:8080/notification/ask/" + cookie)
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
            processTag = 0;

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
                //转换成json数据处理
//                JSONArray jsonArray = new JSONArray(result);
                MedicineNotification temp = null;

                //先缓存，再从缓存中读数据
                CacheUtil cu = new CacheUtil();

//                for (int i = 0; i < jsonArray.length(); i++) {       //一个循环代表一个对象
//                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//                    temp = gson.fromJson(jsonObject.toString(), MedicineNotification.class);
//                    cu.writeJson(getContext(), jsonObject.toString() + "&" + temp.getNextTakeTime().toString(), "notification", true);
//                    data0.add(toHashMap(temp));
//                }
                String json = "{\"notificationId\":15,\"medicineRecord\":{\"mrId\":1,\"medicine\":{\"medicineId\":\"1\",\"medicineName\":\"板蓝根\",\"medicineCompany\":\"复旦\",\"lastUpdateTime\":\"2017-03-02 01:31:52\",\"medicineAttention\":\"111\",\"medicineIngredient\":\"中草药\",\"medicinePhoto\":\"\\/Users\\/zhaoyawen\\/Downloads\\/数据库E-R图.png\",\"medicineGuidance\":\"医嘱\"},\"patient\":{\"patientId\":\"1\",\"patientName\":\"11\",\"patientPassword\":\"11\",\"patientPhoneNumber\":\"11\",\"patientAge\":11},\"doctor\":{\"doctorId\":\"2\",\"doctorPassword\":\"baiqueling\",\"doctorName\":\"百雀羚\",\"doctorHospital\":\"上海医院\",\"workingTime\":\"8~11\"},\"firstTakeTime\":\"2017-03-05 05:26:19\",\"finalTakeTime\":\"2017-03-05 05:26:40\",\"lastTakeTime\":\"2017-03-06 05:27:54\",\"nextTakeTime\":\"2017-03-06 10:20:00\",\"mrMethod\":\"aaa\",\"mrInterval\":1,\"mrBefore\":0,\"mrAfter\":0,\"mrAttention\":\"bbbbbbb\"},\"nextTakeTime\":\"2017-03-06 06:30:00\",\"pushSuccess\":false,\"alreadyFinished\":false,\"notiForgotten\":true}";

                //write to cache
                temp = gson.fromJson(json, MedicineNotification.class);
                cu.writeJson(getContext(), json.toString() + "&" + temp.getNextTakeTime().toString(), "notification", false);

                //read from cache
                List<String> l = cu.readNotificationsAll(getContext(), "notification");
                for (int i = 0; i < l.size(); i++) {
                    temp = gson.fromJson(l.get(i), MedicineNotification.class);
                    if (!temp.isAlreadyFinished())
                        data0.add(toHashMap(temp, l.get(i)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            //fit the data to the adapter
            adapter = new SimpleAdapter(getActivity(), data0, R.layout.notification_item, new String[]{"medicine", "time", "status", "hiden"}, new int[]{R.id.notification_medicine, R.id.notification_time, R.id.notification_status, R.id.hiden_text});
            listView.setAdapter(adapter);
            swiper.post(new Runnable() {
                @Override
                public void run() {
                    swiper.setRefreshing(false);
                }
            });
        }

        private HashMap<String, Object> toHashMap(MedicineNotification mn, String hiden) {
            HashMap<String, Object> hm = new HashMap();
            hm.put("medicine", mn.getMedicineRecord().medicine.medicineName);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            hm.put("time", "Time：" + sdf.format(mn.getNextTakeTime()));
            hm.put("hiden", hiden);
            hm.put("status", "finished");
            return hm;
        }
    }
}
