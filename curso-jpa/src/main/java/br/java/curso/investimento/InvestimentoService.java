package br.java.curso.investimento;

import java.text.MessageFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import br.java.curso.exception.ValidacaoException;
import br.java.curso.jpa.EntityManagerUtil;
import br.java.curso.service.GenericService;

public class InvestimentoService extends GenericService<Investimento>{
	public InvestimentoService(){
		super(Investimento.class);
	}
	
	@Override
	public void salvar(Investimento investimento){
		if (investimento.getDescricao() == null){
			throw new ValidacaoException("Campo descricao não pode ser nulo.");
		}
		if (investimento.getRendimentoMensal() == null){
			throw new ValidacaoException("Campo rednimento mensal não pode ser nulo.");
		}
		if (investimento.getValor() == null){
			throw new ValidacaoException("Campo valor não pode ser nulo.");
		}

		super.salvar(investimento);
	}
	
	public List<Investimento> ordenarInvestimento(Boolean ordemCrescente){

		EntityManager em = EntityManagerUtil.createEntityManager();
		String jpql = MessageFormat.format("FROM {0}  e", Investimento.class.getSimpleName());
		String ordem = "";
		if (ordemCrescente == false)
			ordem = " DESC";
		jpql += "\nORDER BY e.valor"+ordem;
		try {
			return em.createQuery(jpql, Investimento.class)
			.getResultList();
		} finally {
			em.close();
		}
	}
	
	public List<Investimento> retornaInvestimentos(Double valorMinimo, Double rendimentoMinimo){
		EntityManager em = EntityManagerUtil.createEntityManager();
		String jpql = MessageFormat.format("FROM {0}  e", Investimento.class.getSimpleName());
		jpql += "\nWHERE e.valor >= :valorMinimo";
		jpql += "\nAND e.rendimentoMensal > :rendimentoMinimo";
		try {
			TypedQuery<Investimento> query = em.createQuery(jpql, Investimento.class);
			query.setParameter("valorMinimo", valorMinimo);
			query.setParameter("rendimentoMinimo", rendimentoMinimo);
			return query.getResultList();
		} finally {
			em.close();
		}
	}
	
	public List<Investimento> buscaDescricao(String descricao){
		EntityManager em = EntityManagerUtil.createEntityManager();
		String jpql = MessageFormat.format("FROM {0}  e", Investimento.class.getSimpleName());
		jpql += "\nWHERE UPPER(e.descricao) LIKE UPPER(:descricao)";
		try {
			return em.createQuery(jpql, Investimento.class)
			.setParameter("descricao", "%"+descricao+"%")
			.getResultList();
		} finally {
			em.close();
		}
	}
}
