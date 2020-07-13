package api.rest.auto.statistics;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class EstadisticaModelo {

    @Id
    private Long id;
    private int FAMILIAR;
    private int SEDAN;
    private int COUPE;

    public EstadisticaModelo(){}

    public EstadisticaModelo(int familiar, int sedan, int coupe) {
        this.FAMILIAR = familiar;
        this.SEDAN = sedan;
        this.COUPE = coupe;
    }

    public int getFAMILIAR() {
        return FAMILIAR;
    }

    public void setFAMILIAR(int FAMILIAR) {
        this.FAMILIAR = FAMILIAR;
    }

    public int getSEDAN() {
        return SEDAN;
    }

    public void setSEDAN(int SEDAN) {
        this.SEDAN = SEDAN;
    }

    public int getCOUPE() {
        return COUPE;
    }

    public void setCOUPE(int COUPE) {
        this.COUPE = COUPE;
    }
}
