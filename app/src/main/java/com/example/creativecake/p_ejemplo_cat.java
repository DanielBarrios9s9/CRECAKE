package com.example.creativecake;

public class p_ejemplo_cat {
    private int imagenProducto;
    private String nombreProducto;
    private String valorProducto;
    private String pasteleriaProducto;
    private double ratingProducto;

    public p_ejemplo_cat(int imagenProducto, String nombreProducto, String valorProducto, String pasteleriaProducto, double ratingProducto){
        this.imagenProducto = imagenProducto;
        this.nombreProducto = nombreProducto;
        this.valorProducto = valorProducto;
        this.pasteleriaProducto = pasteleriaProducto;
        this.ratingProducto = ratingProducto;
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

    public String getPasteleriaProducto() {
        return pasteleriaProducto;
    }

    public double getRatingProducto() {
        return ratingProducto;
    }
}
