package com.project.OnTheGoShop.BL;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.OnTheGoShop.Beans.Driver;
import com.project.OnTheGoShop.Repo.DriverRepository;

@Service
public class DriverBL {
	@Autowired
	DriverRepository driverrepo;

	public ArrayList<Driver> findalldrivers() {
		return driverrepo.findAll();

	}

	public void add(Driver d) {

		driverrepo.save(d);
	}

}
