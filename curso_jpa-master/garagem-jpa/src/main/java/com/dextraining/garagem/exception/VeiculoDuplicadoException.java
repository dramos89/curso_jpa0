package com.dextraining.garagem.exception;

public class VeiculoDuplicadoException extends RuntimeException {
	public VeiculoDuplicadoException(String mensagem){
		super(mensagem);
	}

	private static final long serialVersionUID = 1388364066154632921L;
}
