package com.example.creativecake;

public class ClienteCotiHelper {
    private String tienda;
    private String direccionTienda;
    private String numeroTienda;
    private String nombreCotizacion;
    private String fechaEntrega;
    private String horaEntrega;
    private String precio;

    public ClienteCotiHelper() {}

    public ClienteCotiHelper(String tienda, String direccionTienda, String numeroTienda,
                             String nombreCotizacion, String fechaEntrega, String horaEntrega, String precio) {
        this.tienda = tienda;
        this.direccionTienda = direccionTienda;
        this.numeroTienda = numeroTienda;
        this.nombreCotizacion = nombreCotizacion;
        this.fechaEntrega = fechaEntrega;
        this.horaEntrega = horaEntrega;
        this.precio = precio;
    }

    public String getTienda() {
        return tienda;
    }

    public void setTienda(String tienda) {
        this.tienda = tienda;
    }

    public String getDireccionTienda() {
        return direccionTienda;
    }

    public void setDireccionTienda(String direccionTienda) {
        this.direccionTienda = direccionTienda;
    }

    public String getNumeroTienda() {
        return numeroTienda;
    }

    public void setNumeroTienda(String numeroTienda) {
        this.numeroTienda = numeroTienda;
    }

    public String getNombreCotizacion() {
        return nombreCotizacion;
    }

    public void setNombreCotizacion(String nombreCotizacion) {
        this.nombreCotizacion = nombreCotizacion;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getHoraEntrega() {
        return horaEntrega;
    }

    public void setHoraEntrega(String horaEntrega) {
        this.horaEntrega = horaEntrega;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }
}
