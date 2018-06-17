package org.spring.springboot.controller;

import org.spring.springboot.entity.Medicine;
import org.spring.springboot.entity.Patient;
import org.spring.springboot.help.TestImageBinary;
import org.spring.springboot.repository.MedicineRepository;
import org.spring.springboot.repository.PatientRepository;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;


@RestController
public class MedicineRestController {

    @Inject
    private MedicineRepository medicineRepository;


    @RequestMapping(value = "/medicine/{id}", method = RequestMethod.GET)
    public Medicine findOneMedicine(@PathVariable("id") String id) {
        return medicineRepository.findByMedicineId(id);
    }


    @RequestMapping(value = {"/medicinelist", "medicine/list"}, method = RequestMethod.GET)
    public List<Medicine> findAllMedicine() {
        return medicineRepository.findAll();
    }

    @RequestMapping(value = {"/medicine/create"}, method = RequestMethod.PUT)
    public void createMedicine(@RequestBody Medicine medicine) {
        medicineRepository.save(medicine);
    }

    /**
     * 图片需要二次请求！！！
     */
    @RequestMapping(value = {"/medicine/photo/{id}"}, method = RequestMethod.GET)
    public String findMedicinePhoto(@PathVariable("id") String id) {
        Medicine medicine = medicineRepository.findByMedicineId(id);
        TestImageBinary testImageBinary = new TestImageBinary();
        String result = testImageBinary.getImageBinary(medicine.getMedicinePhoto());
        if (result == null) {
            return "Sorry,photo transferred failed!!";
        } else {
            return result;
        }
    }


}
