package com.project.OnTheGoShop.Repo;

import com.project.OnTheGoShop.Beans.Van;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

public interface VanRepository extends CrudRepository<Van, Integer> {
	ArrayList<Van> findAll();
	Van findById(int id);
}
