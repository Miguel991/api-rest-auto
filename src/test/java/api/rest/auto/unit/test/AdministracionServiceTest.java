package api.rest.auto.unit.test;

import api.rest.auto.service.AdministracionService;
import api.rest.auto.modelo.Automovil;
import api.rest.auto.modelo.Modelo;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

public class AdministracionServiceTest {

    private Automovil automovil;
    private AdministracionService administracionService;

    @Before
    public void setup(){
        this.automovil = new Automovil(Modelo.FAMILIAR);
        this.administracionService = new AdministracionService();
    }

    @Test
    public void calcularPrecioFinalAutomovil_calcularPrecioFinalAutomovilFamiliarSinOpcionales_precioFinal(){

        double precioFinal = administracionService.calcularPrecioFinalAutomovil(automovil);
        Assertions.assertThat(precioFinal).describedAs("Precio de un automovil sin opcionales.").isEqualTo(245000);

    }

    @Test
    public void calcularPrecioFinalAutomovil_calcularPrecioFinalAutomovilFamiliarConAirbag_precioFinal(){

        automovil.agregarOpcional("AB");

        double precioFinal = administracionService.calcularPrecioFinalAutomovil(automovil);

        Assertions.assertThat(precioFinal).describedAs("Precio de un Automovil Familiar con Airbag").isEqualTo(252000);

    }

    @Test
    public void calcularPrecioFinalAutomovil_calcularPrecioFinalAutomovilFamiliarConAirbagLlantasTechoCorredizoABSAireAcondicionado_precioFinal(){

        automovil.agregarOpcional("AB");
        automovil.agregarOpcional("LL");
        automovil.agregarOpcional("ABS");
        automovil.agregarOpcional("TC");
        automovil.agregarOpcional("AA");

        double precioFinal = administracionService.calcularPrecioFinalAutomovil(automovil);

        Assertions.assertThat(precioFinal).describedAs("Precio total del Automovil").isEqualTo(310000);

    }
}
