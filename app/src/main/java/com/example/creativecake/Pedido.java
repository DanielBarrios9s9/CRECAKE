package com.example.creativecake;

public class Pedido {

    private String cantidad;
    private String imagen;
    private String producto;
    private String oferta;
    private String precio;
    private String tienda;
    private String lugar;

    public Pedido(){}

    public Pedido(String cantidad,String imagen,String producto, String oferta, String precio, String tienda, String lugar) {
        this.cantidad= cantidad;
        this.imagen=imagen;
        this.producto=producto;
        this.oferta=oferta;
        this.precio=precio;
        this.tienda=tienda;
        this.lugar=lugar;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String downloadUrl) {
        this.imagen = downloadUrl;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
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

    public String getTienda() {
        return tienda;
    }

    public void setTienda(String tienda) {
        this.tienda = tienda;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }
}
