package com.hospital.patient;

public class Patient {
    private int patientId;
    private String patientName;
    private int age;
    private String disease;

    public Patient(int patientId, String patientName, int age, String disease) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.age = age;
        this.disease = disease;
    }
    public int getPatientId() {
        return patientId;
    }
    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }
    public String getPatientName() {
        return patientName;
    }
    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getDisease() {
        return disease;
    }
    public void setDisease(String disease) {
        this.disease = disease;
    }
    public void displayPatient() {
        System.out.println("Patient ID: " + patientId + " | Name: " + patientName + " | Age: " + age + " | Disease: " + disease);
        }
        if (p.getAge() > 60 && "Heart Problem".equalsIgnoreCase(cleanDisease)) {
            System.out.println("\n>>> Priority Patient - Immediate Attention Required! <<<");
        }
    }
