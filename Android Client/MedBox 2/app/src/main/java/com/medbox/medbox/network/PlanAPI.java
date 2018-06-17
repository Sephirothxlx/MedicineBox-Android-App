package com.medbox.medbox.network;

import com.medbox.medbox.model.MedicineRecord;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.Call;/**
 * Created by luzhoutao on 2017/3/7.
 */

public interface PlanAPI {

    @GET("/record/askAll/{user_id}")
    Call<List<MedicineRecord>> loadAllPlans(@Path("user_id") String user_id);
}
