package com.project.OnTheGoShop.Repo;

import com.project.OnTheGoShop.Beans.Van;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface VanRepository extends CrudRepository<Van, Integer> {
	ArrayList<Van> findAll();
	Van findById(int id);
    @Transactional
    @Modifying
    @Query("update Van n set n.lang = ?3,n.latitude=?2 where n.id = ?1")
    void Updatelocation(int id, String lan,String lag);
}
