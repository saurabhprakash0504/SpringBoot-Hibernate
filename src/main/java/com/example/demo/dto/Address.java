package com.example.demo.dto;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
	String city;
	int pincode;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getPincode() {
		return pincode;
	}

	public void setPincode(int pincode) {
		this.pincode = pincode;
	}
}
