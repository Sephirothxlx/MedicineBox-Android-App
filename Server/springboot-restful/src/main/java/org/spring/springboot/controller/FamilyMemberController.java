package org.spring.springboot.controller;

import org.spring.springboot.entity.FamilyMember;
import org.spring.springboot.entity.MedicineRecord;
import org.spring.springboot.entity.Patient;
import org.spring.springboot.repository.FamilyMembersRepository;
import org.spring.springboot.repository.MedicineRecordRepository;
import org.spring.springboot.repository.PatientRepository;
import org.spring.springboot.repository.RelatedFamilyMembersRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;


@RestController
public class FamilyMemberController {

    @Inject
    private RelatedFamilyMembersRepository relatedFamilyMembersRepository;

    @Inject
    private PatientRepository patientRepository;

    @Inject
    private FamilyMembersRepository familyMembersRepository;

    @Inject
    private MedicineRecordRepository medicineRecordRepository;

    //家庭成员请求查看患者的用药记录
    @RequestMapping(value = "/familyMember/askForRecord/{patientId}/{memberId}", method = RequestMethod.GET)
    public List<MedicineRecord> responseMembership(@PathVariable("patientId") String patientId, @PathVariable("memberId") String memberId) {
        //确定两人是否是朋友
        if (patientId == relatedFamilyMembersRepository.findPatientByMemberId(memberId)) {
            return medicineRecordRepository.findAllByPatient(patientRepository.findByPatientId(patientId));
        }
        return null;
    }
}
