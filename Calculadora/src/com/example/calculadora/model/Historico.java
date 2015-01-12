package com.example.calculadora.model;

public class Historico {

	public String data;
	public String hora;
	public String operacao;
	
	@Override
	public String toString() {
		return data + " - " + hora + " : " + operacao;
	}
	
}
