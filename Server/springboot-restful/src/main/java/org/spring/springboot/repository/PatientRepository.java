package org.spring.springboot.repository;

import org.spring.springboot.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient,String> {

    Patient findByPatientId(String patientId);


    int countByPatientId(String id);

    List<Patient> findByPatientAge(int patientAge);

    @Query("select p.patientAge from Patient p where p.patientName=:name")
    List<Integer> findAllAges(@Param("name") String name);


}
