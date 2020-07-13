package api.rest.auto.service;

import api.rest.auto.modelo.Statistics;
import api.rest.auto.repository.EstadisticasRepository;
import api.rest.auto.statistics.EstadisticaModeloCustomResponse;
import api.rest.auto.statistics.EstadisticaOpcionalesCustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadisticaAutomovilService {

    private EstadisticasRepository estadisticasRepository;

    @Autowired
    public EstadisticaAutomovilService(EstadisticasRepository estadisticasRepository){
        this.estadisticasRepository = estadisticasRepository;
    }

    public Statistics obtenerEstadisticas() {

        Statistics estadisticas = calcularEstadisticas();

        return estadisticas;
    }

    private Statistics calcularEstadisticas() {

        Statistics estadisticas = new Statistics();

        int numeroTotalDeAutomoviles = this.estadisticasRepository.obtenerNumeroDeAutomovilesExistentes();
        estadisticas.setCount_car(numeroTotalDeAutomoviles);

        agregarEstadisticasDeAutomovilesPorModelo(estadisticas);
        agregarEstadisticasDeOpcionales(estadisticas);

        return estadisticas;
    }

    private void agregarEstadisticasDeOpcionales(Statistics estadisticas) {

        List<EstadisticaOpcionalesCustomResponse> cantidadOpcionales = this.estadisticasRepository.obtenerCantidadDeOpcionales();

        int totalAutomoviles = estadisticas.getCount_car();

        for (int i = 0; i < cantidadOpcionales.size(); i++) {
            int cantidad = cantidadOpcionales.get(i).getCantidad();
            String opcional = cantidadOpcionales.get(i).getOpcional();

            if(opcional.equals("ABS")){
                estadisticas.setCount_abs(cantidad);
                estadisticas.setPercent_abs(calcularPorcentaje(totalAutomoviles, cantidad));
            }
            if(opcional.equals("AA")){
                estadisticas.setCount_aa(cantidad);
                estadisticas.setPercent_aa(calcularPorcentaje(totalAutomoviles, cantidad));
            }
            if(opcional.equals("AB")){
                estadisticas.setCount_ab(cantidad);
                estadisticas.setPercent_ab(calcularPorcentaje(totalAutomoviles, cantidad));
            }
            if(opcional.equals("TC")){
                estadisticas.setCount_tc(cantidad);
                estadisticas.setPercent_tc(calcularPorcentaje(totalAutomoviles, cantidad));
            }
            if(opcional.equals("LL")){
                estadisticas.setCount_ll(cantidad);
                estadisticas.setPercent_ll(calcularPorcentaje(totalAutomoviles, cantidad));
            }

        }

    }

    private void agregarEstadisticasDeAutomovilesPorModelo(Statistics estadisticas) {
        List<EstadisticaModeloCustomResponse> cantidadModelos = this.estadisticasRepository.obtenerCantidadDeAutomovilesPorModelo();

        int totalAutomoviles = estadisticas.getCount_car();

        for (int i = 0; i < cantidadModelos.size() ; i++) {

            int cantidad = cantidadModelos.get(i).getCantidad();
            String modelo = cantidadModelos.get(i).getModelo();

            if(modelo.equals("FAMILIAR")){
                estadisticas.setCount_family(cantidad);
                estadisticas.setPercent_family(calcularPorcentaje(totalAutomoviles,cantidad));
            }
            if(modelo.equals("COUPE")){
                estadisticas.setCount_coupe(cantidad);
                estadisticas.setPercent_coupe(calcularPorcentaje(totalAutomoviles,cantidad));
            }
            if(modelo.equals("SEDAN")){
                estadisticas.setCount_sedan(cantidad);
                estadisticas.setPercent_sedan(calcularPorcentaje(totalAutomoviles,cantidad));
            }
        }
    }

    private float calcularPorcentaje(float total, float cantidad) {

        return (cantidad * 100)/total;
    }
}
