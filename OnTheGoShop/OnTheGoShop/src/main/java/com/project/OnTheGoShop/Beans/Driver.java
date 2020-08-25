package com.project.OnTheGoShop.Beans;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "Driver")
public class Driver extends Person {

    int vanNum;

    @Column
    public int getVanNum() {
        return vanNum;
    }

    public void setVanNum(int vanNum) {
        this.vanNum = vanNum;
    }

}
