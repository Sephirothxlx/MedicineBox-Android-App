package org.spring.springboot.repository;

import org.spring.springboot.entity.Doctor;
import org.spring.springboot.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor,String> {

    Doctor findByDoctorId(String id);


    @Query("select d.doctorName from Doctor d")
    List<String> findAllNames();

}
