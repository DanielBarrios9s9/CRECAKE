package com.example.creativecake;

public class ProductHelperClass {
    String nombre, precio, descripción, tipo, user_name, downloadUrl;
    String cantidad, oferta, estrellas;

    public ProductHelperClass() {
    }

    public ProductHelperClass(String nombre, String precio, String descripción,
                              String tipo, String user_name, String downloadUrl, String cantidad) {
        this.nombre = nombre;
        this.precio = precio;
        this.descripción = descripción;
        this.tipo = tipo;
        this.user_name = user_name;
        this.downloadUrl = downloadUrl;
        this.cantidad = cantidad;
        oferta = " ";
        estrellas = " ";
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getDescripción() {
        return descripción;
    }

    public void setDescripción(String descripción) {
        this.descripción = descripción;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.tipo = user_name;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getCantidadcantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getOferta() {
        return oferta;
    }

    public void setOferta(String oferta) {
        this.oferta = oferta;
    }

    public String getEstrellas() {
        return estrellas;
    }

    public void setEstrellas(String estrellas) {
        this.estrellas = estrellas;
    }

}
