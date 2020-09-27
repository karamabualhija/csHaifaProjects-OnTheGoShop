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
        List<User> users = userRepository.findBySys_id(user.getSys_id());
        if (users == null){
            userRepository.save(user);
            return true;
        }
        return false;
    }

	public ArrayList<Order> finduserorders(int sys_id) {
		return (ArrayList<Order>) userRepository.findBySys_id(sys_id).get(0).getOrders();
	}

	public ArrayList<Order> finduserorders(String username) {
		return (ArrayList<Order>) userRepository.findByUsername(username).getOrders();
	}

	public ArrayList<User> findall() {
		return userRepository.findAll();

	}


}
