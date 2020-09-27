package com.project.OnTheGoShop.Repo;

import com.project.OnTheGoShop.Beans.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    public Product findById(int id);
    public List<Product> findByName(String name);
    public ArrayList<Product> findByPriceBetween(float min, float max);
    public List<Product> findByPrice(float price);
    public List<Product> findByAmountBetween(double min, double max);
    public List<Product> findByAmount(double amount);
    public ArrayList<Product> findAll();
    public ArrayList<Product> findByNameContaining(String name);
}
