package org.spring.springboot.controller;


import org.spring.springboot.entity.Doctor;
import org.spring.springboot.entity.MedicineRecord;
import org.spring.springboot.repository.DoctorRepository;
import org.spring.springboot.repository.MedicineRecordRepository;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.smartcardio.ATR;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@RestController
public class DoctorRestController {
    @Inject
    private DoctorRepository doctorRepository;

    @RequestMapping(value = "/doctor/{id}", method = RequestMethod.GET)
    public Doctor findOneDoctor(@PathVariable("id") String id) {
        return doctorRepository.findByDoctorId(id);
    }

    @RequestMapping(value = "/doctor/save", method = RequestMethod.PUT)
    public void createDoctor(@RequestBody Doctor doctor) {
        doctorRepository.save(doctor);
    }

    @RequestMapping(value = "/doctor/allNames", method = RequestMethod.GET)
    public List<String> findAllDoctorNames() {
        return doctorRepository.findAllNames();
    }

    @RequestMapping(value = "/doctor/delete/{id}", method = RequestMethod.DELETE)
    public void deleteOneDoctor(@PathVariable("id") String id) {
        doctorRepository.delete(id);
    }


}
