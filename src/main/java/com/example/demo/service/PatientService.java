package com.example.demo.service;

import com.example.demo.entity.Patient;
import com.example.demo.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;
    @Async
    public List<Patient> getAllList(){
        return patientRepository.findAll();
    }
    @Async
    public void delete(String id){
        patientRepository.delete(Long.valueOf(id));
    }
    @Async
    public void saveNew(){
        Patient employee = new Patient();
        patientRepository.save(employee);
    }
    @Async
    public Patient getById(String id){
        return patientRepository.getByID(Long.valueOf(id));
    }
    @Async
    public Patient update(Patient employee){
        return patientRepository.update(employee);
    }
    @Async
    private int calculateAge(String dateOfBirth) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthDate = LocalDate.parse(dateOfBirth,formatter); // Предполагается, что дата рождения задана в формате "yyyy-MM-dd"
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(birthDate, currentDate);
        return period.getYears();
    }
    @Async
    public double calculateAverageAge() {
        List<Patient> patients = patientRepository.findAll();
        int totalPatients = patients.size();
        int sumAge = 0;
        for (Patient patient : patients) {
            int age = calculateAge(patient.getDateOfBirth());
            sumAge += age;
        }
        return (double) sumAge / totalPatients;
    }
    @Async
    public double calculateAgeVariance() {
        List<Patient> patients = patientRepository.findAll();
        double averageAge = calculateAverageAge();
        int totalPatients = patients.size();
        double sumSquaredDifferences = 0;
        for (Patient patient : patients) {
            int age = calculateAge(patient.getDateOfBirth());
            double difference = age - averageAge;
            sumSquaredDifferences += difference * difference;
        }
        return sumSquaredDifferences / (totalPatients - 1);
    }
}
