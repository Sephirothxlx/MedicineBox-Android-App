package com.medbox.medbox.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by luzhoutao on 2017/3/7.
 */

public class Patient implements Serializable{

    @SerializedName("patientId")
    public String patientId;

    @SerializedName("patientName")
    public String patientName;

    @SerializedName("patientPassword")
    public String patientPassword;

    @SerializedName("patientPhoneNumber")
    public String patientPhoneNumber;

    @SerializedName("patientAge")
    public int patientAge;

}
