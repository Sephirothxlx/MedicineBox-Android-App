package org.spring.springboot.repository;

import org.spring.springboot.entity.FamilyMember;
import org.spring.springboot.entity.MedicineNotification;
import org.spring.springboot.entity.Patient;
import org.spring.springboot.entity.RelatedFamilyMembers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RelatedFamilyMembersRepository extends JpaRepository<RelatedFamilyMembers, Long> {

    @Query("select rfm.patient.patientId from RelatedFamilyMembers rfm where rfm.familyMember.memberId=:fid")
    String findPatientByMemberId(@Param("fid")String fid);
}
