package org.spring.springboot.controller;

import org.spring.springboot.entity.FamilyMember;
import org.spring.springboot.entity.FriendApplication;
import org.spring.springboot.entity.Patient;
import org.spring.springboot.entity.RelatedFamilyMembers;
import org.spring.springboot.repository.FamilyMembersRepository;
import org.spring.springboot.repository.FriendApplicationRepository;
import org.spring.springboot.repository.PatientRepository;
import org.spring.springboot.repository.RelatedFamilyMembersRepository;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;


@RestController
public class FriendApplicationController {

    @Inject
    private FriendApplicationRepository friendApplicationRepository;


    @Inject
    private RelatedFamilyMembersRepository relatedFamilyMembersRepository;

    @Inject
    private PatientRepository patientRepository;

    @Inject
    private FamilyMembersRepository familyMembersRepository;

    //patient请求新增一个关系，可能需要member验证
    //这些消息登录/用户请求的时候送出去
    @RequestMapping(value = "/friendApply/create/patientAsk/{patientId}/{memberID}/{type}", method = RequestMethod.POST)
    public String createPatientAskMembership(@PathVariable("patientId") String patientId, @PathVariable("memberID") String memberID, @PathVariable("type") boolean type, @RequestBody String content) {
        //首先要确定这两个人是否存在
        Patient patient = patientRepository.findByPatientId(patientId);
        if (patient == null) {
            return "patient does not exist!";
        }
        FamilyMember familyMember = familyMembersRepository.findByMemberId(memberID);
        if (familyMember == null) {
            return "member does not exist!";
        }
        //下面增添一个交友消息
        FriendApplication friendApplication = new FriendApplication();
        friendApplication.setPatient(patient);
        friendApplication.setFamilyMember(familyMember);
        friendApplication.setFaMessageContent(content);
        friendApplication.setFaMessageType(type);

        friendApplicationRepository.save(friendApplication);

        //RelatedFamilyMembers relatedFamilyMembers= new RelatedFamilyMembers();
        //relatedFamilyMembers.setPatient(patient);
        //relatedFamilyMembers.setFamilyMember(familyMember);
        //relatedFamilyMembersRepository.save(relatedFamilyMembers);
        return "true";
    }

    //patient请求新增一个关系，可能需要member验证
    //member可以选择同意或拒绝0/1
    @RequestMapping(value = "/friendApply/response/{id}/{agree}", method = RequestMethod.GET)
    public String responseMembership(@PathVariable("id") Long id, @PathVariable("agree") boolean agree) {
        //首先要确定这个消息是否存在
        FriendApplication friendApplication = friendApplicationRepository.findOne(id);
        if (friendApplication == null) {
            return "friend application does not exist!";
        }
        if (agree) {
            RelatedFamilyMembers relatedFamilyMembers = new RelatedFamilyMembers();
            relatedFamilyMembers.setPatient(friendApplication.getPatient());
            relatedFamilyMembers.setFamilyMember(friendApplication.getFamilyMember());
            relatedFamilyMembersRepository.save(relatedFamilyMembers);
        }
        friendApplicationRepository.delete(friendApplication);

        return "true";
    }

}
