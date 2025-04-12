package com.company.hr.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.company.hr.model.Employee;

@Service
public interface EmployeeService {
	
	List<Employee> getAllEmployees();
    Employee getEmployeeById(Long id);
    void saveEmployee(Employee employee);
    void deleteEmployee(Long id);
    List<Employee> searchEmployees(String keyword);
    List<String> getEmployeeNameSuggestions(String term);
}
