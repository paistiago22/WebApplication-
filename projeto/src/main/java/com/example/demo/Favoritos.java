package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
	public class Favoritos {

	@Id //id_consumidor is the primary key
    private int id_fav;

    private int id_consumidor;

    private int topico;
	
    public Favoritos() {
    }

	public int getId_fav() {
		return id_fav;
	}

	public void setId_fav(int id_fav) {
		this.id_fav = id_fav;
	}

	public int getId_consumidor() {
		return id_consumidor;
	}

	public void setId_consumidor(int id_consumidor) {
		this.id_consumidor = id_consumidor;
	}

	public int getTopico() {
		return topico;
	}

	public void setTopico(int topico) {
		this.topico = topico;
	}
    
    
}