package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity 
public class LoginUser {
	
	
	@Id
	private String lognome;
	
	
	public LoginUser() {
		this.lognome = "";
	}

	

	public String getNome() {
		return lognome;
	}

	public void setNome(String nome) {
		this.lognome = nome;
	}

	@Override
	public String toString() {
		return "LoginUser [nome=" + lognome + ", password=" + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
}