package com.medbox.medbox.network;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.medbox.medbox.MainActivity;
import com.medbox.medbox.model.MedicineRecord;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by luzhoutao on 2017/3/7.
 */

public class LoadPlansController implements Callback<List<MedicineRecord>> {

    private String base_url = "http://192.168.1.107:8080";
    private MainActivity mainActivity;

    public void loadAll(String user_id, MainActivity mainActivity){

        Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                Date date=null;
                try {
                    DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    date = dateFormat2.parse(json.getAsJsonPrimitive().getAsString());
                }catch(ParseException e){
                    e.printStackTrace();
                }
                return date;
            }
        }).create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        PlanAPI planAPI = retrofit.create(PlanAPI.class);

        Call<List<MedicineRecord>> call = planAPI.loadAllPlans(user_id);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<List<MedicineRecord>> call, Response<List<MedicineRecord>> response) {
        if(response.isSuccessful()){
            List<MedicineRecord> plans = response.body();
            mainActivity.updatePlanList(plans);
        }else{
            Log.e("MedBox", "load plans error!");
        }
    }

    @Override
    public void onFailure(Call<List<MedicineRecord>> call, Throwable t)
    {
        t.printStackTrace();
    }
}
