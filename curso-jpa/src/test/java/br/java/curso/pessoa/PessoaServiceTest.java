package br.java.curso.pessoa;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;


import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import br.java.curso.jpa.EntityManagerUtil;

public class PessoaServiceTest {

	@After
	public void close() {
		EntityManagerUtil.close();
	}

	@Test
	public void salvarBancoTeste() throws ParseException{
		PessoaService service = new PessoaService();
		Pessoa pessoa = new Pessoa();
		SimpleDateFormat dataFormat = new SimpleDateFormat("dd/MM/yyyy");

		pessoa.setNome("Juquinha");
		pessoa.setDataNasc(dataFormat.parse("22/02/1995"));

		service.salvar(pessoa);
		
		Pessoa outraPessoa = new Pessoa();
		outraPessoa.setNome("Juquinha");
		outraPessoa.setDataNasc(dataFormat.parse("22/02/1992"));

		service.salvar(outraPessoa);

		Assert.assertNotNull("ID da pessoa " + pessoa.getNome() + " não pode ser nulo.", pessoa.getId());
	}

	@Test
	public void buscarPessoaPorIdTeste(){
		PessoaService service = new PessoaService();
		Pessoa pessoa = new Pessoa();

		pessoa.setNome("Juquinha");
		
		service.salvar(pessoa);

		Pessoa pessoaNaoEncontrada = service.buscarPorId(-1L);
		Pessoa pessoaEncontrada = service.buscarPorId(pessoa.getId());

		Assert.assertNull(pessoaNaoEncontrada);
		Assert.assertNotNull(pessoaEncontrada);
		Assert.assertEquals(pessoa.getNome(),pessoaEncontrada.getNome());

	}
	
	@Test
	public void compararNomes(){
		PessoaService service = new PessoaService();
		Pessoa pessoa = new Pessoa();
		
		pessoa.setNome("Frederica");
		
		service.salvar(pessoa);
		
		Pessoa pessoa2 = service.buscarPorId(pessoa.getId());

		Assert.assertEquals(pessoa.getNome(), pessoa2.getNome());
		Assert.assertNotEquals(pessoa.getNome(), "Loreley");
	}
	
	@Test
	public void buscarTodosTeste() throws Throwable{
		PessoaService service = new PessoaService();
		Pessoa pessoa = new Pessoa();
		SimpleDateFormat dataFormat = new SimpleDateFormat("dd/MM/yyyy");

		pessoa.setNome("Juquinha");
		pessoa.setDataNasc(dataFormat.parse("22/02/1995"));
		
		List<Pessoa> pessoas = service.buscarTodos();
		
		Assert.assertEquals(1, pessoas.size());
	}
}
