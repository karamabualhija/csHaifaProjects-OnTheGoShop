package com.project.OnTheGoShop.Repo;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.project.OnTheGoShop.Beans.van_products;

import javax.transaction.Transactional;

public interface van_prorepository extends CrudRepository<van_products, Integer>{
    public van_products findById(int id);
    public  ArrayList<van_products> findAllById(int id);
    public van_products findByVanid(int id);
    public  ArrayList<van_products> findAllByVanid(int id);
    public van_products findByProductid(int id);
    public  ArrayList<van_products> findAllByProductid(int id);
    public van_products findByAmount(int id);
    public  ArrayList<van_products> findAllByAmount(int id);

    @Transactional
    @Modifying
    @Query("update van_products n set n.amount = ?2 where n.id = ?1")
    void updateamount(int id,int amount);
    
    
}
