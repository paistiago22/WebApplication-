package com.example.demo;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity // This tells Hibernate to make a table out of this class
public class Topicos{
    
    @Id //id_consumidor is the primary key
    private int id_topico;
    private String nome_topico;
    /*
    @ManyToMany
    private List <Consumidor> consumidor;
    @OneToMany (cascade=CascadeType.ALL, mappedBy="id_topico")
    private List <Noticias> noticias;*/
 
    
    public Topicos() {     
    }
    
    @Override
    public String toString() {
        return "Tópicos [id_topico=" + id_topico + ", nome_topico=" + nome_topico +  "]";
    }


    public int getId_topico() {
        return id_topico;
    }


    public void setId_topico(int id_topico) {
        this.id_topico = id_topico;
    }


    public String getNome_topico() {
        return nome_topico;
    }


    public void setNome_topico(String nome_topico) {
        this.nome_topico = nome_topico;
    }


    
}
 