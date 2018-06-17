package com.medbox.medbox.model;

import java.util.Date;

/**
 * Created by zhaoyawen on 2017/3/3.
 */

public class MedicineNotification {

    public MedicineNotification() {
    }


    private Long notificationId;

    private MedicineRecord medicineRecord;


    private Date nextTakeTime;

    private boolean pushSuccess;

    private boolean alreadyFinished;

    private boolean notiForgotten;

    public MedicineRecord getMedicineRecord() {
        return medicineRecord;
    }

    public void setMedicineRecord(MedicineRecord medicineRecord) {
        this.medicineRecord = medicineRecord;
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public Date getNextTakeTime() {
        return nextTakeTime;
    }

    public void setNextTakeTime(Date nextTakeTime) {
        this.nextTakeTime = nextTakeTime;
    }

    public boolean isPushSuccess() {
        return pushSuccess;
    }

    public void setPushSuccess(boolean pushSuccess) {
        this.pushSuccess = pushSuccess;
    }

    public boolean isAlreadyFinished() {
        return alreadyFinished;
    }

    public void setAlreadyFinished(boolean alreadyFinished) {
        this.alreadyFinished = alreadyFinished;
    }

    public boolean isNotiForgotten() {
        return notiForgotten;
    }

    public void setNotiForgotten(boolean notiForgotten) {
        this.notiForgotten = notiForgotten;
    }
}
