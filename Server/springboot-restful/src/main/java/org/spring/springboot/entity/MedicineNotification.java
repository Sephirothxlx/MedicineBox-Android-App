package org.spring.springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class MedicineNotification {

    public MedicineNotification() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long notificationId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "related_medicine_record_id")
    private MedicineRecord medicineRecord;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @Column
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
