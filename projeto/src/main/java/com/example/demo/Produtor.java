package com.example.demo;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity // This tells Hibernate to make a table out of this class

public class Produtor{
    
    @Id //id_consumidor is the primary key
    private int id_produtor;
    
    private String nome_produtor;
    /*
    @OneToMany(cascade=CascadeType.ALL, mappedBy="id_produtor")
    
    private List <Noticias> noticias;*/
 
    public Produtor() {
    }
    
    @Override
    public String toString() {
        return "Produtor [id_produtor=" + id_produtor + ", nome_produtor=" + nome_produtor + "]";
    }


    public int getId_produtor() {
        return id_produtor;
    }


    public void setId_produtor(int id_produtor) {
        this.id_produtor = id_produtor;
    }


    public String getNome_produtor() {
        return nome_produtor;
    }


    public void setNome_produtor(String nome_produtor) {
        this.nome_produtor = nome_produtor;
    }
    
}