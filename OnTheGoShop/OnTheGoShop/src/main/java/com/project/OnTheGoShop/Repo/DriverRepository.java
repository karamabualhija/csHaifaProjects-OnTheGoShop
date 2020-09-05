package com.project.OnTheGoShop.Repo;

import com.project.OnTheGoShop.Beans.Driver;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

@Transactional
public interface DriverRepository extends PersonRepository<Driver> ,CrudRepository<Driver, Integer> {

}
