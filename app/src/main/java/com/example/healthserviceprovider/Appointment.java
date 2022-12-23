package com.example.healthserviceprovider;

public class Appointment {
    //Randevu sınıfı, randevuları ekleme ve çekme işlemleri için kullanılır
    private int id;
    private int userid;
    private int providerid;
    private String providerName;
    private String patientName;
    private String date;
    private int status;

    public Appointment() {
    }

    public Appointment(int id, int userid, int providerid, String date) {
        this.id = id;
        this.userid = userid;
        this.providerid = providerid;
        this.date = date;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getProviderid() {
        return providerid;
    }

    public void setProviderid(int providerid) {
        this.providerid = providerid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
