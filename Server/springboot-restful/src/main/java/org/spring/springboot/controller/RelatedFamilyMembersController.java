package org.spring.springboot.controller;

import org.spring.springboot.entity.FamilyMember;
import org.spring.springboot.entity.FriendApplication;
import org.spring.springboot.entity.Patient;
import org.spring.springboot.entity.RelatedFamilyMembers;
import org.spring.springboot.repository.FamilyMembersRepository;
import org.spring.springboot.repository.PatientRepository;
import org.spring.springboot.repository.RelatedFamilyMembersRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
public class RelatedFamilyMembersController {

    @Inject
    private PatientRepository patientRepository;

    @Inject
    private FamilyMembersRepository familyMembersRepository;


    //用户要求查看与自己关联的家庭成员
    @RequestMapping(value = "/membership/askAll/{id}", method = RequestMethod.GET)
    public List<FamilyMember> responseMembership(@PathVariable("id") String id) {
        //首先要确定这个消息是否存在
        Patient patient = patientRepository.findByPatientId(id);
        if (patient == null) {
            return null;
        }
        return familyMembersRepository.findByPatient(patient);
    }

}
