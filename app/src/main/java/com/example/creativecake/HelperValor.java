package com.example.creativecake;

public class HelperValor {
    private String valor;
    private String subtotal;
    private String descuento;
    private String comision;
    private String confirmacion;

    public HelperValor(){}

    public HelperValor(String valor,String subtotal,String descuento, String comision, String confirmacion){
        this.comision=comision;
        this.valor=valor;
        this.subtotal=subtotal;
        this.descuento=descuento;
        this.confirmacion= confirmacion;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getDescuento() {
        return descuento;
    }

    public void setDescuento(String descuento) {
        this.descuento = descuento;
    }

    public String getComision() {
        return comision;
    }

    public void setComision(String comision) {
        this.comision = comision;
    }

    public String getConfirmacion() {
        return confirmacion;
    }

    public void setConfirmacion(String confirmacion) {
        this.confirmacion = confirmacion;
    }
}
