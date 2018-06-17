package com.medbox.medbox.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by luzhoutao on 2017/3/7.
 */

public class MedicineRecord implements Serializable{

    @SerializedName("mrId")
    public Long mrId;

    @SerializedName("medicine")
    public Medicine medicine;

    @SerializedName("patient")
    public Patient patient;

    @SerializedName("Doctor")
    public Doctor doctor;

    @SerializedName("firstTakeTime")
    public Date firstTakeTime;

    @SerializedName("finalTakeTime")
    public Date finalTakeTime;

    @SerializedName("lastTakeTime")
    public Date lastTakeTime;

    @SerializedName("nextTakeTime")
    public Date nextTakeTime;

    @SerializedName("mrMethod")
    public String mrMethod;

    @SerializedName("mrInterval")
    public int mrInterval;

    @SerializedName("mrBefore")
    public int mrBefore;

    @SerializedName("mrAfter")
    public int mrAfter;

    @SerializedName("mrAttention")
    public String mrAttention;

    public HashMap<String, Object> getPlanItem(){
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("name", this.medicine.medicineName);
        map.put("interval", this.mrInterval);
        map.put("period", this.firstTakeTime+"~"+this.finalTakeTime);
        map.put("attention", this.mrAttention);
        return map;
    }

}
