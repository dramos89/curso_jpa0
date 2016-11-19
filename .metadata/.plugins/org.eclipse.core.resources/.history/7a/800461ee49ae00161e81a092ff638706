package com.dextraining.garagem.dominio;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.dextraining.garagem.dominio.veiculo.Veiculo;
import com.dextraining.garagem.exception.VeiculoDuplicadoException;
import com.dextraining.garagem.exception.VeiculoNaoEncontradoException;
import com.dexraining.garagem.jpa.EntityManagerUtil;

public class GaragemJPA implements Garagem {
	private static final String ORDENACAO = "marca, modelo, ano, preco";

	@Override
	public void adicionar(Veiculo veiculo) {
		if (buscar(veiculo.getPlaca()) != null) {
			throw new VeiculoDuplicadoException("Placa do veículo a ser inserido já foi cadastrada!");
		}
		EntityManager em = EntityManagerUtil.criarEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(veiculo);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}
	}

	@Override
	public void vender(String placa) {
		Veiculo veiculoVendido = buscar(placa);
		EntityManager em = EntityManagerUtil.criarEntityManager();
		if (veiculoVendido == null) {
			throw new VeiculoNaoEncontradoException("Placa do veículo a ser vendido não existe!");
		}
		try {
			em.getTransaction().begin();
			em.remove(em.merge(veiculoVendido));
			em.getTransaction().commit();
		}catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}
	}

	@Override
	public Veiculo buscar(String placa) {
		EntityManager em = EntityManagerUtil.criarEntityManager();
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT v FROM Veiculo v");
		jpql.append(" WHERE UPPER(v.placa) = UPPER(:placa)");
		try {
			TypedQuery<Veiculo> query = em.createQuery(jpql.toString(), Veiculo.class);
			query.setParameter("placa", placa);
			return query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		} finally {
			em.close();
		}

	}

	@Override
	public List<Veiculo> listar() {
		EntityManager em = EntityManagerUtil.criarEntityManager();
		StringBuilder jpql = new StringBuilder();
		jpql.append("SELECT v FROM Veiculo v");
		jpql.append(" ORDER BY :ordem");
		try {
			TypedQuery<Veiculo> query = em.createQuery(jpql.toString(), Veiculo.class);
			query.setParameter("ordem", ORDENACAO);
			return query.getResultList();
		} finally {
			em.close();
		}
	}
}
