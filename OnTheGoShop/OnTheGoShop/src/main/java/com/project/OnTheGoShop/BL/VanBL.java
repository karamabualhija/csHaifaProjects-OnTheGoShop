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
	

}
