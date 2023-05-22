package com.example.demo.repository;

import com.example.demo.entity.Patient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Repository
public class PatientRepository {
    private final String filePlace = "src/main/resources/static/patient.json";
    private Gson gson;

    private Comparator<Patient> idComparator = new Comparator<Patient>() {
        @Override
        public int compare(Patient o1, Patient o2) {
            return o1.getId().compareTo(o2.getId());
        }
    };

    public PatientRepository(Gson gson) {
        this.gson = gson;
    }
    @Async
    private List<Patient> loadData() {
        var list = new ArrayList<Patient>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePlace));
            list = gson.fromJson(bufferedReader, new TypeToken<List<Patient>>() {
            }.getType());
            bufferedReader.close();
            System.out.println("Lighting objects have been read from " + filePlace + " file.");
            list.sort(idComparator);
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Async
    private void writeData(List<Patient> employee) {
        try {
            FileWriter fileWriter = new FileWriter(filePlace);
            gson.toJson(employee, fileWriter);
            fileWriter.close();
            System.out.println("Lighting objects have been saved to " + filePlace + " file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Async
    public Patient getByID(Long id) {
        List<Patient> employee = loadData();
        var buff = employee.stream().filter(x -> x.getId() == Integer.parseInt(id.toString())).findFirst().get();
        return buff;
    }
    @Async
    public void delete(Long myClassId) {
        List<Patient> myClassList = loadData();
        myClassList.removeIf(x -> myClassId - 1 >= 0 && x.getId() == myClassId);
        writeData(myClassList);
    }
    @Async
    public void save(Patient employee) {
        List<Patient> myClassList = loadData();
        if (myClassList.isEmpty()) {
            employee.setId(Long.valueOf(1));
        } else {
            employee.setId(Long.valueOf(myClassList.get(myClassList.size() - 1).getId() + 1));
        }
        myClassList.add(employee);
        writeData(myClassList);
    }
    @Async
    public List<Patient> findAll() {
        List<Patient> myClassList = loadData();
        return myClassList;
    }
    @Async
    public Patient update(Patient patient) {
        List<Patient> employees = loadData();
        if (!employees.isEmpty() && patient != null) {
            var id = 0;
            for (var item : employees) {
                if (item.getId() == patient.getId()) {
                    break;
                }
                id = id + 1;
            }
            employees.set(id, patient);
        }
        writeData(employees);
        employees = loadData();
        return employees.stream().filter(x -> (x.getId()) == patient.getId()).toList().get(0);
    }
}
