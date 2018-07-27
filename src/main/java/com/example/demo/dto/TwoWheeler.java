package com.example.demo.dto;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
//We will need Discriminator annotation only if we are having inheritance=Single table
//@DiscriminatorValue("Bike")
public class TwoWheeler extends Vehicle{

	String steeringHandle;

	public String getSteeringHandle() {
		return steeringHandle;
	}

	public void setSteeringHandle(String steeringHandle) {
		this.steeringHandle = steeringHandle;
	}
	
}
