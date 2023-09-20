package com.example.MyGShop.ui.shop;

import java.util.Date;

public class Juego {
    private String name;
    private int id;
    private String descripcion;
    private String image;
    private Date fechaCreacion;
    private int precio;

    public Juego(int id, String name, String descripcion, String image, Date fechaCreacion, Integer precio) {
        this.name = name;
        this.precio= precio;
        this.id = id;
        this.descripcion = descripcion;
        this.image = image;
        this.fechaCreacion = fechaCreacion;
    }

    public Juego(String name, String descripcion, String image, Date fechaCreacion) {
        this.name = name;
        this.descripcion = descripcion;
        this.image = image;
        this.fechaCreacion = fechaCreacion;
    }

    public Juego(String name, String descripcion, String image, Date fechaCreacion, int precio) {
        this.name = name;
        this.descripcion = descripcion;
        this.image = image;
        this.fechaCreacion = fechaCreacion;
        this.precio = precio;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public String toString() {
        return "Juego{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", image=" + image +
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }
}
