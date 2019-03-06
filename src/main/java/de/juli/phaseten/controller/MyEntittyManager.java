package de.juli.phaseten.controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class MyEntittyManager {
	private static MyEntittyManager manager;
	private EntityManagerFactory emf;
	private EntityManager em;
	
	private MyEntittyManager() {
		super();
		emf = Persistence.createEntityManagerFactory("pu");
		em = emf.createEntityManager();
	}
	
	public static MyEntittyManager getInstance() {
		if(manager == null) {
			manager = new MyEntittyManager();
		}
		return manager;
	}
	
	public EntityManager getEm() {
		return em;
	}
	public void setEm(EntityManager em) {
		this.em = em;
	}
	
}
