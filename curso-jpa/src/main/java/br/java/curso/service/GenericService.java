package br.java.curso.service;

import java.text.MessageFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.java.curso.jpa.EntityManagerUtil;

public class GenericService<T> {
	private Class<T> targetClass;

	public GenericService(Class<T> targetClass) {
		this.targetClass = targetClass;
	}

	public void salvar(T entidade) {
		EntityManager em = EntityManagerUtil.createEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(entidade);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}
	}

	public T buscarPorId(Long id) {
		EntityManager em = EntityManagerUtil.createEntityManager();
		try {
			return em.find(this.targetClass, id);
		} finally {
			em.close();
		}
	}

	public List<T> buscarTodos() {
		EntityManager em = EntityManagerUtil.createEntityManager();
		String jpql = MessageFormat.format("FROM {0}  e", this.targetClass.getSimpleName());
		try {
			TypedQuery<T> query = em.createQuery(jpql, this.targetClass);
			return query.getResultList();
		} finally {
			em.close();
		}
	}
}
