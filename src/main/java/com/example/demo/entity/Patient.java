package com.example.demo.entity;

public class Patient {
    private Long id; // Уникальный идентификатор пациента
    private String firstName; // Имя пациента
    private String lastName; // Фамилия пациента
    private String middleName; // Отчество пациента
    private String dateOfBirth; // Дата рождения пациента
    private String diagnosis; // Диагноз пациента
    private String patientRegistrationDate; // Дата регистрации пациента
    private String dischargeDate; // Дата выписки пациента

    public Patient() {
        this.dateOfBirth = "1970-01-01"; // Устанавливаем значение по умолчанию для даты рождения
    }

    public Patient(Long id, String firstName, String lastName, String middleName, String dateOfBirth, String diagnosis, String patientRegistrationDate, String dischargeDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.dateOfBirth = dateOfBirth;
        this.diagnosis = diagnosis;
        this.patientRegistrationDate = patientRegistrationDate;
        this.dischargeDate = dischargeDate;
    }

    // Геттеры и сеттеры для всех полей

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getPatientRegistrationDate() {
        return patientRegistrationDate;
    }

    public void setPatientRegistrationDate(String patientRegistrationDate) {
        this.patientRegistrationDate = patientRegistrationDate;
    }

    public String getDischargeDate() {
        return dischargeDate;
    }

    public void setDischargeDate(String dischargeDate) {
        this.dischargeDate = dischargeDate;
    }
}
