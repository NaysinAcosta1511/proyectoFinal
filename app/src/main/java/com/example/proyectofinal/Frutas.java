package com.example.proyectofinal;

import java.io.Serializable;

public class Frutas implements Serializable {
    private int Id;
    private String Nombre;
    private String Descripcion;
    private String Url;


    public Frutas()
    {

    }

    public Frutas(int Id, String Nombre,String Descripcion,String Url)
    {
        this.setDescripcion(Descripcion);
        this.setId(Id);
        this.setNombre(Nombre);
        this.setUrl(Url);
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getUrl() {
        return Url;
    }
}



