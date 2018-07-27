package com.example.demo.dto;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)

// We will need Discriminator annotation only if we are having inheritance=Single table 
//@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name="Vehicle_Type")
//@DiscriminatorValue("Generic_Vehicle")
public class Vehicle {
	@Id
	@GeneratedValue
	int vehicle_Id;

	String vehicleName;

	public int getVehicle_Id() {
		return vehicle_Id;
	}

	public void setVehicle_Id(int vehicle_Id) {
		this.vehicle_Id = vehicle_Id;
	}

	public String getVehicleName() {
		return vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

}
