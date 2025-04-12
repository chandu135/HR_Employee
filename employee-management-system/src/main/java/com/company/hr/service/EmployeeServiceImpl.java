package com.company.hr.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.company.hr.model.Employee;
import com.company.hr.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    public void saveEmployee(Employee employee) {
        if (employee.getAddress() != null) {
            employee.getAddress().setEmployee(employee);
        }
        employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public List<Employee> searchEmployees(String keyword) {
        return employeeRepository
                .findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrDepartmentContainingIgnoreCase(
                        keyword, keyword, keyword);
    }

    @Override
    public List<String> getEmployeeNameSuggestions(String term) {
        List<Employee> employees = employeeRepository.findByNameStartingWithIgnoreCase(term);
        return employees.stream()
                .map(Employee::getName)
                .collect(Collectors.toList());
    }
}
