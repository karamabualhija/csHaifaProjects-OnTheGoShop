package com.project.OnTheGoShop.BL;

import com.project.OnTheGoShop.Beans.User;
import com.project.OnTheGoShop.Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBL {

    @Autowired
    UserRepository userRepository;

    public boolean register(User user){
        List<User> users = userRepository.findById(user.getSys_id());
        if (users == null){
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
