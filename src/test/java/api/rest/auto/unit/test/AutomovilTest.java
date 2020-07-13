package api.rest.auto.unit.test;

import api.rest.auto.modelo.Automovil;
import api.rest.auto.modelo.Modelo;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

public class AutomovilTest {

    private Automovil automovil = null;

    @Before
    public void setup(){
        automovil = new Automovil(Modelo.FAMILIAR);
    }

    @Test
    public void constructor_CrearAutoMovilSedan_RetornaAutomovilModeloSedan() {
        this.automovil = new Automovil(Modelo.SEDAN);
        Assertions.assertThat(automovil.getModelo()).isEqualTo("Sedan");
    }

    @Test
    public void constructor_crearAutoMovilFamiliar_retornaAutomovilModeloFamiliar() {
        Assertions.assertThat(automovil.getModelo()).isEqualTo("Familiar");
    }

    @Test
    public void constructor_crearAutoMovilCoupe_retornaAutomovilModeloCoupe() {
        this.automovil = new Automovil(Modelo.COUPE);
        Assertions.assertThat(automovil.getModelo()).isEqualTo("Coupe");
    }

    @Test
    public void agregarOpcional_agregarAirbag_retornaAutomovilFamiliarConAirbag(){

        automovil.agregarOpcional("Airbag");

        Assertions.assertThat(automovil.getOpcionales()).contains("Airbag");
    }

    @Test
    public void agregarOpcional_agregarLLantasYAirbag_retornaAutomovilFamiliarConAirbagYLLantas(){

        automovil.agregarOpcional("Airbag");
        automovil.agregarOpcional("Llantas");

        Assertions.assertThat(automovil.getOpcionales()).contains("Airbag","Llantas");
    }

    @Test
    public void agregarOpcional_agregarAirbagLlantasAireAcondicionadoTechoCorredizoABS_retornaAutomovilFamiliarConAirbagLlantasABSAireAcondicionadoTechoCorredizo(){

        automovil.agregarOpcional("Airbag");
        automovil.agregarOpcional("Llantas");
        automovil.agregarOpcional("ABS");
        automovil.agregarOpcional("Aire Acondicionado");
        automovil.agregarOpcional("Techo Corredizo");

        Assertions.assertThat(automovil.getOpcionales()).contains("Airbag","Llantas", "ABS", "Aire Acondicionado", "Techo Corredizo");

    }
}
