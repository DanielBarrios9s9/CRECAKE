package com.example.creativecake;

public class PedidoTienda {
    private String imagen;
    private String nombre;
    private String cantidad;

    public PedidoTienda(String imagen, String nombre, String cantidad) {
        this.imagen = imagen;
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }
}
