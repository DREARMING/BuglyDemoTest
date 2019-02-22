package com.mvcoder.buglydemotest.paging;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class StudentBean {

    private int id;
    private String name;
    @Generated(hash = 599421776)
    public StudentBean(int id, String name) {
        this.id = id;
        this.name = name;
    }
    @Generated(hash = 2097171990)
    public StudentBean() {
    }
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    
}
