package com.example.creativecake;

public class producto_carrito {
    private String cantidad;
    private String nom_usuario;
    private String dir_usuario;;
    private String telefono;
    private String key;

    public producto_carrito(){}

    public producto_carrito(String cantidad,String nom_usuario,String dir_usuario, String telefono, String key) {
        this.cantidad= cantidad;
        this.nom_usuario=nom_usuario;
        this.dir_usuario=dir_usuario;
        this.telefono = telefono;
        this.key = key;

    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getNom_usuario() {
        return nom_usuario;
    }

    public void setNom_usuario(String nom_usuario) {
        this.nom_usuario = nom_usuario;
    }

    public String getDir_usuario() {
        return dir_usuario;
    }

    public void setDir_usuario(String dir_usuario) {
        this.dir_usuario = dir_usuario;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
