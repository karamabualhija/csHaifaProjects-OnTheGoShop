package com.project.OnTheGoShop.Repo;

import com.project.OnTheGoShop.Beans.Manager;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

@Transactional
public interface ManagerRepository extends  PersonRepository<Manager> ,CrudRepository<Manager, Integer>  {
}
