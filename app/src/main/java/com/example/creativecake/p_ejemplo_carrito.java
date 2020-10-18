package com.example.creativecake;

public class p_ejemplo_carrito {
    private int imagenProducto;
    private String nombreProducto;
    private String valorProducto;
    private String totalItems;

    public p_ejemplo_carrito(int imagenProducto, String nombreProducto, String valorProducto, String totalItems){
        this.imagenProducto = imagenProducto;
        this.nombreProducto = nombreProducto;
        this.valorProducto = valorProducto;
        this.totalItems = totalItems;
    }

    public int getImagenProducto() {
        return imagenProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public String getValorProducto() {
        return valorProducto;
    }

    public String getTotalItems() {
        return totalItems;
    }
}
