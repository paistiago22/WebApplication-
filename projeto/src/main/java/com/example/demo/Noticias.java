package com.example.demo;

import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;


@Entity // This tells Hibernate to make a table out of this class
public class Noticias{
    
    @Id //id_consumidor is the primary key
    private int id_noticia;
    private int id_topico;
	private String titulo;
    private String corpo_noticia;
    private String data_publicacao;
    private int id_produtor;
    
    public int getId_topico() {
		return id_topico;
	}

	public void setId_topico(int id_topico) {
		this.id_topico = id_topico;
	}
    
    public int getId_produtor() {
		return id_produtor;
	}

	public void setId_produtor(int id_produtor) {
		this.id_produtor = id_produtor;
	}

	/*
    @ManyToOne
    private Topicos id_topico;
    @ManyToOne
    private Produtor id_produtor;
    */
    public Noticias(){
    }
    
   

    @Override
	public String toString() {
		return "Noticias [id_noticia=" + id_noticia + ", id_topico=" + id_topico + ", titulo=" + titulo
				+ ", corpo_noticia=" + corpo_noticia + ", data_publicacao=" + data_publicacao + ", id_produtor="
				+ id_produtor + "]";
	}

	public int getId_noticia() {
        return id_noticia;
    }


    public void setId_noticia(int id_noticia) {
        this.id_noticia = id_noticia;
    }


    public String getTitulo() {
        return titulo;
    }


    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }


    public String getCorpo_noticia() {
        return corpo_noticia;
    }


    public void setCorpo_noticia(String corpo_noticia) {
        this.corpo_noticia = corpo_noticia;
    }


    public String getData_publicacao() {
        return data_publicacao;
    }

	public void setData_publicacao(String data_publicacao) {
        this.data_publicacao = data_publicacao;
    }
}
