package com.example.creativecake;

public class p_ejemplo_tienda {
    private String imagenProducto;
    private String nombreProducto;
    private String valorProducto;
    private String ofertaProducto;

    public p_ejemplo_tienda(String imagenProducto, String nombreProducto, String valorProducto, String ofertaProducto){
        this.imagenProducto = imagenProducto;
        this.nombreProducto = nombreProducto;
        this.valorProducto = valorProducto;
        this.ofertaProducto = ofertaProducto;
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

    public String getOfertaProducto() {
        return ofertaProducto;
    }
}
