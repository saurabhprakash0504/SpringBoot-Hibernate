package com.example.demo.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

//This tells that the particular class is entity and hibernate has to save it
@Entity
public class User {

	// @Id tells to treat the particular field as primary key of the table.
	@Id
	@GeneratedValue
	@Column(name="USER_ID")
	private int userId;

	private String userName;
	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "city", column = @Column(name = "office_city")),
			@AttributeOverride(name = "pincode", column = @Column(name = "office_pincode")) })

	private Address officeAddress;
	@Embedded
	private Address homeAddress;

	@Temporal(TemporalType.DATE)
	private Date userJoiningDate;

	@Lob
	private String userDescription;

	@JoinTable(name="AddressSet")
	@ElementCollection(fetch=FetchType.EAGER)
	@GenericGenerator(name="hilo-gen",strategy="increment")
	@CollectionId(columns= {@Column(name="Address_ID")},generator="hilo-gen",type=@Type(type="long"))
	Collection<Address> addressSet =new ArrayList<>();
	
	@OneToOne
	@JoinColumn(name="Vehicle_ID")
	private Vehicle vehicle;
	
	@ManyToMany
	Collection<Department> departmentList=new ArrayList<>();
	
	
	
	public Collection<Department> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(Collection<Department> departmentList) {
		this.departmentList = departmentList;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public Collection<Address> getAddressSet() {
		return addressSet;
	}

	public void setAddressSet(Collection<Address> addressSet) {
		this.addressSet = addressSet;
	}

	public Address getOfficeAddress() {
		return officeAddress;
	}

	public void setOfficeAddress(Address officeAddress) {
		this.officeAddress = officeAddress;
	}

	public Address getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(Address homeAddress) {
		this.homeAddress = homeAddress;
	}

	public Date getUserJoiningDate() {
		return userJoiningDate;
	}

	public void setUserJoiningDate(Date userJoiningDate) {
		this.userJoiningDate = userJoiningDate;
	}

	public String getUserDescription() {
		return userDescription;
	}

	public void setUserDescription(String userDescription) {
		this.userDescription = userDescription;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
