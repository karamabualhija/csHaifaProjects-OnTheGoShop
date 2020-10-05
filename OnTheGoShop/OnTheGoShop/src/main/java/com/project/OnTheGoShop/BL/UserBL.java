package com.project.OnTheGoShop.BL;

import com.project.OnTheGoShop.Beans.Order;
import com.project.OnTheGoShop.Beans.User;
import com.project.OnTheGoShop.Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserBL {

    @Autowired
    UserRepository userRepository;

    public boolean register(User user){
       User users = userRepository.findById(user.getId());
        if (users == null){
            userRepository.save(user);
            return true;
        }
        return false;
    }

	public ArrayList<Order> finduserorders(int sys_id) {
		return (ArrayList<Order>) userRepository.findById(sys_id).getOrders();
	}

	public ArrayList<Order> finduserorders(String username) {
		return (ArrayList<Order>) userRepository.findByUsername(username).getOrders();
	}

	public ArrayList<User> findall() {
		return userRepository.findAll();

	}


}