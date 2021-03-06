package com.project.OnTheGoShop.Repo;

import com.project.OnTheGoShop.Beans.Driver;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

@Transactional
public interface DriverRepository extends PersonRepository<Driver> ,CrudRepository<Driver, Integer> {
	ArrayList<Driver> findAll();



}
