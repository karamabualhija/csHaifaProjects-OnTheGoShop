package com.project.OnTheGoShop.BL;

import com.project.OnTheGoShop.Beans.Order;
import com.project.OnTheGoShop.Beans.User;
import com.project.OnTheGoShop.Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

	public List<Order> finduserorders(int sys_id) {
		return userRepository.findById(sys_id).getOrders();
	}

	public List<Order> finduserorders(String username) {
		return userRepository.findByUsername(username).getOrders();
	}

	public ArrayList<User> findall() {
		return userRepository.findAll();

	}

	public void add(User d) {
		userRepository.save(d);
	}

	public User findbyusername(String username) {
		return userRepository.findByUsername(username);
	}


}