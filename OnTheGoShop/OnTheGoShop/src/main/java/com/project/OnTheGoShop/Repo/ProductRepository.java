package com.project.OnTheGoShop.Repo;

import com.project.OnTheGoShop.Beans.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Integer> {
}
