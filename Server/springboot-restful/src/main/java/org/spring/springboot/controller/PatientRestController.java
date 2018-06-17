package org.spring.springboot.controller;

import org.spring.springboot.entity.Patient;
import org.spring.springboot.repository.PatientRepository;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.util.List;


@RestController
public class PatientRestController {


    @Inject
    private PatientRepository patientRepository;

    @RequestMapping(value = {"/patientlist", "patient/list"}, method = RequestMethod.GET)
    public List<Patient> findAllPatients() {
        return patientRepository.findAll();
    }


    @RequestMapping(value = "/patient/name/{name}", method = RequestMethod.GET)
    public List<Integer> findAllAgesPatientsByName(@PathVariable("name") String name) {
        return patientRepository.findAllAges(name);
    }

    @RequestMapping(value = "/patient/count", method = RequestMethod.GET)
    public long findNowManyPatients() {
        return patientRepository.count();
    }


    @RequestMapping(value = "/patient/age", method = RequestMethod.GET)//http://127.0.0.1:8080/patient/age?age=12
    public List<Patient> findPatientByAge(@RequestParam(value = "age") int age) {
        return patientRepository.findByPatientAge(age);
    }

    @RequestMapping(value = "/patient/create", method = RequestMethod.PUT)
    public boolean createPatient(@RequestBody Patient patient) {
        if (patientRepository.countByPatientId(patient.getPatientId()) == 0) {
            patientRepository.save(patient);
            return true;
        } else {
            return false;//说明用户名重复
        }
    }


    //TODO:客户端可以直接传id和密码，也可以穿一个对象
    @RequestMapping(value = "/patient/login", method = RequestMethod.GET)
    public String loginPatient(@RequestBody Patient patient) {
        if (patientRepository.findByPatientId(patient.getPatientId()) == null) {
            return "用户名不存在";
        }
        if (!patientRepository.findByPatientId(patient.getPatientId()).getPatientPassword().equals(patient.getPatientPassword())){
            return "用户密码错误";
        }
        return "true";
    }

    @RequestMapping(value = "/patient/delete", method = RequestMethod.DELETE)
    public void deletePatient(@RequestBody Patient patient) {
        patientRepository.delete(patient);
    }


}
