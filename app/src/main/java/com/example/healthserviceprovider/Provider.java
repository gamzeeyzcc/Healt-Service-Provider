package com.example.healthserviceprovider;

public class Provider {
    private int id;
    private String diplomaNo;
    private String password;
    private String name;
    private String profession;
    private double lat;
    private double lng;
    //Provider işlemler için yardımcı sınıf
    public Provider() {
    }

    public Provider(int id, String diplomaNo, String password, String name, String profession, double lat, double lng) {
        this.id = id;
        this.diplomaNo = diplomaNo;
        this.password = password;
        this.name = name;
        this.profession = profession;
        this.lat = lat;
        this.lng = lng;
    }

    public Provider(String diplomaNo, String password, String name, String profession, double lat, double lng) {
        this.diplomaNo = diplomaNo;
        this.password = password;
        this.name = name;
        this.profession = profession;
        this.lat = lat;
        this.lng = lng;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDiplomaNo() {
        return diplomaNo;
    }

    public void setDiplomaNo(String diplomaNo) {
        this.diplomaNo = diplomaNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
