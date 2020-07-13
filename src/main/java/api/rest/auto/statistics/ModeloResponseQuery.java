package api.rest.auto.statistics;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ModeloResponseQuery {

    @Id
    private String modelo;
    private int cantidad;
}
