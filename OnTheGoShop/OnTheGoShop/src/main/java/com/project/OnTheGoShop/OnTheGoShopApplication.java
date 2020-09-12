package com.project.OnTheGoShop;

import com.project.OnTheGoShop.BL.ManagerBL;
import com.project.OnTheGoShop.Beans.Manager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class OnTheGoShopApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(OnTheGoShopApplication.class, args);
        Manager manager = new Manager();
   //     manager.setID("32156416");
        manager.setName("karam abu alhija");
        manager.setPassword("there");
        manager.setPhonenumber("0546074508");
        manager.setUsername("gilgogog");
        ManagerBL managerBL = context.getBean(ManagerBL.class);
      //  managerBL.register(manager);
    }

}
