package com.project.OnTheGoShop.Repo;

import com.project.OnTheGoShop.Beans.van_products;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

public interface van_prorepository extends CrudRepository<van_products, Integer> {
    public van_products findById(int id);

    public ArrayList<van_products> findAllById(int id);

    public List<van_products> findByVanid(int id);

    public ArrayList<van_products> findAllByVanid(int id);

    public van_products findByProductid(int id);

    public ArrayList<van_products> findAllByProductid(int id);

    public van_products findByAmount(int id);

    public ArrayList<van_products> findAllByAmount(int id);

    @Query(value = "select * from van_products where productid = ?2 and vanid = ?1", nativeQuery = true)
    public van_products findByVanIdAndProductId(int vanId, int productId);

    @Transactional
    @Modifying
    @Query("update van_products n set n.amount = ?2 where n.id = ?1")
    void updateamount(int id, int amount);


}
