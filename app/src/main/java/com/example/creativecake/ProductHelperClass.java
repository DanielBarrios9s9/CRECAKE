package com.example.creativecake;

public class ProductHelperClass {
    String nombre, precio, descripción, tipo;

    public ProductHelperClass() {
    }

    public ProductHelperClass(String nombre, String precio, String descripción,
                              String tipo) {
        this.nombre = nombre;
        this.precio = precio;
        this.descripción = descripción;
        this.tipo = tipo;
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

    public void setTipo(String telefono) {
        this.tipo = tipo;
    }
}
