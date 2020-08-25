package com.project.OnTheGoShop.Repo;

import com.project.OnTheGoShop.Beans.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Integer> {
}
