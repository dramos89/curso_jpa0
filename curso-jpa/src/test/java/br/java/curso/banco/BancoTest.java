package br.java.curso.banco;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import br.java.curso.jpa.EntityManagerUtil;

public class BancoTest {

	@After
	public void close() {
		EntityManagerUtil.close();
	}

	@Test
	public void salvarBancoTeste() {
		BancoService bancoService = new BancoService();
		Banco bancoJP = new Banco();
		bancoJP.setNome("JPMorgan");

		Banco bancoInt = new Banco();
		bancoInt.setNome("Intermedium");

		bancoService.salvar(bancoJP);
		bancoService.salvar(bancoInt);

		Assert.assertNotNull("ID do " + bancoJP.getNome() + " não pode ser nulo.", bancoJP.getId());
		Assert.assertNotNull("ID do " + bancoInt.getNome() + " não pode ser nulo.", bancoInt.getId());
	}

	@Test
	public void buscarPorIdTeste() {
		BancoService bancoService = new BancoService();
		Banco bancoJP = new Banco();
		bancoJP.setNome("JPMorgan");

		bancoService.salvar(bancoJP);

		Banco bancoNaoEncontrado = bancoService.buscarPorId(-1L);
		Banco bancoEncontrado = bancoService.buscarPorId(bancoJP.getId());

		Assert.assertNull(bancoNaoEncontrado);
		Assert.assertNotNull(bancoEncontrado);
		Assert.assertEquals(bancoJP.getNome(), bancoEncontrado.getNome());
	}
}
