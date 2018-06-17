package org.spring.springboot.controller;

import com.sun.org.apache.bcel.internal.generic.LREM;

import org.spring.springboot.entity.MedicineNotification;
import org.spring.springboot.entity.Patient;
import org.spring.springboot.repository.MedicineNotificationRepository;
import org.spring.springboot.repository.MedicineRecordRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


@RestController
public class MedicineNotificationController {
    @Inject
    private MedicineNotificationRepository medicineNotificationRepository;

    @Inject
    private MedicineRecordRepository medicineRecordRepository;

    //某条notification已经完成,返回值代表是否更新成功
    @RequestMapping(value = "/notification/finish/{id}", method = RequestMethod.GET)
    public boolean finish(@PathVariable("id") Long id) {
        try {
            MedicineNotification medicineNotification = medicineNotificationRepository.findOne(id);
            medicineNotification.setAlreadyFinished(true);
            //如果没忘记，那么更新lasttaketime
            if (!medicineNotification.isNotiForgotten()) {
                medicineNotification.getMedicineRecord().setLastTakeTime(getCurrentTime());
                medicineRecordRepository.save(medicineNotification.getMedicineRecord());
            }
            medicineNotificationRepository.save(medicineNotification);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    //客户端请求notice,把push=false的且时间已到的推送出去
    @RequestMapping(value = "/notification/ask/{id}/{time1}/{time2}", method = RequestMethod.GET)//TODO:???????
    public List<MedicineNotification> sendNoticeWhenRequest(@PathVariable("id") String id, @PathVariable("time1") String dateString1, @PathVariable("time2") String dateString2) {
        Date currentTime = stringToDate(dateString1 + " " + dateString2);
        //Date currentTime = getCurrentTime();
        List<MedicineNotification> result = new LinkedList<MedicineNotification>();
        //下面选出了push=false的属于这个客户的notification
        List<MedicineNotification> medicineNotificationList = medicineNotificationRepository.findAllNotificationsForOnePatientToSend(id);
        //下面对时间进行筛选
        for (MedicineNotification medicineNotification : medicineNotificationList) {
            //Date befordDate = new Date(0, 0, 0, 0, medicineNotification.getMedicineRecord().getMrBefore(), 0);
            Date date = new Date(medicineNotification.getNextTakeTime().getTime() - medicineNotification.getMedicineRecord().getMrBefore() * 60000);
            if (date.getTime() <= currentTime.getTime()) {
                result.add(medicineNotification);
            }
        }
        return result;
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

    public Date stringToDate(String dateString) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss ");//TODO???????
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        return date;
    }

}
