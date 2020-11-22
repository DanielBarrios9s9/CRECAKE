package com.example.creativecake;

public class producto_ejemplo {
    private String cantidad;
    private String descripción;
    private String downloadUrl;
    private String nombre;
    private String oferta;
    private String precio;
    private String rating;
    private String tipo;
    private String user_name;

    public producto_ejemplo(){}

    public producto_ejemplo(String cantidad, String descripción,String downloadUrl,String nombre, String oferta, String precio, String rating, String tipo, String user_name) {
        this.cantidad= cantidad;
        this.descripción= descripción;
        this.downloadUrl=downloadUrl;
        this.nombre=nombre;
        this.oferta=oferta;
        this.precio=precio;
        this.rating=rating;
        this.tipo=tipo;
        this.user_name=user_name;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidadcantidad(String cantidadcantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripción() {
        return descripción;
    }

    public void setDescripción(String descripción) {
        this.descripción = descripción;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getOferta() {
        return oferta;
    }

    public void setOferta(String oferta) {
        this.oferta = oferta;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
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
        this.user_name = user_name;
    }
}
