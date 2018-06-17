package org.spring.springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

//http://127.0.0.1:8080/notification

@Entity
public class MedicineRecord {

    public MedicineRecord() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long mrId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "mr_medicine_Id")
    private Medicine medicine;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "mr_patient_Id")
    private Patient patient;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "mr_doctor_Id")
    private Doctor doctor;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column
    private Date firstTakeTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column
    private Date finalTakeTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column
    private Date lastTakeTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column
    private Date nextTakeTime;

    //@Column(name="`method`")
    private String mrMethod;

    //@Column(name="`interval`")
    private int mrInterval;

    private int mrBefore;

    private int mrAfter;

    public void copyRecord(MedicineRecord origin) {
        this.setFirstTakeTime(origin.getFirstTakeTime());
        this.setFinalTakeTime(origin.getFinalTakeTime());
        this.setLastTakeTime(origin.getLastTakeTime());
        this.setNextTakeTime(origin.getNextTakeTime());
        this.setMrMethod(origin.getMrMethod());
        this.setMrInterval(origin.getMrInterval());
        this.setMrBefore(origin.getMrBefore());
        this.setMrAfter(origin.getMrAfter());
        this.setMrAttention(origin.getMrAttention());
    }

    @Column
    private String mrAttention;

    public Date getNextTakeTime() {
        return nextTakeTime;
    }

    public void setNextTakeTime(Date nextTakeTime) {
        this.nextTakeTime = nextTakeTime;
    }

    public int getMrInterval() {
        return mrInterval;
    }

    public void setMrInterval(int mrInterval) {
        this.mrInterval = mrInterval;
    }

    public int getMrBefore() {
        return mrBefore;
    }

    public void setMrBefore(int mrBefore) {
        this.mrBefore = mrBefore;
    }

    public int getMrAfter() {
        return mrAfter;
    }

    public void setMrAfter(int mrAfter) {
        this.mrAfter = mrAfter;
    }

    public Long getMrId() {
        return mrId;
    }

    public void setMrId(Long mrId) {
        this.mrId = mrId;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Date getFirstTakeTime() {
        return firstTakeTime;
    }

    public void setFirstTakeTime(Date firstTakeTime) {
        this.firstTakeTime = firstTakeTime;
    }

    public Date getFinalTakeTime() {
        return finalTakeTime;
    }

    public void setFinalTakeTime(Date finalTakeTime) {
        this.finalTakeTime = finalTakeTime;
    }

    public Date getLastTakeTime() {
        return lastTakeTime;
    }

    public void setLastTakeTime(Date lastTakeTime) {
        this.lastTakeTime = lastTakeTime;
    }

    public String getMrMethod() {
        return mrMethod;
    }

    public void setMrMethod(String mrMethod) {
        this.mrMethod = mrMethod;
    }

    public String getMrAttention() {
        return mrAttention;
    }

    public void setMrAttention(String mrAttention) {
        this.mrAttention = mrAttention;
    }
}
