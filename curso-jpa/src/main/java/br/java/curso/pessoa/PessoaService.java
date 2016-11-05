package br.java.curso.pessoa;

import br.java.curso.service.GenericService;

public class PessoaService extends GenericService<Pessoa>{
	public PessoaService(){
		super(Pessoa.class);
	}
}
