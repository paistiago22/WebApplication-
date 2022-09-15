package com.example.demo;


import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

public class NoticiaList {
	
	
	private int id;
	private String topico;
	private String Titulo;
	private String conteudo;
	private String data;
	private String produtor;
	
	public NoticiaList() {
		this.id = -1;
		this.topico = "";
		this.conteudo = "";
		this.data = null;
		this.produtor = "";
		this.Titulo = "";
	}
	
	

	public String getTitulo() {
		return Titulo;
	}



	public void setTitulo(String titulo) {
		Titulo = titulo;
	}



	public NoticiaList(int id, String Titulo1 ,String topico, String conteudo, String data, String produtor) {
		this.id = id;
		this.Titulo = Titulo1;
		this.topico = topico;
		this.conteudo = conteudo;
		this.data = data;
		this.produtor = produtor;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTopico() {
		return topico;
	}

	public void setTopico(String topico) {
		this.topico = topico;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public String getProdutor() {
		return produtor;
	}

	public void setProdutor(String p) {
		this.produtor = p;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "NoticiaList [id=" + id + ", topico=" + topico + ", conteudo=" + conteudo + ", data=" + data
				+ ", produtor=" + produtor + "]";
	}
}