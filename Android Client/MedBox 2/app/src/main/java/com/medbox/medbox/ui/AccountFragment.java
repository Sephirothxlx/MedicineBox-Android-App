package com.medbox.medbox.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.medbox.medbox.R;
import com.medbox.medbox.cache.CacheUtil;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sephiroth on 17/3/9.
 */

public class AccountFragment extends Fragment {
    private TextView account_name;
    private TextView account_age;
    private TextView account_gender;
    private TextView account_username;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view=inflater.inflate(R.layout.fragment_account, container, false);

        account_name=(TextView)getActivity().findViewById(R.id.account_name);
        account_age=(TextView)getActivity().findViewById(R.id.account_age);
        account_gender=(TextView)getActivity().findViewById(R.id.account_gender);

        CacheUtil cu=new CacheUtil();
        try {
            JSONObject ac = new JSONObject(cu.readAccount(getActivity()));
            account_name.setText(ac.get("name").toString());
            account_username.setText(ac.get("username").toString());
            account_age.setText(ac.get("age").toString());
            account_gender.setText(ac.get("gender").toString());
        }catch(JSONException e){
            e.printStackTrace();
        }

        return view;
    }

}
