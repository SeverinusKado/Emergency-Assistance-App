package com.example.mednow;

public class Doctor {
    private String name;
    private String hospital;
    private String speciality;
    private String phoneNumber;
    private String availablehours;

    public Doctor(String name, String hospital, String speciality, String phoneNumber, String availablehours){
        this.name = name;
        this.hospital = hospital;
        this.speciality = speciality;
        this.phoneNumber = phoneNumber;
        this.availablehours = availablehours;
    }

    public String getName() {return name;}
    public String getHospital() {return hospital;}
    public String getSpeciality() {return speciality;}
    public String getPhoneNumber() {return phoneNumber;}
    public String getAvailablehours() {return availablehours;}
}
