package com.example.creativecake;

public class DomiciliaryHelperClass
{
    String nombre, correo, cuenta, telefono, password;

    public DomiciliaryHelperClass() {
    }

    public DomiciliaryHelperClass(String nombre, String correo, String cuenta, String telefono, String password)
    {
        this.nombre = nombre;
        this.correo = correo;
        this.cuenta = cuenta;
        this.telefono = telefono;
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
