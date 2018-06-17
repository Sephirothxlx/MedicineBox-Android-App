package com.medbox.medbox;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;


import com.medbox.medbox.cache.CacheUtil;
import com.medbox.medbox.model.MedicineRecord;
import com.medbox.medbox.ui.AccountFragment;
import com.medbox.medbox.ui.FamilyFragment;
import com.medbox.medbox.ui.HistoryFragment;
import com.medbox.medbox.ui.NotificationsFragment;
import com.medbox.medbox.ui.PlanFragment;
import com.medbox.medbox.ui.SearchFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;

    private DrawerLayout drawerLayout;

    private NavigationView navigationView;
    private NotificationsFragment notificationsFragment;
    private FamilyFragment familyFragment;
    private HistoryFragment historyFragment;
    private PlanFragment planFragment;
    private AccountFragment accountFragment;
    private SearchFragment searchFragment;
    private TextView nav_name;
    private TextView nav_username;

    private Dialog dialog;

    private JSONObject ac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //drawer and fragment
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        //get the nav menu
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        nav_name = (TextView) findViewById(R.id.nav_name);
        nav_username = (TextView) findViewById(R.id.nav_username);

        CacheUtil cu = new CacheUtil();
        try {
            ac = new JSONObject(cu.readAccount(MainActivity.this));
            nav_name.setText("Name: " + ac.get("name").toString());
            nav_username.setText("Username: " + ac.get("username").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        //极光推送
        JPushInterface.setDebugMode(true);//正式版的时候设置false，关闭调试
        JPushInterface.init(this);
        //建议添加tag标签，发送消息的之后就可以指定tag标签来发送了
        Set<String> set = new HashSet<>();
        set.add("jiguangtest");//名字任意，可多添加几个
        JPushInterface.setTags(this, set, null);//设置标签

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //if the drawer is open, then the first time backpressed will make the drawer closed
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            dialog = new android.app.AlertDialog.Builder(this)
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            MainActivity.this.finish();
                            MainActivity.super.onDestroy();
                            android.os.Process.killProcess(android.os.Process.myPid()); //获取PID
                            System.exit(0);
                        }
                    })
                    .create();
            dialog.setTitle("Are you going to exit?");
            dialog.show();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar notification_item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view notification_item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_notifications) {
            switchTabWithoutBack(notificationsFragment = new NotificationsFragment());
            item.setChecked(false);
        } else if (id == R.id.nav_plan) {
            switchTabWithoutBack(planFragment = new PlanFragment());
            item.setChecked(false);
//        } else if (id == R.id.nav_family) {
//            switchTabWithoutBack(familyFragment = new FamilyFragment());
//            item.setChecked(false);
        } else if (id == R.id.nav_history) {
            switchTabWithoutBack(historyFragment = new HistoryFragment());
            item.setChecked(false);
        } else if (id == R.id.nav_search) {
            switchTabWithoutBack(searchFragment = new SearchFragment());
            item.setChecked(false);
        } else if (id == R.id.nav_account) {
            switchTabWithoutBack(accountFragment = new AccountFragment());
            item.setChecked(false);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void switchTabWithBack(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.main_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void switchTabWithoutBack(Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.main_container, fragment);
        transaction.commit();
    }

    public void updatePlanList(List<MedicineRecord> plans) {
        planFragment.updateList(plans);
    }
}
