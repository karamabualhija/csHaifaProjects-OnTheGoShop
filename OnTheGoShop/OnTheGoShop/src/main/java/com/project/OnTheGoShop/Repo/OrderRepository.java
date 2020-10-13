package com.project.OnTheGoShop.Repo;

import com.project.OnTheGoShop.Beans.Order;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface OrderRepository extends CrudRepository<Order, Integer> {
	public ArrayList<Order> findAll();
	public Order findById(int id);

	@Transactional
    @Modifying
    @Query("update Orders n set n.pending = ?2 where n.id = ?1")
    void updatpending(int id,boolean x);
}

