package org.spring.springboot.entity;

import java.util.Date;


public class Disease {

    public Disease() {
    }

    private String diseaseId;

    private String diseaseName;

    private String diseaseAlias;

    private Date lastUpdateTime;

    public String getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(String diseaseId) {
        this.diseaseId = diseaseId;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public String getDiseaseAlias() {
        return diseaseAlias;
    }

    public void setDiseaseAlias(String diseaseAlias) {
        this.diseaseAlias = diseaseAlias;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
