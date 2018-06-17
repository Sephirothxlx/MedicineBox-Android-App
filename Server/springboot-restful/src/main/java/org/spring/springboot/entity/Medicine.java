package org.spring.springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Medicine {

    public Medicine() {
    }

    @Id
    private String medicineId;

    @Column
    private String medicineName;

    @Column
    private String medicineCompany;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @Column
    private Date lastUpdateTime;

    @Column
    private String medicineAttention;

    @Column
    private String medicineIngredient;

    @Column
    private String medicinePhoto;

    @Column
    private String medicineGuidance;


    public String getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(String medicineId) {
        this.medicineId = medicineId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getMedicineCompany() {
        return medicineCompany;
    }

    public void setMedicineCompany(String medicineCompany) {
        this.medicineCompany = medicineCompany;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getMedicineAttention() {
        return medicineAttention;
    }

    public void setMedicineAttention(String medicineAttention) {
        this.medicineAttention = medicineAttention;
    }

    public String getMedicineIngredient() {
        return medicineIngredient;
    }

    public void setMedicineIngredient(String medicineIngredient) {
        this.medicineIngredient = medicineIngredient;
    }

    public String getMedicinePhoto() {
        return medicinePhoto;
    }

    public void setMedicinePhoto(String medicinePhoto) {
        this.medicinePhoto = medicinePhoto;
    }

    public String getMedicineGuidance() {
        return medicineGuidance;
    }

    public void setMedicineGuidance(String medicineGuidance) {
        this.medicineGuidance = medicineGuidance;
    }
}
