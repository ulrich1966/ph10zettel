package de.juli.phaseten.controller;

import java.util.List;

import javax.persistence.EntityManager;

import de.juli.phaseten.model.Game;
import de.juli.phaseten.model.Model;
import de.juli.phaseten.model.PlaySession;
import de.juli.phaseten.model.Player;

public class Controller<T extends Model> {

	EntityManager em = MyEntittyManager.getInstance().getEm();

	private Model persist(Model model) {
		em.getTransaction().begin();
		em.persist(model);
		em.getTransaction().commit();
		return model;
	}
	
	public Model create(Model model){
		return persist(model);
	}


	public Model update(Model model) {
		em.merge(model);
		return model;
	}

	@SuppressWarnings("unchecked")
	public T findById(Long id, Class<T> clazz) {
		Model find = em.find(clazz, id);
		return (T) find;
	}

	public Model findFirst(String modelName) {
		return em.createNamedQuery(String.format("%s.findAll", modelName), Model.class).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll(String modelName) {
		return (List<T>) em.createNamedQuery(String.format("%s.findAll", modelName), Model.class).getResultList();
	}

	public Model findByName(String modelName, String name) {
		String querry = String.format("%s.findByName", modelName);
		return em.createNamedQuery(querry, Model.class).setParameter("name", name).getSingleResult();
	}

	public Model findByNumber(String modelName, Integer number) {
		String querry = String.format("%s.findByNumber", modelName);
		return em.createNamedQuery(querry, Model.class).setParameter("number", number).getSingleResult();
	}

	public Model findByWinner(String modelName, Player player) {
		String querry = String.format("%s.findByNumber", modelName);
		return em.createNamedQuery(querry, Model.class).setParameter("winner", player).getSingleResult();
	}

	public Model findByPlaySession(PlaySession session) {
		return em.createNamedQuery("Game.findByPlaySession", Game.class).setParameter("session", session).getSingleResult();
	}

	public boolean delete(Model model) {
		boolean success = false;
		try {
			if (!em.contains(model)) {
				em.merge(model);
			}
			em.getTransaction().begin();
			em.remove(model);
			em.getTransaction().commit();
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}
}
