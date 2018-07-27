package com.example.demo.dto;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
//We will need Discriminator annotation only if we are having inheritance=Single table
//@DiscriminatorValue("Car")
public class FourWheeler extends Vehicle {

	String steeringWheel;

	public String getSteeringWheel() {
		return steeringWheel;
	}

	public void setSteeringWheel(String steeringWheel) {
		this.steeringWheel = steeringWheel;
	}

}
