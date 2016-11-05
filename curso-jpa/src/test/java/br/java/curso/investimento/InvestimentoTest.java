package br.java.curso.investimento;

import java.util.List;
import java.util.Random;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import br.java.curso.exception.ValidacaoException;
import br.java.curso.jpa.EntityManagerUtil;

public class InvestimentoTest {

	@After
	public void close() {
		EntityManagerUtil.close();
	}

	@Test
	public void salvarComSucesso() {
		InvestimentoService service = new InvestimentoService();
		Investimento investimento = new Investimento();
		investimento.setDescricao("Tesouro Selic 2023");
		investimento.setRendimentoMensal(0.88);
		investimento.setValor(1500.00);
		service.salvar(investimento);

		Investimento buscaInvestimento = service.buscarPorId(investimento.getId());

		Assert.assertEquals(investimento.getId(), buscaInvestimento.getId());

	}

	@Test(expected = ValidacaoException.class)
	public void salvarValorComFalha() {
		InvestimentoService service = new InvestimentoService();
		Investimento investimento = new Investimento();
		investimento.setDescricao("Tesouro Selic 2023");
		investimento.setRendimentoMensal(0.88);
		// investimento.setValor(1500.00);
		service.salvar(investimento);
	}

	@Test(expected = ValidacaoException.class)
	public void salvarDescricaoComFalha() {
		InvestimentoService service = new InvestimentoService();
		Investimento investimento = new Investimento();
		//investimento.setDescricao("Tesouro Selic 2023");
		investimento.setRendimentoMensal(0.88);
		investimento.setValor(1500.00);
		service.salvar(investimento);
	}

	@Test(expected = ValidacaoException.class)
	public void salvarRendimentoMensalComFalha() {
		InvestimentoService service = new InvestimentoService();
		Investimento investimento = new Investimento();
		investimento.setDescricao("Tesouro Selic 2023");
		//investimento.setRendimentoMensal(0.88);
		investimento.setValor(1500.00);
		service.salvar(investimento);
	}
	
	@Test
	public void testarOrdenaInvestimentoDecrescente(){
		InvestimentoService service = new InvestimentoService();
		Investimento tSelic = new Investimento();
		Investimento tPrefix = new Investimento();
		Investimento tIPCA = new Investimento();
		tSelic.setDescricao("Tesouro Selic 2023");
		tSelic.setValor(100.00);
		tSelic.setRendimentoMensal(0.55);
		tPrefix.setDescricao("Tesouro Prefixado 2021");
		tPrefix.setValor(200.00);
		tPrefix.setRendimentoMensal(0.75);
		tIPCA.setDescricao("Tesouro Ipca+ 2019");
		tIPCA.setValor(300.00);
		tIPCA.setRendimentoMensal(0.65);

		service.salvar(tSelic);
		service.salvar(tPrefix);
		service.salvar(tIPCA);
		
		List<Investimento> retorno = service.ordenarInvestimento(false);
		//Assert.assertEquals(3, retorno.size());
		Assert.assertEquals(retorno.get(0).getValor(), tIPCA.getValor());
		Assert.assertEquals(retorno.get(1).getValor(), tPrefix.getValor());
		Assert.assertEquals(retorno.get(2).getValor(), tSelic.getValor());
	}
	
	@Test
	public void testarBuscarInvestimentosPorValor(){
		InvestimentoService service = new InvestimentoService();
		Investimento tSelic = new Investimento();
		Investimento tPrefix = new Investimento();
		Investimento tIPCA = new Investimento();
		tSelic.setDescricao("Tesouro Selic 2023");
		tSelic.setValor(1500.00);
		tSelic.setRendimentoMensal(0.55);
		tPrefix.setDescricao("Tesouro Prefixado 2021");
		tPrefix.setValor(2000.00);
		tPrefix.setRendimentoMensal(26.00);
		tIPCA.setDescricao("Tesouro Ipca+ 2019");
		tIPCA.setValor(300.00);
		tIPCA.setRendimentoMensal(0.65);

		service.salvar(tSelic);
		service.salvar(tPrefix);
		service.salvar(tIPCA);
		
		List<Investimento> retorno = service.retornaInvestimentos(1000.00, 25.00);
		for (Investimento investimento : retorno) {
			Assert.assertTrue((investimento.getRendimentoMensal() > 25.00 && investimento.getValor() >= 1000.00));
		}
	}
	
	@Test
	public void testaBuscaDescricao(){
		InvestimentoService service = new InvestimentoService();
		Investimento tSelic = new Investimento();
		Investimento tPrefix = new Investimento();
		Investimento tIPCA = new Investimento();
		tSelic.setDescricao("Tesouro Selic 2023");
		tSelic.setValor(1500.00);
		tSelic.setRendimentoMensal(0.55);
		tPrefix.setDescricao("Tesouro Prefixado 2021");
		tPrefix.setValor(2000.00);
		tPrefix.setRendimentoMensal(26.00);
		tIPCA.setDescricao("Ipca+ 2019");
		tIPCA.setValor(300.00);
		tIPCA.setRendimentoMensal(0.65);

		service.salvar(tSelic);
		service.salvar(tPrefix);
		service.salvar(tIPCA);
		
		List<Investimento> retorno = service.buscaDescricao("Tesouro");
		Assert.assertEquals(2, retorno.size());
	}
	
	public List<Investimento> geraInvestimento(){
		/*List<Investimento> inv;
		Random r = new Random();
		int qtd = r.nextInt(10);
		Double valor = r.nextDouble();
		for (int i = 0; i <= qtd; i++){
			inv.add(new Investimento());
			inv.get(i).setDescricao("Investimento "+i);
			inv.get(i).setValor(valor);
		}*/
		return null;
	}
	
	private void criarInvestimentos(int quantidade) {
		InvestimentoService investimentoService = new InvestimentoService();

		for (int i = 0; i < quantidade; i++) {
			Double valor = 1000 + new Random().nextDouble();
			Double rendimento = new Random().nextDouble();
			investimentoService.salvar(criarInvestimento("Investimento " + i, rendimento, valor));
		}
	}

	private Investimento criarInvestimento(String descricao, Double rendimento, Double valor) {
		Investimento investimento = new Investimento();
		investimento.setDescricao(descricao);
		investimento.setRendimentoMensal(rendimento);
		investimento.setValor(valor);
		return investimento;
	}
}
