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
 * Created by Sephiroth on 18/4/3.
 */
public class HistoryFragment extends Fragment {

    private SwipeRefreshLayout swiper;
    private ListView listView;
    private SimpleAdapter adapter;
    private ArrayList<HashMap<String, Object>> data0 = new ArrayList();
    private int itemTag = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        listView = (ListView) view.findViewById(R.id.history_listview);
        listView.setOnItemClickListener(new Listener0());

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
        CacheUtil cu = new CacheUtil();
        List<String> l = cu.readNotificationsAll(getActivity(), "notification");
        MedicineNotification temp = null;
        for (int i = 0; i < l.size(); i++) {
            temp = gson.fromJson(l.get(i), MedicineNotification.class);
            if (temp.isAlreadyFinished())
                data0.add(toHashMap(temp, l.get(i)));
        }

        adapter = new SimpleAdapter(getActivity(), data0, R.layout.notification_item, new String[]{"medicine", "time", "status", "hiden"}, new int[]{R.id.notification_medicine, R.id.notification_time, R.id.notification_status, R.id.hiden_text});
        listView.setAdapter(adapter);

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
            intent.putExtra("tag", "1");
            startActivity(intent);
        }
    }

    private HashMap<String, Object> toHashMap(MedicineNotification mn, String hiden) {
        HashMap<String, Object> hm = new HashMap();
        hm.put("status", "not finished");
        hm.put("medicine", mn.getMedicineRecord().medicine.medicineName);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        hm.put("time", "Time：" + sdf.format(mn.getNextTakeTime()));
        hm.put("hiden", hiden);
        return hm;
    }
}
