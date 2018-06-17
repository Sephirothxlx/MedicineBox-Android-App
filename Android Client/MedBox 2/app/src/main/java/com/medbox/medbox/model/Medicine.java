package com.medbox.medbox.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by luzhoutao on 2017/3/7.
 */

public class Medicine implements Serializable{

    @SerializedName("medicineId")
    public String medicineId;

    @SerializedName("medicineName")
    public String medicineName;

    @SerializedName("medicineCompany")
    public String medicineCompany;

    @SerializedName("lastUpdateTime")
    public Date lastUpdateTime;

    @SerializedName("medicineAttention")
    public String medicineAttention;

    @SerializedName("medicineIngredient")
    public String medicineIngredient;

    @SerializedName("medicinePhoto")
    public String medicinePhoto;

    @SerializedName("medicineGuidance")
    public String medicineGuidance;

    @SerializedName("medicineDosage")
    public String medicineDosage;

}
