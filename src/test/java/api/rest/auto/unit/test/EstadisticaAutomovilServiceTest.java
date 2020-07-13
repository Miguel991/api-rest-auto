package api.rest.auto.unit.test;

import api.rest.auto.modelo.Statistics;
import api.rest.auto.repository.EstadisticasRepository;
import api.rest.auto.service.EstadisticaAutomovilService;
import api.rest.auto.statistics.EstadisticaModeloCustomResponse;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class EstadisticaAutomovilServiceTest {

    private static final int CINCUENTA_POR_CIENTO = 50;
    private static final int VEINTE_POR_CIENTO = 20;

    @Mock
    private EstadisticasRepository estadisticasRepository;

    @InjectMocks
    private EstadisticaAutomovilService estadisticaAutomovilService;

    //unitUndertest_scenario_expectedBehavior

    @Test
    public void obtenerEstadistica_existenDiezAutomovilesCreados_retornaObjetoConElNumeroDeAutomovilesCreados(){

        List<EstadisticaModeloCustomResponse> cantidadAutomovilesPorModelo = new ArrayList<>();
        cantidadAutomovilesPorModelo.add(new CustomResponseModelo("FAMILIAR", 10));

        Mockito.when(this.estadisticasRepository.obtenerNumeroDeAutomovilesExistentes()).thenReturn(10);
        Mockito.when(this.estadisticasRepository.obtenerCantidadDeAutomovilesPorModelo()).thenReturn(cantidadAutomovilesPorModelo);

        Statistics estadisticas = estadisticaAutomovilService.obtenerEstadisticas();

        Assertions.assertThat(estadisticas.getCount_car()).isEqualTo(10);

    }

    @Test
    public void obtenerEstadisticas_existenCincoAutomovilesCreados_retornaObjetoConElNumeroDeAutomovilesCreados(){

        List<EstadisticaModeloCustomResponse> cantidadAutomovilesPorModelo = new ArrayList<>();
        cantidadAutomovilesPorModelo.add(new CustomResponseModelo("FAMILIAR", 5));

        Mockito.when(this.estadisticasRepository.obtenerNumeroDeAutomovilesExistentes()).thenReturn(5);
        Mockito.when(this.estadisticasRepository.obtenerCantidadDeAutomovilesPorModelo()).thenReturn(cantidadAutomovilesPorModelo);

        Statistics estadisticas = estadisticaAutomovilService.obtenerEstadisticas();

        Assertions.assertThat(estadisticas.getCount_car()).isEqualTo(5);
    }

    @Test
    public void obtenerEstadisticas_existenDiezAutomovilesCincoSonModeloFamiliar_retornaElPorcentajeDeAutomovilesModeloFamiliar(){

        List<EstadisticaModeloCustomResponse> cantidadAutomovilesPorModelo = new ArrayList<>();
        cantidadAutomovilesPorModelo.add(new CustomResponseModelo("FAMILIAR", 5));
        cantidadAutomovilesPorModelo.add(new CustomResponseModelo("COUPE", 2));
        cantidadAutomovilesPorModelo.add(new CustomResponseModelo("SEDAN", 3));

        Mockito.when(this.estadisticasRepository.obtenerNumeroDeAutomovilesExistentes()).thenReturn(10);
        Mockito.when(this.estadisticasRepository.obtenerCantidadDeAutomovilesPorModelo()).thenReturn(cantidadAutomovilesPorModelo);

        Statistics expected = this.estadisticaAutomovilService.obtenerEstadisticas();

        Assertions.assertThat(expected.getPercent_family()).isEqualTo(CINCUENTA_POR_CIENTO);

    }

    @Test
    public void obtenerEstadisticas_existenDiezAutomovilesDosSonModeloFamiliar_retornaElPorcentajeDeAutomovilesModeloFamiliar(){

        Mockito.when(this.estadisticasRepository.obtenerNumeroDeAutomovilesExistentes()).thenReturn(10);

        List<EstadisticaModeloCustomResponse> cantidadAutomovilesPorModelo = new ArrayList<>();
        cantidadAutomovilesPorModelo.add(new CustomResponseModelo("FAMILIAR", 2));
        cantidadAutomovilesPorModelo.add(new CustomResponseModelo("COUPE", 3));
        cantidadAutomovilesPorModelo.add(new CustomResponseModelo("SEDAN", 5));

        Mockito.when(this.estadisticasRepository.obtenerCantidadDeAutomovilesPorModelo()).thenReturn(cantidadAutomovilesPorModelo);

        Statistics expected = this.estadisticaAutomovilService.obtenerEstadisticas();

        Assertions.assertThat(expected.getPercent_family()).isEqualTo(VEINTE_POR_CIENTO);

    }

    class CustomResponseModelo implements EstadisticaModeloCustomResponse {

        private String modelo;
        private int cantidad;

        public CustomResponseModelo(String modelo, int cantidad){
            this.modelo = modelo;
            this.cantidad = cantidad;
        }

        @Override
        public String getModelo() {
            return modelo;
        }

        @Override
        public int getCantidad() {
            return cantidad;
        }
    }

}
