package com.project.OnTheGoShop.Repo;

import com.project.OnTheGoShop.Beans.Manager;
import org.springframework.data.repository.CrudRepository;

public interface ManagerRepository extends CrudRepository<Manager, Integer> {
    Manager findByID(String id);
}
