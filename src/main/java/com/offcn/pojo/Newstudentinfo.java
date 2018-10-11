package com.offcn.pojo;

import java.math.BigDecimal;

public class Newstudentinfo {
    private Integer id;

    private String name;

    private double score;

    private String phone;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

	@Override
	public String toString() {
		return "Newstudentinfo [id=" + id + ", name=" + name + ", score=" + score + ", phone=" + phone + "]";
	}
    
    
}