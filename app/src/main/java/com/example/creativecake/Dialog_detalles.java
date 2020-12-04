package com.example.creativecake;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Dialog_detalles {
    CotizacionHelperClass cotizacion;
    String telefono;
    private Context globalContext = null;
    TextView nombreCoti, nombreCliente, saborCoti,tamañoCoti, coberturaCoti, decoracionCoti,especificoCoti, fechaCoti, horaCoti, dineroCoti, numeroCliente;

    public Dialog_detalles(final CotizacionHelperClass cotizacion, String telefono, final Context globalContext) {
        this.cotizacion = cotizacion;
        this.telefono = telefono;
        this.globalContext = globalContext;
        final Dialog dialog = new Dialog(globalContext);
        dialog.requestWindowFeature((Window.FEATURE_NO_TITLE));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_detalles);

        Button btn_aceptar = (Button) dialog.findViewById(R.id.btn_Aceptar);
        Button btn_volver = (Button) dialog.findViewById(R.id.btn_Volver);

        nombreCoti = (TextView) dialog.findViewById(R.id.nombrePedidoC);
        nombreCliente= (TextView) dialog.findViewById(R.id.nombreClienteC);
        saborCoti =(TextView) dialog.findViewById(R.id.saborCotizacionT);
        tamañoCoti=(TextView) dialog.findViewById(R.id.tamañoCotizacionT);
        coberturaCoti=(TextView) dialog.findViewById(R.id.coberturaCotizacionT);
        decoracionCoti=(TextView) dialog.findViewById(R.id.decoracionCotizacionT);
        especificoCoti=(TextView) dialog.findViewById(R.id.especificacionesCotizacionT);
        fechaCoti =(TextView) dialog.findViewById(R.id.fechaCotizacionT);
        horaCoti=(TextView) dialog.findViewById(R.id.horaCotizacionT);
        dineroCoti=(TextView) dialog.findViewById(R.id.dineroCotizacionT);
        numeroCliente=(TextView) dialog.findViewById(R.id.cotizacionNumeroC);

        nombreCoti.setText(cotizacion.getNombre());
        nombreCliente.setText(cotizacion.getNombreCliente());
        saborCoti.setText(cotizacion.getSabor());
        tamañoCoti.setText(cotizacion.getTamaño());
        coberturaCoti.setText(cotizacion.getCobertura());
        decoracionCoti.setText(cotizacion.getDecoracion());
        especificoCoti.setText(cotizacion.getEspecificaciones());
        fechaCoti.setText(cotizacion.getFecha());
        horaCoti.setText(cotizacion.getHora());
        dineroCoti.setText(cotizacion.getPrecio());
        numeroCliente.setText(cotizacion.getNumeroCliente());

        btn_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_confirmacion dialog_product = new Dialog_confirmacion(cotizacion, SharedPreferences_Util.getPhone_SP(globalContext), globalContext);
                dialog.dismiss();
            }
        });

        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
