package org.spring.springboot.repository;

import org.spring.springboot.entity.FamilyMember;
import org.spring.springboot.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FamilyMembersRepository extends JpaRepository<FamilyMember, Long> {
    FamilyMember findByMemberId(String id);

    @Query("select fm from FamilyMember fm where fm.patient=:patient")
    List<FamilyMember> findByPatient(@Param("patient") Patient patient);
}
