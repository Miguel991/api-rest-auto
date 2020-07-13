package api.rest.auto.unit.test;

import api.rest.auto.exceptions.InvalidOpcionalException;
import api.rest.auto.exceptions.ResourceNotFoundException;
import api.rest.auto.modelo.Automovil;
import api.rest.auto.modelo.Modelo;
import api.rest.auto.repository.AutomovilRepository;
import api.rest.auto.service.AdministracionService;
import api.rest.auto.service.AutomovilService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AutomovilServiceTest {

    private static final Automovil AUTOMOVIL_FAMILIAR = new Automovil(Modelo.FAMILIAR);
    private static final String MODELO_FAMILIAR = "Familiar";

    private static final String OPCIONAL_INVALIDO = "test";
    private static final String OPCIONAL_ABS = "ABS";
    private static final String OPCIONAL_LLANTAS = "LL";
    private static final String OPCIONAL_AIRBAG = "AB";
    private static final String OPCIONAL_TECHO_CORREDIZO = "TC";

    private static final Double PRECIO_BASE_MODELO_FAMILIAR = 245000D;

    private static final Long AUTOMOVIL_ID = 1L;

    @Mock
    private AutomovilRepository automovilRepository;
    @Mock
    private AdministracionService administracionService;

    @InjectMocks
    private AutomovilService automovilService;

    @Test
    public void create_crearUnNuevoAutomovil_returnUnNuevoAutomovil() throws InvalidOpcionalException {

        Automovil familiar = new Automovil(Modelo.FAMILIAR);

        Mockito.when(automovilRepository.save(Mockito.any(Automovil.class))).thenReturn(AUTOMOVIL_FAMILIAR);

        Automovil expected = this.automovilService.create(familiar);

        Assertions.assertThat(expected.getModelo()).isNotNull().isEqualTo(MODELO_FAMILIAR);

    }

    @Test(expected = InvalidOpcionalException.class)
    public void update_validarOpcionalesInvalidos_retornaInvalidOpcionalExeption() throws ResourceNotFoundException, InvalidOpcionalException {

        Automovil familiar = new Automovil(Modelo.FAMILIAR);
        familiar.agregarOpcional(OPCIONAL_INVALIDO);
        this.automovilService.update(1L ,familiar);

    }

    @Test
    public void update_agregarOpcionalesValidos_retornaAutomovilActualizado() throws ResourceNotFoundException, InvalidOpcionalException {

        Automovil familiar = new Automovil(Modelo.FAMILIAR);
        familiar.agregarOpcional(OPCIONAL_ABS);
        familiar.agregarOpcional(OPCIONAL_LLANTAS);
        familiar.agregarOpcional(OPCIONAL_TECHO_CORREDIZO);

        Mockito.when(automovilRepository.findById(AUTOMOVIL_ID)).thenReturn(java.util.Optional.of(new Automovil(Modelo.FAMILIAR)));
        Mockito.when(automovilRepository.save(Mockito.any(Automovil.class))).thenReturn(familiar);

        Automovil expected =  this.automovilService.update(AUTOMOVIL_ID ,familiar);

        Assertions.assertThat(expected).isNotNull();
        Assertions.assertThat(expected.getOpcionales()).contains(OPCIONAL_ABS, OPCIONAL_LLANTAS, OPCIONAL_TECHO_CORREDIZO);
    }

    @Test
    public void create_crearNuevoAutomovilSinOpcionales_retornaNuevoAutomovilConPrecio() throws InvalidOpcionalException {

        Automovil familiar = new Automovil(Modelo.FAMILIAR);

        Mockito.when(automovilRepository.save(Mockito.any(Automovil.class))).thenReturn(familiar);
        Mockito.when(administracionService.calcularPrecioFinalAutomovil(Mockito.any())).thenReturn(PRECIO_BASE_MODELO_FAMILIAR);

        Automovil expected = this.automovilService.create(familiar);

        Assertions.assertThat(expected.getPrecio()).isNotNull().isEqualTo(PRECIO_BASE_MODELO_FAMILIAR);
    }

    @Test
    public void create_crearNuevoAutomovilConOpcionales_retornaNuevoAutomovilConPrecioTotal(){
        //TODO
    }

    @Test
    public void update_actualizarOpcionalesAutomovil_retornaAutomovilConPrecioActualizado(){
        //TODO
    }
}
