package com.company.hr.controller;

import com.company.hr.model.Address;
import com.company.hr.model.Employee;
import com.company.hr.service.EmployeeService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

	
	private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public String listEmployees(Model model,
                                @RequestParam(required = false) String keyword) {
        if (keyword != null && !keyword.isEmpty()) {
            model.addAttribute("employees", employeeService.searchEmployees(keyword));
        } else {
            model.addAttribute("employees", employeeService.getAllEmployees());
        }

        model.addAttribute("employee", new Employee());
        return "employees";
    }

    @PostMapping("/save")
    public String saveEmployee(@Valid @ModelAttribute("employee") Employee employee,
            BindingResult result,
            Model model,
            @RequestParam(value = "keyword", required = false) String keyword) {

    	
    	// Ensure address object is not null
    	if (employee.getAddress() != null) {
    	    Address address = employee.getAddress();

    	    if (address.getId() == null && employee.getId() != null) {
    	        // Fetch existing employee to preserve the existing address ID
    	        Employee existingEmployee = employeeService.getEmployeeById(employee.getId());
    	        if (existingEmployee != null && existingEmployee.getAddress() != null) {
    	            address.setId(existingEmployee.getAddress().getId());
    	        }
    	    }

    	    address.setEmployee(employee); // maintain bidirectional link
    	    employee.setAddress(address);
    	} 
    	else {
    	    Address address = new Address();
    	    address.setEmployee(employee);
    	    employee.setAddress(address);
    	}
    	
    	
		// If validation errors exist, return to the same form with errors and employee list
		if (result.hasErrors()) {
			List<Employee> employees;
			if (keyword != null && !keyword.isEmpty()) {
				employees = employeeService.searchEmployees(keyword);
			} else {
				employees = employeeService.getAllEmployees();
			}
		
			model.addAttribute("employees", employees);
			model.addAttribute("keyword", keyword); // preserve search term if any
			return "employees";
		}

		// Save employee via service layer
		employeeService.saveEmployee(employee);
		return "redirect:/employees";
    }

    @GetMapping("/edit/{id}")
    public String editEmployee(@PathVariable Long id,  @RequestParam(value = "keyword", required = false) String keyword,Model model) {
        
    	Employee employee = employeeService.getEmployeeById(id);
    	
    	 // Ensure address exists to avoid form binding errors
        if (employee.getAddress() == null) {
            Address address = new Address();
            address.setEmployee(employee);
            employee.setAddress(address);
        } else {
            employee.getAddress().setEmployee(employee);
        }

    	
        model.addAttribute("employee", employee);
        model.addAttribute("employees", employeeService.getAllEmployees());
        model.addAttribute("keyword", keyword);
        return "employees";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/employees";
    }
    
}
