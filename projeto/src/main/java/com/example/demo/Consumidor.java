package com.example.demo;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity // This tells Hibernate to make a table out of this class

public class Consumidor{
    
    @Id //id_consumidor is the primary key
    private int id_consumidor;

    private String nome_consumidor;

    private String morada;

    private String password;

    private int contacto;
/*
   @ManyToMany
   private List <Topicos> topicos;*/
   
    public Consumidor() {
    }

	public int getId_consumidor() {
		return id_consumidor;
	}

	public void setId_consumidor(int id_consumidor) {
		this.id_consumidor = id_consumidor;
	}

	public String getNome_consumidor() {
		return nome_consumidor;
	}

	public void setNome_consumidor(String nome_consumidor) {
		this.nome_consumidor = nome_consumidor;
	}

	public String getMorada() {
		return morada;
	}

	public void setMorada(String morada) {
		this.morada = morada;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getContacto() {
		return contacto;
	}

	public void setContacto(int contacto) {
		this.contacto = contacto;
	}

	@Override
	public String toString() {
		return "Consumidor [id_consumidor=" + id_consumidor + ", nome_consumidor=" + nome_consumidor + ", morada="
				+ morada + ", password=" + password + ", contacto=" + contacto + "]";
	}

 
}