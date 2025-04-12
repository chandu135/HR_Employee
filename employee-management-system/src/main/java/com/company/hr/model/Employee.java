/**
 * 
 */
package com.company.hr.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


@Entity
public class Employee {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Long id;
	
	@NotBlank(message = "Name is required")
    private String name;
	
	
	@Email(message = "Enter a valid email")
    @NotBlank(message = "Email is required")
    private String email;
	
	@NotBlank(message = "Department is required")
    private String department;

	@Valid
	@OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private Address address;
   
	public Employee() {}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
		if (address != null) {
            address.setEmployee(this);
        }
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}


}
