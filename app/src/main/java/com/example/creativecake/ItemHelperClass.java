package com.example.creativecake;

public class ItemHelperClass {
    private String producto;
    private String cantidad;
    private String tienda;
    private String direccionTienda;
    private String numeroTienda;
    private String usuario;
    private String direccionUsuario;
    private String numeroUsuario;
    private String imagen;
    private String oferta;
    private String precio;
    private String fecha;
    private String hora;
    private String lugar;

    public ItemHelperClass(){}

    public ItemHelperClass(String producto, String cantidad, String tienda, String direccionTienda,
                           String numeroTienda, String usuario, String direccionUsuario, String numeroUsuario,
                           String imagen, String oferta, String precio, String fecha, String hora) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.tienda = tienda;
        this.direccionTienda = direccionTienda;
        this.numeroTienda = numeroTienda;
        this.usuario = usuario;
        this.direccionUsuario = direccionUsuario;
        this.numeroUsuario = numeroUsuario;
        this.imagen = imagen;
        this.oferta = oferta;
        this.precio = precio;
        this.fecha=fecha;
        this.hora=hora;
    }


    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getDireccionUsuario() {
        return direccionUsuario;
    }

    public void setDireccionUsuario(String direccionUsuario) {
        this.direccionUsuario = direccionUsuario;
    }

    public String getNumeroUsuario() {
        return numeroUsuario;
    }

    public void setNumeroUsuario(String numeroUsuario) {
        this.numeroUsuario = numeroUsuario;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getOferta() {
        return oferta;
    }

    public void setOferta(String oferta) {
        this.oferta = oferta;
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

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }
}
