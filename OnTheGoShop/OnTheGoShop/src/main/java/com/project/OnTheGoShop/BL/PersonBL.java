package com.project.OnTheGoShop.BL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.OnTheGoShop.Beans.Person;
import com.project.OnTheGoShop.Beans.User;
import com.project.OnTheGoShop.Repo.DriverRepository;
import com.project.OnTheGoShop.Repo.ManagerRepository;
import com.project.OnTheGoShop.Repo.PersonRepository;
import com.project.OnTheGoShop.Repo.UserRepository;
@Service

public class PersonBL {
	@Autowired
	UserRepository userRepository;
	@Autowired
	DriverRepository driverRepository;
	@Autowired
	ManagerRepository managerRepository;
	@Autowired
	PersonRepository<Person> personRepository;
	
	public Person LogIn(String username, String password) {
		Person byUserName = personRepository.findByUsername(username);
		if (byUserName != null && byUserName.validate(password)) {

			return byUserName;
		}

		return null;
	}
	
	
	

}
