package org.spring.springboot.task;

import org.spring.springboot.entity.MedicineNotification;
import org.spring.springboot.entity.MedicineRecord;
import org.spring.springboot.repository.MedicineNotificationRepository;
import org.spring.springboot.repository.MedicineRecordRepository;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

//这个类负责，定时检查数据库看有没有生成新的通知！！！,如果有就生成新的通知，插入数据库
@Component
@Service
@Configurable
@EnableScheduling
public class ScheduledTasks {

    @Inject
    private MedicineNotificationRepository medicineNotificationRepository;

    @Inject
    private MedicineRecordRepository medicineRecordRepository;

    @Scheduled(fixedRate = 10000)//为了测试方便所以暂定为5秒，单位：ms
    @Transactional
    @Async
    public void generateNotification() {//这个函数只负责生成新的notification

        //medicineRecordRepository.updateNextTakeTime(getCurrentTime());

        //插入新的notification
        List<MedicineRecord> medicineRecordList = medicineRecordRepository.findAll();
        //然后插入新的数据到notification表
        for (MedicineRecord mr : medicineRecordList) {
            if (medicineNotificationRepository.countByMedicineRecordAndNextTakeTime(mr, mr.getNextTakeTime()) == 0) {//==0说明不存在这样一条notice
                MedicineNotification medicineNotification = new MedicineNotification();
                medicineNotification.setMedicineRecord(mr);
                medicineNotification.setNextTakeTime(mr.getNextTakeTime());//TODO:!!!!!!!!,这个对象缺少数据，id是要自动增加的，剩下三个默认false
                medicineNotificationRepository.save(medicineNotification);
            }
        }

        //更新notification的状态
        updateNotificationStateTask();

        //更新medicineRecord表
        /**
         * 注意此处，先更新notification的状态，再更新record的状态，就是为了防止忽略初次启动时的检查
         * */
        updateMedicineRecordTable();
    }

    public void updateMedicineRecordTable(){
        //首先更新medicine_record表，注意这里只更新一次，默认检查时间小于吃药间隔时间
        Date currentDate = getCurrentTime();
        List<MedicineRecord> medicineRecordList = medicineRecordRepository.findAll();
        for (MedicineRecord mr : medicineRecordList) {
            Date date = new Date(mr.getNextTakeTime().getTime() + mr.getMrInterval() * 3600000 - mr.getMrBefore() * 60000);
            //Date befordDate = new Date(0, 0, 0, 0, mr.getMrBefore(), 0);
            //Date intervalDate = new Date(0, 0, 0, mr.getMrInterval(), 0, 0);
            //此种情况说明当前时间夹在两次吃药时间中间
            if (mr.getNextTakeTime().getTime() < currentDate.getTime() && date.getTime() >= currentDate.getTime()) {
                Date newNext = new Date(mr.getNextTakeTime().getTime() + mr.getMrInterval() * 3600000);
                mr.setNextTakeTime(newNext);
                medicineRecordRepository.save(mr);
            }
            /**
             * 可能需要前台检查一条记录刚刚被录入的时候，不允许nexttaketime小于当前时间*/
        }
    }

    //@Scheduled(fixedRate = 10000)//为了测试方便所以暂定为10秒，单位：ms
    //@Transactional
    //@Async
    public void pushNotificationToClientWhenStart() {//这个函数负责检查notification并将需要推送的推出去
        //当前时间到了before
        //push=false
        //finish=false
        //forget=false

    }

    //@Scheduled(fixedRate = 10000)//为了测试方便所以暂定为10秒，单位：ms
    //@Transactional
    //@Async
    public void pushNotificationToClientWhenEnd() {//这个函数负责检查notification并将需要推送的推出去
        //当前时间到了after
        //push=true
        //finish=false
        //forget=false


    }

    public void updateNotificationStateTask() {//这个函数负责检查notification的状态
        /**
         * push不根据时间设置，指根据是否发送（成功）设置
         */
        /**
         * finish根据client回应设置
         */
        /**
         * forget根据client和时间设置
         */
        /**
         * 什么样子的notification需要改变？？？
         * 这个函数所能依照的只是当前时间
         * 不论push=false/true的情况下，只需要判断是否forget：now>next+after,那么就是忘记了
         * */

        //push=false,finish=false,forget=false---------没到时间，尚未推送,到时间要推送的
        //push=false,finish=false,forget=true--------已到时间，尚未推送，将来只推送一遍，用户登录的时候就推送
        //push=true,finish=false,forget=false--------未到时间，已经推送，等待forget改变或者finish改变
        //push-true,finish=false,forget=true---------已到时间，已经推送，但是忘记了，等待finished改变
        //push=true,finish=true,forget=false---------已到时间，按时完成任务，这条通知以后不需要出现了
        //push=true,finish=true,forget=true---------已经推送，已经忘记，但是已经知道忘记，这条通知以后不需要出现了
        Date currentTime = getCurrentTime();
        // Map<Long, Integer> longIntegerMap = medicineRecordRepository.findIdAndAfter();//TODO
        List<MedicineNotification> medicineNotificationList = medicineNotificationRepository.findAll();
        for (MedicineNotification medicineNotification : medicineNotificationList) {
            int after = medicineNotification.getMedicineRecord().getMrAfter();
            //Date afterDate = new Date(0, 0, 0, 0, after, 0);
            //没有finish并且超时，才算忘记了
            System.out.println(after * 60000 + medicineNotification.getNextTakeTime().getTime());
            System.out.println(currentTime.getTime());
            if (!medicineNotification.isAlreadyFinished() && (after * 60000 + medicineNotification.getNextTakeTime().getTime()) < currentTime.getTime()) {//说明忘记了
                medicineNotification.setNotiForgotten(true);
                medicineNotificationRepository.save(medicineNotification);
            }
            //medicineNotificationRepository.updateNotificationState(date);
        }

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
