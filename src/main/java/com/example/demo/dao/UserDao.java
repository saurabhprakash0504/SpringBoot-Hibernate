package com.example.demo.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.User;

@Qualifier("UserDao")
@Repository
public class UserDao {

	public void insert() {
		User user = new User();
		user.setUserId(1);
		user.setUserName("saurabh");

		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.getSession().save(user);
		session.getTransaction().commit();
		session.close();
		sessionFactory.close();
	}

}
