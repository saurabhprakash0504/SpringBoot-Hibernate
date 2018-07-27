package com.example.demo.dto;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQuery;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)

//Hiberante Entity annotation will first trigger select query, if it finds that the there is a change 
//in the value then only it will perform the update operation.
@org.hibernate.annotations.Entity(selectBeforeUpdate = true)

//using NamedQuery we can segregate the queries at one place at the entity level
@NamedQuery(name = "personCRUD.byId", query = "from PersonCRUD where id=?")

//Using NamedNativeQuery we can write the sql query with no need to mention as the class name we can directly give the table name
@NamedNativeQuery(name = "personCRUD.byNativeId", query = "select * from PersonCRUD where id=?", resultClass = PersonCRUD.class)

public class PersonCRUD {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	String personName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

}
