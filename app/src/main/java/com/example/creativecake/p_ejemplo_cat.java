package com.example.creativecake;

public class p_ejemplo_cat {
    private String imagenProducto;
    private String nombreProducto;
    private String valorProducto;
    private String pasteleriaProducto;
    private String tipoProducto;
    private String ratingProducto;

    public p_ejemplo_cat(String imagenProducto, String nombreProducto, String valorProducto, String pasteleriaProducto, String tipoProducto, String ratingProducto){
        this.imagenProducto = imagenProducto;
        this.nombreProducto = nombreProducto;
        this.valorProducto = valorProducto;
        this.pasteleriaProducto = pasteleriaProducto;
        this.tipoProducto= tipoProducto;
        this.ratingProducto = ratingProducto;
    }

    public String getImagenProducto() {
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

    public String getTipoProducto() {
        return tipoProducto;
    }

    public String getRatingProducto() {
        return ratingProducto;
    }
}
