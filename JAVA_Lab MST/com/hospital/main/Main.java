package com.hospital.main;

import com.hospital.patient.Patient;
import com.hospital.service.HospitalService;
import com.hospital.exception.InvalidAgeException;
import com.hospital.exception.PatientNotFoundException;
import com.hospital.exception.DuplicatePatientException;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        HospitalService service = new HospitalService();
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Enter Patient ID:");
            int id = Integer.parseInt(scanner.nextLine().trim());

            System.out.println("Enter Patient Name:");
            String name = scanner.nextLine().trim();

            System.out.println("Enter Patient Age:");
            int age = Integer.parseInt(scanner.nextLine().trim());
            if (age < 0 || age > 120) {
                throw new InvalidAgeException("Invalid age entered: " + age);
            }

            System.out.println("Enter Patient Disease:");
            String disease = scanner.nextLine().trim();

            Patient newPatient = new Patient(id, name, age, disease);
            service.addPatient(newPatient);

        } catch (InvalidAgeException | DuplicatePatientException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Please enter valid numeric values for ID and Age.");
        }

        System.out.println("\n--- All Patients ---");
        service.displayPatients();

        System.out.println("\nEnter Patient ID to search:");
        try {
            int searchId = Integer.parseInt(scanner.nextLine().trim());
            Patient found = service.searchPatient(searchId);
            System.out.println("Patient Found:");
            found.displayPatient();
        } catch (PatientNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
        }
        
        scanner.close();
    }
}