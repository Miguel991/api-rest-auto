package api.rest.auto.service;

import api.rest.auto.modelo.Automovil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdministracionService {

    private Map<String, Integer> precios;

    public AdministracionService(){
        this.precios = obtenerPrecios();
    }

    private Map<String, Integer> obtenerPrecios() {

        Map<String, Integer> listaPrecios = new HashMap<>();
        listaPrecios.put("Sedan", 230000);
        listaPrecios.put("Familiar", 245000);
        listaPrecios.put("Coupe", 270000);

        listaPrecios.put("AB", 7000);
        listaPrecios.put("LL", 12000);
        listaPrecios.put("AA", 20000);
        listaPrecios.put("ABS", 14000);
        listaPrecios.put("TC", 12000);

        return listaPrecios ;
    }

    public Double calcularPrecioFinalAutomovil(Automovil automovil){

        double precioFinal = 0;
        double precioModelo = obtenerPrecioPorTipo(automovil.getModelo());
        double precioOpcionales = 0;

        for (String opcionales : automovil.getOpcionales()) {
            precioOpcionales = precioOpcionales + obtenerPrecioPorTipo(opcionales);
        }

        precioFinal = precioModelo + precioOpcionales;

        return precioFinal;
    }

    private int obtenerPrecioPorTipo(String tipo){
        return this.precios.get(tipo);
    }
}
