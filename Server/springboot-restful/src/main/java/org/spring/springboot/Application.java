package org.spring.springboot;

import org.spring.springboot.entity.Patient;
import org.spring.springboot.repository.PatientRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Spring Boot 应用的标识
@SpringBootApplication
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(Application.class)
//@RunWith(SpringJuit4ClassRunner.class)
// mapper 接口类扫描包配置
//@MapperScan("org.spring.springboot.dao")
public class Application {

    private PatientRepository patientRepository;

    public static void main(String[] args) throws Exception{

        // 程序启动入口
        // 启动嵌入式的 Tomcat 并初始化 Spring 环境及其各 Spring 组件
        SpringApplication.run(Application.class,args);
    }

    public void test() throws Exception{
        Patient patient = new Patient();
        patient.setPatientId("AAA");
        patient.setPatientAge(10);
        patient.setPatientName("AAAAA");
        patient.setPatientPassword("password");
        patient.setPatientPhoneNumber("12345");
        patientRepository.save(patient);

        //Assert.assertEquals(10,patientRepository.findByPatientId("AAA"));

    }
}
