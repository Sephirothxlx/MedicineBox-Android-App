package org.spring.springboot.repository;

import org.spring.springboot.entity.MedicineNotification;
import org.spring.springboot.entity.MedicineRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface MedicineNotificationRepository extends JpaRepository<MedicineNotification, Long> {
    MedicineNotification findByNotificationId(Long id);

    @Query("select mn from MedicineNotification mn where mn.medicineRecord.patient.patientId=:id  and mn.pushSuccess=false")
    List<MedicineNotification> findAllNotificationsForOnePatientToSend(@Param("id") String id);

    //@Query("update MedicineNotification mn set mn.notiForgotten=true where mn.alreadyFinished=false and mn.medicineRecord.mrAfter+mn.nextTakeTime<:date")
    //void updateNotificationState(@Param("date") Date date);

    int countByMedicineRecordAndNextTakeTime(MedicineRecord medicineRecord, Date nextTakeTime);

    @Query("delete from MedicineNotification mn where mn.medicineRecord.mrId=:id")
    void deleteByMedicineRecordId(@Param("id") Long id);

}
