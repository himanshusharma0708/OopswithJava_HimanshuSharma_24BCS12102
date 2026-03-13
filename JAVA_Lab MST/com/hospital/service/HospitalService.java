package com.hospital.service;

import com.hospital.patient.Patient;
import com.hospital.exception.DuplicatePatientException;
import com.hospital.exception.PatientNotFoundException;
import java.io.*;
import java.util.*;

public class HospitalService {
    private List<Patient> patients;
    private static final String FILE_NAME = "patients.txt";

    public HospitalService() {
        patients = new ArrayList<>();
        loadPatientsFromFile();
    }

    private void loadPatientsFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) {
                    int id = Integer.parseInt(data[0].trim());
                    String name = data[1].trim();
                    int age = Integer.parseInt(data[2].trim());
                    String disease = data[3].trim().replace("\"", ""); // Clean quotes on load
                    patients.add(new Patient(id, name, age, disease));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }

    public void addPatient(Patient p) throws DuplicatePatientException {
        for (Patient existing : patients) {
            if (existing.getPatientId() == p.getPatientId()) {
                throw new DuplicatePatientException("Patient ID " + p.getPatientId() + " already exists.");
            }
        }
        patients.add(p);
        savePatientToFile(p);
        String cleanDisease = p.getDisease().replace("\"", "").trim();
        p.setDisease(cleanDisease);
        if (p.getAge() > 60 && "Heart Problem".equalsIgnoreCase(cleanDisease)) {
            System.out.println("\n>>> Priority Patient - Immediate Attention Required! <<<");
        }
        
        if (p.getAge()<0 || p.getAge()>120){
            System.out.println("\n>>> Warning: Unusual age entered! <<<");
        }
        
    }

    private void savePatientToFile(Patient p) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            bw.write(p.getPatientId() + "," + p.getPatientName() + "," + p.getAge() + "," + p.getDisease());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error saving to file.");
        }
    }

    public Patient searchPatient(int id) throws PatientNotFoundException {
        for (Patient p : patients) {
            if (p.getPatientId() == id) return p;
        }
        throw new PatientNotFoundException("Patient with ID " + id + " not found.");
    }

    public void displayPatients() {
        if (patients.isEmpty()) System.out.println("No records found.");
        for (Patient p : patients) p.displayPatient();
    }
}