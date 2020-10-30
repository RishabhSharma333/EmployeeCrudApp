package com.springside.springcrud.controller;

import com.springside.springcrud.exception.ResourceNotFoundException;
import com.springside.springcrud.model.Employee;
import com.springside.springcrud.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
    private EmployeeRepo employeeRepo;

    @GetMapping("/employees")
    public List<Employee> getAllEmployee(){
        return employeeRepo.findAll();
    }
    
    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee){
        return employeeRepo.save(employee);
    }

    @GetMapping("/employees/{Id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long Id){
        Employee employee= employeeRepo.findById(Id).orElseThrow(
                ()->new ResourceNotFoundException("Employee Does not exist with id :"+Id)
        );
        return ResponseEntity.ok(employee);
    }
    @PutMapping("/employees/{Id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long Id,
                                                   @RequestBody Employee employee){
        Employee employee2= employeeRepo.findById(Id).orElseThrow(
                ()->new ResourceNotFoundException("Employee Does not exist with id :"+Id)
        );
        employee2.setFirstName(employee.getFirstName());
        employee2.setLastName(employee.getLastName());
        employee2.setEmailId(employee.getEmailId());
        return   ResponseEntity.ok(employeeRepo.save(employee2));//this doesnt change its id  in the actual database also it
        // remains as it was in the first place
    }
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Map<String ,Boolean>> deleteEmployee(@PathVariable  Long id){
        Employee employee2= employeeRepo.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Employee Does not exist with id :"+id)
        );
         employeeRepo.deleteById(id);
         Map<String,Boolean> status=new HashMap<>();
         status.put("deleted",Boolean.TRUE);
         return ResponseEntity.ok(status);
    }
}
