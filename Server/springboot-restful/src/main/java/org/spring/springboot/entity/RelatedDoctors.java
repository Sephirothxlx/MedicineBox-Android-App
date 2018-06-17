package org.spring.springboot.entity;

import org.springframework.data.repository.query.Param;

import javax.print.Doc;


public class RelatedDoctors {

    public RelatedDoctors() {
    }

    private Patient Patient;

    private Doctor doctor;

    private String lastTakeTime;

    public org.spring.springboot.entity.Patient getPatient() {
        return Patient;
    }

    public void setPatient(org.spring.springboot.entity.Patient patient) {
        Patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public String getLastTakeTime() {
        return lastTakeTime;
    }

    public void setLastTakeTime(String lastTakeTime) {
        this.lastTakeTime = lastTakeTime;
    }
}
