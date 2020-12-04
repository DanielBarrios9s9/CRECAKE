package com.example.creativecake;

public class CotizacionHelperClass {

    String nombre, decoracion, sabor, cobertura, tamaño,
            precio, fecha, hora, especificaciones, direccionCliente, numeroCliente, nombreCliente, estadoPago;

    public CotizacionHelperClass(String nombre, String decoracion, String sabor, String cobertura,
    String tamaño, String precio, String fecha, String hora, String direccionCliente, String numeroCliente, String nombreCliente, String especificaciones, String estadoPago){
        this.nombre = nombre;
        this.decoracion = decoracion;
        this.sabor = sabor;
        this.cobertura = cobertura;
        this.tamaño = tamaño;
        this.precio = precio;
        this.fecha = fecha;
        this.hora = hora;
        this.especificaciones = especificaciones;
        this.direccionCliente=direccionCliente;
        this.nombreCliente= nombreCliente;
        this.numeroCliente=numeroCliente;
        this.estadoPago=estadoPago;
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

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    public String getNumeroCliente() {
        return numeroCliente;
    }

    public void setNumeroCliente(String numeroCliente) {
        this.numeroCliente = numeroCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }
}
