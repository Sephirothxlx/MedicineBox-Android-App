package org.spring.springboot.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class RelatedFamilyMembers {

    public RelatedFamilyMembers() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long rfmId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "rfm_patient_id")
    private Patient patient;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "rfm_member_id")
    private FamilyMember familyMember;

    public Patient getPatient() {
        return patient;
    }

    public Long getRfmId() {
        return rfmId;
    }

    public void setRfmId(Long rfmId) {
        this.rfmId = rfmId;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public FamilyMember getFamilyMember() {
        return familyMember;
    }

    public void setFamilyMember(FamilyMember familyMember) {
        this.familyMember = familyMember;
    }
}
