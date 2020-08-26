package com.project.OnTheGoShop.BL;

import com.project.OnTheGoShop.Beans.Driver;
import com.project.OnTheGoShop.Beans.Manager;
import com.project.OnTheGoShop.Beans.Person;
import com.project.OnTheGoShop.Repo.ManagerRepository;
import com.project.OnTheGoShop.Repo.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerBL {

    @Autowired
    PersonRepository managerRepository;

    public boolean register(Manager manager){
        Person bySys_id = managerRepository.findByID(manager.getID());
        if (!(bySys_id instanceof Manager)){
            managerRepository.save(manager);
            return true;
        }
        return false;
    }

    public boolean registerDriver(Driver driver){
        Person byId = managerRepository.findByID(driver.getID());
        if (!(byId instanceof Driver)){
            managerRepository.save(driver);
            return true;
        }
        return false;
    }
}
