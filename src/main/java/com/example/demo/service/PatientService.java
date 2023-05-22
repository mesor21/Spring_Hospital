package com.example.demo.service;

import com.example.demo.entity.Patient;
import com.example.demo.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

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
}
