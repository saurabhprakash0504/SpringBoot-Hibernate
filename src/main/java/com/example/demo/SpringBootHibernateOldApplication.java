package com.example.demo;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.dto.Address;
import com.example.demo.dto.Department;
import com.example.demo.dto.FourWheeler;
import com.example.demo.dto.PersonCRUD;
import com.example.demo.dto.TwoWheeler;
import com.example.demo.dto.User;
import com.example.demo.dto.Vehicle;

@SpringBootApplication
public class SpringBootHibernateOldApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootHibernateOldApplication.class, args);

		Address homeAddress = new Address();
		homeAddress.setCity("Bangalore");
		homeAddress.setPincode(1111);

		Address officeAddress = new Address();
		officeAddress.setCity("Bangalore");
		officeAddress.setPincode(1111);

		Vehicle vehicle = new Vehicle();
		vehicle.setVehicleName("Vespa");

		Department dept1 = new Department();
		dept1.setDepartmentName("Maths");

		Department dept2 = new Department();
		dept2.setDepartmentName("Science");

		FourWheeler fourWheeler = new FourWheeler();
		fourWheeler.setSteeringWheel("CarSteering");

		TwoWheeler twoWheeler = new TwoWheeler();
		twoWheeler.setSteeringHandle("BikeSteering");

		User user = new User();
		user.setUserName("prakash");
		user.setHomeAddress(homeAddress);
		user.setOfficeAddress(officeAddress);
		user.setUserDescription(" prakash description ");
		user.setUserJoiningDate(new Date());
		user.getAddressSet().add(homeAddress);
		user.getAddressSet().add(officeAddress);
		user.setVehicle(vehicle);
		user.getDepartmentList().add(dept1);
		user.getDepartmentList().add(dept2);

		dept1.getUserList().add(user);
		dept2.getUserList().add(user);

		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.save(user);
		session.save(vehicle);
		session.save(dept1);
		session.save(dept2);
		session.save(twoWheeler);
		session.save(fourWheeler);
		session.getTransaction().commit();
		session.close();
		System.out.println(">>>>> values commited >>>>");
		System.out.println("\n\n\n\n");

		user = null;
		session = sessionFactory.openSession();
		session.beginTransaction();
		user = (User) session.get(User.class, 1);

		// Testing lazy and eager loading
		session.close();
		System.out.println("size  >>> " + user.getAddressSet().size());

		// CRUD operations
		Session session2 = sessionFactory.openSession();
		session2.beginTransaction();
		// Create operation
		for (int i = 1; i <= 10; i++) {
			PersonCRUD personCrud = new PersonCRUD();
			personCrud.setPersonName("Name " + i);
			session2.save(personCrud);
			personCrud.setPersonName("Namess " + i);
		}
		session2.getTransaction().commit();
		session2.close();

		Session session3 = sessionFactory.openSession();
		session3.beginTransaction();

		// Read
		PersonCRUD person = (PersonCRUD) session3.get(PersonCRUD.class, 5);
		System.out.println("Name retrieved " + person.getPersonName());

		// Update
		PersonCRUD person2 = (PersonCRUD) session3.get(PersonCRUD.class, 6);
		person2.setPersonName("updated name");
		session3.update(person2);

		// Delete
		PersonCRUD person3 = (PersonCRUD) session3.get(PersonCRUD.class, 7);
		session3.delete(person3);

		session3.getTransaction().commit();
		session3.close();

		// Video-26
		// Persistent after detaching
		// NO session active at this point but still the value will get updated after
		// the next session
		person.setPersonName("saurabh");

		Session session5 = sessionFactory.openSession();
		session5.beginTransaction();
		session5.update(person);
		session5.getTransaction().commit();
		session5.close();

		// Video-27
		// writing HQL
		session5 = sessionFactory.openSession();
		session5.beginTransaction();
		Query query = session5.createQuery("from PersonCRUD");

		// video-28
		// this is used for paginitiation
		query.setFirstResult(4);
		query.setMaxResults(2);
		// paginitiation ends

		List list = query.list();

		System.out.println("qery size >> " + list.size());
		session5.getTransaction().commit();
		session5.close();

		// video-29
		// Parameter binding
		session5 = sessionFactory.openSession();
		session5.beginTransaction();
		String id = "7";
		String personName = "Namess 8";

		// first way is by using a ?
		query = session5.createQuery("select id from PersonCRUD where id > ? and personName = ?");
		query.setInteger(0, Integer.parseInt(id));
		query.setString(1, personName);
		List<Integer> ids = query.list();
		for (Integer i : ids) {
			System.out.println("Id is  >>> " + i);
		}

		// second way is by using a ":"
		query = session5.createQuery("select id from PersonCRUD where id > :id and personName = :name ");
		query.setInteger("id", Integer.parseInt(id));
		query.setString("name", personName);
		List<Integer> idss = query.list();
		for (Integer i : idss) {
			System.out.println("Second Id is  >>> " + i);
		}
		session5.getTransaction().commit();
		session5.close();

		// video -30
		// NamedQuery
		// second way is by using a ":"
		session5 = sessionFactory.openSession();
		session5.beginTransaction();
		query = session5.getNamedQuery("personCRUD.byId");
		query.setInteger(0, 8);
		List<PersonCRUD> idsss = (List<PersonCRUD>) query.list();
		for (PersonCRUD i : idsss) {
			System.out.println("Named Query Id is  >>> " + i.getId());
			System.out.println("Named Query name is  >>> " + i.getPersonName());
		}
		session5.getTransaction().commit();
		session5.close();

		// video-30
		// NamedNativeQuery
		// second way is by using a ":"
		session5 = sessionFactory.openSession();
		session5.beginTransaction();
		query = session5.getNamedQuery("personCRUD.byNativeId");
		query.setInteger(0, 9);
		List<PersonCRUD> idssss = (List<PersonCRUD>) query.list();
		for (PersonCRUD i : idssss) {
			System.out.println("Named native Query Id is  >>> " + i.getId());
			System.out.println("Named native Query name is  >>> " + i.getPersonName());
		}
		session5.getTransaction().commit();
		session5.close();

		// Video-31 and Video-32
		// criteria Example
		session5 = sessionFactory.openSession();
		session5.beginTransaction();
		Criteria criteria = session5.createCriteria(PersonCRUD.class);
		/*
		 * criteria.add(Restrictions.le("id", 5)) .add(Restrictions.gt("id", 1));
		 */

		// This way we can give OR condition in Restrictions
		criteria.add(Restrictions.or(Restrictions.eq("id", 5), Restrictions.eq("id", 15)));

		List<PersonCRUD> personCRUD = (List<PersonCRUD>) criteria.list();
		for (PersonCRUD i : personCRUD) {
			System.out.println("Criteria Id is  >>> " + i.getId());
			System.out.println("Criteria name is  >>> " + i.getPersonName());
		}
		session5.getTransaction().commit();
		session5.close();

		// Video-33 ProjectionsExamplw
		session5 = sessionFactory.openSession();
		session5.beginTransaction();
		Criteria criterias = session5.createCriteria(PersonCRUD.class).setProjection(Projections.max("id"));
		List<Integer> maxId = (List<Integer>) criterias.list();
		for (Integer i : maxId) {
			System.out.println("Max Id using projection  >>> " + i);
		}
		session5.getTransaction().commit();
		session5.close();

		// Video-33 Example
		// Example does not work on null and primary key column
		session5 = sessionFactory.openSession();
		session5.beginTransaction();

		PersonCRUD persons = new PersonCRUD();
		persons.setId(1);
		persons.setPersonName("Namess 1");

		Example example = Example.create(persons).excludeProperty("id");

		Criteria criteriass = session5.createCriteria(PersonCRUD.class).add(example);
		List<PersonCRUD> personCRUDD = (List<PersonCRUD>) criteriass.list();
		for (PersonCRUD i : personCRUDD) {
			System.out.println(" Id using example  >>> " + i.getId());
			System.out.println(" Name using example  >>> " + i.getPersonName());
		}
		session5.getTransaction().commit();
		session5.close();

		System.out.println("\n\n  caching example  \n\n");

		// Video-34 First level Cache(Demo)
		// we can see that the query is executed only once , second time the query is
		// not executed.
		session5 = sessionFactory.openSession();
		session5.beginTransaction();
		System.out.println("\n\n First time");
		PersonCRUD queryy = session5.get(PersonCRUD.class, 3);

		/*
		 * System.out.println("<<<<<<<<<<>>>>>>>>>>"); queryy =
		 * session5.get(PersonCRUD.class, 3);
		 * System.out.println(" Id using example  >>> " + queryy.getId());
		 * System.out.println(" Name using example  >>> " + queryy.getPersonName());
		 */

		session5.getTransaction().commit();
		session5.close();

		// Video-35 Second level cache
		// (Second Level Cache is not working).. NEED TO LOOK

		Session session55 = sessionFactory.openSession();
		session55.beginTransaction();

		// System.out.println("Yippee again query ");
		PersonCRUD queryyy = session55.get(PersonCRUD.class, 3);

		session55.getTransaction().commit();
		session55.close();

		// video-36 query cache
		// Not Working.... will have to see


		Session session10 = sessionFactory.openSession();
		session10.beginTransaction();

		Query query10 = session10.createQuery("select personName from PersonCRUD where id= 1 ");
		query10.setCacheable(true);
		List list10 = query10.list();
		session10.getTransaction().commit();
		session10.close();

		Session session11 = sessionFactory.openSession();
		session11.beginTransaction();

		Query query11 = session11.createQuery("select personName from PersonCRUD where id= 1 ");
		query11.setCacheable(true);
		List list11 = query11.list();
		session11.getTransaction().commit();
		session11.close();
	}
}
