package tdtu.edu.ex02.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tdtu.edu.ex02.models.Employee;
import tdtu.edu.ex02.services.EmployeeServiceImp;

@Controller
public class EmployeeController{
    @Autowired
    private EmployeeServiceImp employeeServiceImp;
    @GetMapping("/")
    public String getHomePage(){
        return "redirect:/employee";
    }
    @GetMapping("/employee")
    public String getAllEmployee(Model model){
        model.addAttribute("employees",employeeServiceImp.findAll());
        return "index";
    }
    @GetMapping("/employee/add")
    public String getAddEmployee(){
        return "add";
    }
    @PostMapping("/employee/add")
    public String postAddEmployee(@RequestBody Employee employee){
        employeeServiceImp.save(employee);
        return "add";
    }
}
