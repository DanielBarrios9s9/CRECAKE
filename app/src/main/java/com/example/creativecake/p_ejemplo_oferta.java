package com.example.creativecake;

public class p_ejemplo_oferta {
    private int imagenProducto;
    private String nombreProducto;
    private String valorProducto;
    private String pasteleriaProducto;
    private String ofertaProducto;
    private double ratingProducto;

    public p_ejemplo_oferta(int imagenProducto, String nombreProducto, String valorProducto, String pasteleriaProducto, String ofertaProducto, double ratingProducto){
        this.imagenProducto = imagenProducto;
        this.nombreProducto = nombreProducto;
        this.valorProducto = valorProducto;
        this.pasteleriaProducto = pasteleriaProducto;
        this.ofertaProducto=ofertaProducto;
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

    public String getOfertaProducto() {
        return ofertaProducto;
    }

    public double getRatingProducto() {
        return ratingProducto;
    }
}
