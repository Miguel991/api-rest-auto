package api.rest.auto.repository;

import api.rest.auto.statistics.EstadisticaModeloCustomResponse;
import api.rest.auto.statistics.EstadisticaOpcionalesCustomResponse;
import api.rest.auto.statistics.ModeloResponseQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstadisticasRepository extends JpaRepository<ModeloResponseQuery, Long> {

    @Query(value = "SELECT COUNT(*) AS total FROM automoviles", nativeQuery = true)
    int obtenerNumeroDeAutomovilesExistentes();

    @Query( value = "SELECT modelo, COUNT(*) AS cantidad FROM automoviles GROUP BY modelo ORDER BY modelo ASC", nativeQuery = true)
    List<EstadisticaModeloCustomResponse> obtenerCantidadDeAutomovilesPorModelo();

    @Query( value = "SELECT opcionales as opcional, COUNT(*) AS cantidad FROM automovil_opcionales GROUP BY opcionales", nativeQuery = true)
    List<EstadisticaOpcionalesCustomResponse> obtenerCantidadDeOpcionales();
}
