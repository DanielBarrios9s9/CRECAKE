package com.example.creativecake;

public class CotizacionHelperClass {

    String nombre, decoracion, sabor, cobertura, tamaño, precio, fecha, hora, especificaciones;

    public CotizacionHelperClass(String nombre, String decoracion, String sabor, String cobertura,
    String tamaño, String precio, String fecha, String hora, String especificaciones){
        this.nombre = nombre;
        this.decoracion = decoracion;
        this.sabor = sabor;
        this.cobertura = cobertura;
        this.tamaño = tamaño;
        this.precio = precio;
        this.fecha = fecha;
        this.hora = hora;
        this.especificaciones = especificaciones;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDecoracion() {
        return decoracion;
    }

    public void setDecoracion(String decoracion) {
        this.decoracion = decoracion;
    }

    public String getSabor() {
        return sabor;
    }

    public void setSabor(String sabor) {
        this.sabor = sabor;
    }

    public String getCobertura() {
        return cobertura;
    }

    public void setCobertura(String cobertura) {
        this.cobertura = cobertura;
    }

    public String getTamaño() {
        return tamaño;
    }

    public void setTamaño(String tamaño) {
        this.tamaño = tamaño;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getEspecificaciones() {
        return especificaciones;
    }

    public void setEspecificaciones(String especificaciones) {
        this.especificaciones = especificaciones;
    }
}
