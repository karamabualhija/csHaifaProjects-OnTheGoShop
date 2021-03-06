package com.project.OnTheGoShop.BL;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.OnTheGoShop.Beans.Van;
import com.project.OnTheGoShop.Repo.VanRepository;
@Service
public class VanBL {
	@Autowired
	VanRepository vanRepository;

	public ArrayList<Van> findallvans() {

		return  vanRepository.findAll();
	}

	public Van findvan(int id) {
		return vanRepository.findById(id);
	}

	public void updatelocation(String lag, String lan, int id) {
		vanRepository.Updatelocation(id, lan, lag);		
	}

	public void orderreceived(int van_id, int order_id) {
		
		Van v=vanRepository.findById(van_id);
		v.removeorder(order_id);
		vanRepository.save(v);
	}
	
	public void add(Van van){
		vanRepository.save(van);
	}

}
