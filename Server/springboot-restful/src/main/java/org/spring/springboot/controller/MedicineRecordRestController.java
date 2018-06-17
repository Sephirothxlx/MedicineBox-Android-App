package org.spring.springboot.controller;

import org.joda.time.DateTime;
import org.spring.springboot.entity.*;
import org.spring.springboot.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.print.Doc;
import javax.smartcardio.ATR;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@RestController
public class MedicineRecordRestController {

    @Inject
    private MedicineRecordRepository medicineRecordRepository;

    @Inject
    private DoctorRepository doctorRepository;

    @Inject
    private PatientRepository patientRepository;

    @Inject
    private MedicineRepository medicineRepository;

    @Inject
    private MedicineNotificationRepository medicineNotificationRepository;


    @RequestMapping(value = "/record/{id}", method = RequestMethod.GET)
    public MedicineRecord findOneMedicineRecord(@PathVariable("id") Long id) {
        return medicineRecordRepository.findByMrId(id);
    }

    @RequestMapping(value = "/record/create", method = RequestMethod.PUT)
    public String createRecord(@RequestBody MedicineRecord entity) {
        /**
         * 添加一条新的纪录，
         * firstTakeTime不能小于等于当前时间
         * finalTakeTime为空
         * lastTakeTime为空
         * nextTakeTime为=firsttaketime
         * interval,before,after默认为0，这个待定
         * */
        //这里medicineRecord的两个属性Medicine，patient，doctor都只有id，所以还要先进行查询，才能将这条记录存入数据库
        //doctor只有id
        if (entity.getFirstTakeTime().getTime() <= getCurrentTime().getTime()) {
            return "first take time cannot less than now!";
        }
        MedicineRecord medicineRecord = new MedicineRecord();
        medicineRecord.copyRecord(entity);
        Doctor doctor = doctorRepository.findByDoctorId(entity.getDoctor().getDoctorId());
        if (doctor == null) {
            return "doctor does not exist!";
        }
        medicineRecord.setDoctor(doctor);
        //patient 只有id
        Patient patient = patientRepository.findByPatientId(entity.getPatient().getPatientId());
        if (patient == null) {
            return "patient does not exit!";
        }
        medicineRecord.setPatient(patient);
        //medicine只有id
        Medicine medicine = medicineRepository.findByMedicineId(entity.getMedicine().getMedicineId());
        if (medicine == null) {
            return "medicine does not exit!";
        }
        medicineRecord.setMedicine(medicine);

        medicineRecordRepository.save(medicineRecord);
        return "true";
    }


    //客户请求用药计划
    @RequestMapping(value = "/record/askAll/{id}", method = RequestMethod.GET)
    public List<MedicineRecord> findRecordsForPatient(@PathVariable("id") String id) {
        return medicineRecordRepository.findAllByPatient(patientRepository.findByPatientId(id));
    }

    //客户结束用药计划
    @RequestMapping(value = "/record/finish/{id}", method = RequestMethod.GET)
    public String findRecordsForPatient(@PathVariable("id") Long id) {
        MedicineRecord medicineRecord = medicineRecordRepository.findByMrId(id);
        if (medicineRecord == null) {
            return "medicineRecord does not exists";
        }
        Date finalTakeTime = new Date(medicineRecord.getNextTakeTime().getTime() - medicineRecord.getMrInterval() * 3600000);//设置最后一次用药时间是nexttaketime之前的一次
        medicineRecord.setFinalTakeTime(finalTakeTime);//FinalTakeTime不是null说明用药已完成
        medicineRecordRepository.save(medicineRecord);
        //然后删除notification中的提醒条目
        medicineNotificationRepository.deleteByMedicineRecordId(id);
        return "true";
    }

    public Date getCurrentTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        Date currentTime = null;
        try {
            currentTime = df.parse(df.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return currentTime;
    }
}
