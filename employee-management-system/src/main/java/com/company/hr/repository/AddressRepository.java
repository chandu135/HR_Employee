package com.company.hr.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.hr.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
		
}
