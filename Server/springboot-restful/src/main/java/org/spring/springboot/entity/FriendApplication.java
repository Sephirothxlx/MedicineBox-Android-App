package org.spring.springboot.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class FriendApplication {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long faId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "fa_sender")
    private Patient patient;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "fa_receiver")
    private FamilyMember familyMember;


    private boolean faMessageType;//0表示请求交友，1表示请求退出交友
    private String faMessageContent;//交友消息内容

    public Long getFaId() {
        return faId;
    }

    public void setFaId(Long faId) {
        this.faId = faId;
    }

    public Patient getPatient() {
        return patient;
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

    public boolean isFaMessageType() {
        return faMessageType;
    }

    public void setFaMessageType(boolean faMessageType) {
        this.faMessageType = faMessageType;
    }

    public String getFaMessageContent() {
        return faMessageContent;
    }

    public void setFaMessageContent(String faMessageContent) {
        this.faMessageContent = faMessageContent;
    }
}
