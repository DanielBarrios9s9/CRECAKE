package com.example.creativecake;

public class p_ejemplo_oferta {
    private String imagenProducto;
    private String nombreProducto;
    private String valorProducto;
    private String pasteleriaProducto;
    private String ofertaProducto;
    private String tipoProducto;
    private String ratingProducto;

    public p_ejemplo_oferta(String imagenProducto, String nombreProducto, String valorProducto, String pasteleriaProducto, String ofertaProducto, String tipoProducto, String ratingProducto){
        this.imagenProducto = imagenProducto;
        this.nombreProducto = nombreProducto;
        this.valorProducto = valorProducto;
        this.pasteleriaProducto = pasteleriaProducto;
        this.ofertaProducto=ofertaProducto;
        this.tipoProducto=tipoProducto;
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

    public String getOfertaProducto() {
        return ofertaProducto;
    }

    public String getTipoProducto() {
        return tipoProducto;
    }

    public String getRatingProducto() {
        return ratingProducto;
    }
}
