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

    // Метод для получения списка всех пациентов
    @Async
    public List<Patient> getAllList() {
        return patientRepository.findAll();
    }

    // Метод для удаления пациента по его идентификатору
    @Async
    public void delete(String id) {
        patientRepository.delete(Long.valueOf(id));
    }

    // Метод для сохранения нового пациента
    @Async
    public void saveNew() {
        Patient patient = new Patient();
        patientRepository.save(patient);
    }

    // Метод для получения пациента по его идентификатору
    @Async
    public Patient getById(String id) {
        return patientRepository.getByID(Long.valueOf(id));
    }

    // Метод для обновления информации о пациенте
    @Async
    public Patient update(Patient patient) {
        return patientRepository.update(patient);
    }

    // Метод для вычисления возраста на основе даты рождения
    @Async
    private int calculateAge(String dateOfBirth) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthDate = LocalDate.parse(dateOfBirth, formatter); // Предполагается, что дата рождения задана в формате "yyyy-MM-dd"
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(birthDate, currentDate);
        return period.getYears();
    }

    // Метод для вычисления среднего возраста пациентов
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

    // Метод для вычисления дисперсии возраста пациентов
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
