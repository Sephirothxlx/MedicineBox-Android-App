package org.spring.springboot.entity;


public class Administer {

    public Administer() {
    }

    private String administerId;

    private String administerPassword;

    public String getAdministerId() {
        return administerId;
    }

    public void setAdministerId(String administerId) {
        this.administerId = administerId;
    }

    public String getAdministerPassword() {
        return administerPassword;
    }

    public void setAdministerPassword(String administerPassword) {
        this.administerPassword = administerPassword;
    }
}
