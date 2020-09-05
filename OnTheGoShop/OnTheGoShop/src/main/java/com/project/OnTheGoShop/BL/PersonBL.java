package com.project.OnTheGoShop.BL;

import org.springframework.beans.factory.annotation.Autowired;

import com.project.OnTheGoShop.Repo.DriverRepository;
import com.project.OnTheGoShop.Repo.ManagerRepository;
import com.project.OnTheGoShop.Repo.UserRepository;

public class PersonBL {
	@Autowired
	UserRepository userRepository;
	@Autowired
	DriverRepository driverRepository;
	@Autowired
	ManagerRepository managerRepository;
	
	
	

}
