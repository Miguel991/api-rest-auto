package api.rest.auto.modelo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "automoviles")
public class Automovil {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "modelo", nullable = false)
    private Modelo modelo;

    @ElementCollection
    private List<String> opcionales = new ArrayList<>();

    @Column
    private Double precio;

    public Automovil(){}

    public Automovil(Modelo modelo) {
        this.modelo = modelo;
        this.opcionales = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getModelo(){
        return this.modelo.value;
    }

    public void agregarOpcional(String opcional) {
        this.opcionales.add(opcional);
    }

    public List<String> getOpcionales() {
        return opcionales;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public void setOpcionales(List<String> opcionales) {
        this.opcionales = opcionales;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }
}
