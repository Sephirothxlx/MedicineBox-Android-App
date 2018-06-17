package org.spring.springboot.repository;

import org.spring.springboot.entity.Doctor;
import org.spring.springboot.entity.Medicine;
import org.spring.springboot.entity.MedicineRecord;
import org.spring.springboot.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

public interface MedicineRepository  extends JpaRepository<Medicine,String> {

    Medicine findByMedicineId(String id);


    @Query("select m.medicineName from Medicine m")
    List<String> findAllNames(@Param("name") String name);

}
