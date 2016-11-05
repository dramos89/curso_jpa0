package br.java.curso.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerUtil {
	private static EntityManagerFactory factory;

	public static EntityManager createEntityManager(){
		if (factory == null)
			factory = Persistence.createEntityManagerFactory("CURSO_JPA");
		return factory.createEntityManager();
	}
	
	public static EntityManager createEntityManager(String managerFactory){
		if (factory == null)
			factory = Persistence.createEntityManagerFactory(managerFactory);
		return factory.createEntityManager();
	}
	
	public static void close(){
		if(factory != null){
			factory.close();
			factory = null;
		}
	}
}
