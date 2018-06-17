package com.medbox.medbox.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.medbox.medbox.R;
import com.medbox.medbox.cache.CacheUtil;
import com.medbox.medbox.model.MedicineNotification;

import org.json.JSONException;
import org.json.JSONObject;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by Sephiroth on 17/3/6.
 */
public class NotificationItemActivity extends AppCompatActivity {

    private TextView name;
    private TextView time;
    private TextView dosage;
    private TextView description;
    private TextView taboo;
    private TextView status;
    private LinearLayout buttonLayout;

    private String hiden;
    private String status_str;
    private String tag;
    private MedicineNotification temp;

//    private String name_str="Medicine: ";
//    private String time_str="Time: ";
//    private String dosage_str="Dosage: ";
//    private String description_str;
//    private String taboo_str;

    private Button button0;
    private Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_item);
        Bundle bundle = getIntent().getExtras();
        hiden = bundle.getString("hiden");
        status_str = bundle.getString("status");
        tag = bundle.getString("tag");
        name = (TextView) findViewById(R.id.notification_item_name);
        time = (TextView) findViewById(R.id.notification_item_time);
        dosage = (TextView) findViewById(R.id.notification_item_dosage);
        description = (TextView) findViewById(R.id.notification_item_description);
        taboo = (TextView) findViewById(R.id.notification_item_taboo);
        status = (TextView) findViewById(R.id.notification_item_status);

        buttonLayout = (LinearLayout) findViewById(R.id.notification_item_buttonLayout);
        //this activity has two entrance
        if (tag.equals("1")) {
            buttonLayout.setVisibility(View.INVISIBLE);
        }

        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle("Notification");

        GsonBuilder builder = new GsonBuilder();
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
        temp = gson.fromJson(hiden, MedicineNotification.class);
        name.setText("Medicine: " + temp.getMedicineRecord().medicine.medicineName);
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        time.setText("Time: " + sdf.format(temp.getNextTakeTime()));
        dosage.setText("Dosage: " + temp.getMedicineRecord().medicine.medicineDosage);
        description.setText("Description: " + temp.getMedicineRecord().medicine.medicineGuidance);
        taboo.setText("Taboo: " + temp.getMedicineRecord().medicine.medicineAttention);
        status.setText("Status: " + status_str);
        button0 = (Button) findViewById(R.id.notification_item_button0);
        button1 = (Button) findViewById(R.id.notification_item_button1);

        button0.setOnClickListener(new listener0());
        button1.setOnClickListener(new listener1());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    public class listener0 implements View.OnClickListener {
        public void onClick(View view) {
            CacheUtil cu = new CacheUtil();
            //read cookie and write cookie
            List<String> l = cu.readNotificationsAll(NotificationItemActivity.this, "notification");
            JSONObject jo;
            try {
                for (int i = 0; i < l.size(); i++) {
                    jo = new JSONObject(l.get(i));
                    if (jo.get("notificationId").equals(temp.getNotificationId())) {
                        jo.put("alreadyFinished", "false");
                        l.set(i, jo.toString());
                    }
                }
                cu.clear(NotificationItemActivity.this, "notification");
                for (int i = 0; i < l.size(); i++) {
                    cu.writeJson(NotificationItemActivity.this, l.get(i), "notification", true);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public class listener1 implements View.OnClickListener {
        public void onClick(View view) {
            CacheUtil cu = new CacheUtil();
            //read cookie
            List<String> l = cu.readNotificationsAll(NotificationItemActivity.this, "notification");
            JSONObject jo;
            try {
                for (int i = 0; i < l.size(); i++) {
                    jo = new JSONObject(l.get(i));
                    if (jo.get("notificationId").equals(temp.getNotificationId())) {
                        jo.put("alreadyFinished", "true");
                        l.set(i, jo.toString());
                    }
                }
                cu.clear(NotificationItemActivity.this, "notification");
                for (int i = 0; i < l.size(); i++) {
                    cu.writeJson(NotificationItemActivity.this, l.get(i), "notification", true);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
