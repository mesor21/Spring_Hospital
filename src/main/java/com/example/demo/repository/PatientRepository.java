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

    // Компаратор для сравнения пациентов по их идентификатору
    private Comparator<Patient> idComparator = new Comparator<Patient>() {
        @Override
        public int compare(Patient o1, Patient o2) {
            return o1.getId().compareTo(o2.getId());
        }
    };

    public PatientRepository(Gson gson) {
        this.gson = gson;
    }

    // Метод для загрузки данных о пациентах из файла
    @Async
    private List<Patient> loadData() {
        var list = new ArrayList<Patient>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePlace));
            list = gson.fromJson(bufferedReader, new TypeToken<List<Patient>>() {}.getType());
            bufferedReader.close();
            System.out.println("Patient objects have been read from " + filePlace + " file.");
            list.sort(idComparator); // Сортировка списка пациентов по их идентификатору
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Метод для записи данных о пациентах в файл
    @Async
    private void writeData(List<Patient> patientList) {
        try {
            FileWriter fileWriter = new FileWriter(filePlace);
            gson.toJson(patientList, fileWriter);
            fileWriter.close();
            System.out.println("Patient objects have been saved to " + filePlace + " file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод для получения пациента по его идентификатору
    @Async
    public Patient getByID(Long id) {
        List<Patient> patientList = loadData();
        var buff = patientList.stream().filter(x -> x.getId() == Integer.parseInt(id.toString())).findFirst().get();
        return buff;
    }

    // Метод для удаления пациента по его идентификатору
    @Async
    public void delete(Long patientId) {
        List<Patient> patientList = loadData();
        patientList.removeIf(x -> patientId - 1 >= 0 && x.getId() == patientId);
        writeData(patientList);
    }

    // Метод для сохранения пациента
    @Async
    public void save(Patient patient) {
        List<Patient> patientList = loadData();
        if (patientList.isEmpty()) {
            patient.setId(Long.valueOf(1));
        } else {
            patient.setId(Long.valueOf(patientList.get(patientList.size() - 1).getId() + 1));
        }
        patientList.add(patient);
        writeData(patientList);
    }

    // Метод для получения всех пациентов
    @Async
    public List<Patient> findAll() {
        List<Patient> patientList = loadData();
        return patientList;
    }

    // Метод для обновления информации о пациенте
    @Async
    public Patient update(Patient patient) {
        List<Patient> patientList = loadData();
        if (!patientList.isEmpty() && patient != null) {
            var id = 0;
            for (var item : patientList) {
                if (item.getId() == patient.getId()) {
                    break;
                }
                id = id + 1;
            }
            patientList.set(id, patient);
        }
        writeData(patientList);
        patientList = loadData();
        return patientList.stream().filter(x -> (x.getId()) == patient.getId()).toList().get(0);
    }
}
