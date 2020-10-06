package com.project.OnTheGoShop.Repo;

import java.util.ArrayList;
import org.springframework.data.repository.CrudRepository;
import com.project.OnTheGoShop.Beans.order_product;

public interface Order_ProductRepository extends CrudRepository<order_product, Integer>{
    public order_product findById(int id);
    public  ArrayList<order_product> findAllById(int id);
    public order_product findByOrderid(int id);
    public  ArrayList<order_product> findAllByOrderid(int id);
    public order_product findByProductid(int id);
    public  ArrayList<order_product> findAllByProductid(int id);
    public order_product findByAmount(int id);
    public  ArrayList<order_product> findAllByAmount(int id);
}
