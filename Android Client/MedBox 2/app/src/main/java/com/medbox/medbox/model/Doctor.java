package com.medbox.medbox.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by luzhoutao on 2017/3/7.
 */

public class Doctor implements Serializable{
    @SerializedName("doctorId")
    public String doctorId;

    @SerializedName("doctorPassword")
    public String doctorPassword;

    @SerializedName("doctorName")
    public String doctorName;

    @SerializedName("doctorHospital")
    public String doctorHospital;

    @SerializedName("workingTime")
    public String workingTime;

}
