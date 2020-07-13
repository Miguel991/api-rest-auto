package api.rest.auto.repository;

import api.rest.auto.modelo.Automovil;
import api.rest.auto.statistics.EstadisticaModelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AutomovilRepository extends JpaRepository<Automovil, Long> {

}