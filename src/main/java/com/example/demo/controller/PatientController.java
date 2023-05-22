package com.example.demo.controller;

import com.example.demo.entity.Patient;
import com.example.demo.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PatientController {
    @Autowired
    private PatientService patientService;
    @Async
    @GetMapping("/")
    public String getAll(Model model){
        model.addAttribute("list",patientService.getAllList());
        return "main";
    }
    @Async
    @GetMapping("/new")
    public String newPatient(){
        patientService.saveNew();
        return "redirect:/";
    }
    @Async
    @GetMapping("edit/{id}")
    public String getForEdit(@PathVariable("id") String id,Model model){
        model.addAttribute("patient",patientService.getById(id));
        return "edit";
    }
    @Async
    @PostMapping("edit/{id}")
    public String postDataForEdit(@PathVariable("id") String id,
                                  @RequestParam(value = "firstName",required = false) String firstName,
                                  @RequestParam(value = "lastName",required = false) String lastName,
                                  @RequestParam(value = "middleName",required = false) String middleName,
                                  @RequestParam(value = "dateOfBirth",required = false) String dateOfBirth,
                                  @RequestParam(value = "diagnosis",required = false) String diagnosis,
                                  @RequestParam(value = "patientRegistrationDate",required = false) String patientRegistrationDate,
                                  @RequestParam(value = "dischargeDate",required = false) String dischargeDate
                                  ){
        Patient patient = new Patient(Long.parseLong(id),firstName,lastName,middleName,dateOfBirth,diagnosis,patientRegistrationDate,dischargeDate);
        patientService.update(patient);
        return "redirect:/";
    }
    @Async
    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") String id){
        patientService.delete(id);
        return "redirect:/";
    }
}
