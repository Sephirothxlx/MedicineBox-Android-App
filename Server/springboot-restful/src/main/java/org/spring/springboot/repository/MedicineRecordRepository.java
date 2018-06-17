package org.spring.springboot.repository;

import org.spring.springboot.entity.Medicine;
import org.spring.springboot.entity.MedicineRecord;
import org.spring.springboot.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public interface MedicineRecordRepository extends JpaRepository<MedicineRecord, Long> {

    MedicineRecord findByMrId(Long mrId);

    List<MedicineRecord> findAllByPatient(Patient patient);

    //@Query("update MedicineRecord mr set mr.nextTakeTime=mr.nextTakeTime+mr.mrInterval where mr.nextTakeTime < :currentTime and mr.nextTakeTime+mr.mrInterval-mr.mrBefore >= :currentTime")
    //void updateNextTakeTime(@Param("currentTime") Date currentTime);


}
