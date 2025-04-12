package com.company.hr.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.company.hr.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	List<Employee> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrDepartmentContainingIgnoreCase(String name, String email, String department);

	List<Employee> findByNameStartingWithIgnoreCase(String prefix); //for autocomplete
}
