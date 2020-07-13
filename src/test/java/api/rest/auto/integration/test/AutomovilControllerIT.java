package api.rest.auto.integration.test;

import api.rest.auto.App;
import api.rest.auto.modelo.Automovil;
import api.rest.auto.modelo.Modelo;

import api.rest.auto.modelo.Statistics;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@SpringBootTest(classes = App.class, webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith( SpringRunner.class )
@ContextConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AutomovilControllerIT {

    private static final String AUTOMOVIL_COUPE_JSON_OBJ = "\"modelo\":\"Coupe\",\"opcionales\":[]";
    private static final String AUTOMOVIL_SEDAN_JSON_OBJ = "\"modelo\":\"Sedan\",\"opcionales\":[]";
    private static final String DELETE_AUTOMOVIL_RESPONSE_TRUE = "{\"deleted\":true}";
    private static final String DELETE_AUTOMOVIL_RESPONSE_FALSE = "{\"deleted\":false,\"message\":\"Car with id: 0 not found.\"}";

    private static final String MODELO_FAMILIAR = "Familiar";

    private static final String OPCIONAL_LLANTAS = "LL";

    private static final int STATUS_CODE_200_OK = 200;

    private static final float CINCUENTA_POR_CIENTO =  50;
    private static final float VEINTE_POR_CIENTO =  20;
    private static final float TREINTA_POR_CIENTO =  30;

    private ResponseEntity<Statistics> estadisticas;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    private String getBaseUrl(){
        return "http://localhost:"+port+"/api/v1/automoviles";
    }

    @Test
    public void crearNuevoAutomovilFamiliarBasico() throws Exception {
        Automovil automovil = new Automovil(Modelo.FAMILIAR);

        ResponseEntity<Automovil> response = testRestTemplate.postForEntity(getBaseUrl(),automovil,Automovil.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody().getModelo()).describedAs("se espera un automovil modelo familiar").isEqualTo(MODELO_FAMILIAR);

    }

    @Test
    public void modificarUnAutomovilFamiliarBasicoAgregandoLlantas(){

        Automovil automovilSinOpcionales = new Automovil(Modelo.FAMILIAR);
        Automovil response = testRestTemplate.postForObject(getBaseUrl(),automovilSinOpcionales,Automovil.class);

        response.agregarOpcional(OPCIONAL_LLANTAS);

        testRestTemplate.put(getBaseUrl() + "/" + response.getId() ,response);

        Automovil expected = testRestTemplate.getForObject(getBaseUrl() + "/" + response.getId(), Automovil.class );

        Assertions.assertThat(expected.getOpcionales()).isNotNull().contains(OPCIONAL_LLANTAS);

    }

    @Test
    public void obtenerTodosLosAutomovilesCreados(){

        ResponseEntity<Automovil> coupe = testRestTemplate.postForEntity(getBaseUrl(),new Automovil(Modelo.COUPE),Automovil.class);
        ResponseEntity<Automovil> sedan = testRestTemplate.postForEntity(getBaseUrl(),new Automovil(Modelo.SEDAN),Automovil.class);

        Assertions.assertThat(coupe.getStatusCodeValue()).isEqualTo(STATUS_CODE_200_OK);
        Assertions.assertThat(sedan.getStatusCodeValue()).isEqualTo(STATUS_CODE_200_OK);

        ResponseEntity<String> response = testRestTemplate.exchange(getBaseUrl(), HttpMethod.GET, getRequestEntity(), String.class);

        Assertions.assertThat(response.getBody()).contains(AUTOMOVIL_COUPE_JSON_OBJ, AUTOMOVIL_SEDAN_JSON_OBJ);
    }

    @Test
    public void modificarUnAutomovilFamiliarConAirbagYLlantasEliminandoElAirbag(){
        //TODO
    }

    @Test
    public void eliminarUnAutomovilFamiliar(){

        ResponseEntity<Automovil> automovilFamiliar = testRestTemplate.postForEntity(getBaseUrl(),new Automovil(Modelo.FAMILIAR),Automovil.class);
        Assertions.assertThat(automovilFamiliar).isNotNull();
        Automovil automovil = automovilFamiliar.getBody();

        ResponseEntity<String> response =  testRestTemplate.exchange(getBaseUrl() +"/" + automovil.getId(), HttpMethod.DELETE, getRequestEntity(), String.class);
        Assertions.assertThat(response.getBody()).contains(DELETE_AUTOMOVIL_RESPONSE_TRUE);

    }

    @Test
    public void cuandoSeIntentaEliminarUnAutomovilQueNoExisteDevuelveRespuestaFalse(){

        Long idAutomovil = 0L;

        ResponseEntity<String> deleteResponse =  testRestTemplate.exchange(getBaseUrl() +"/" + idAutomovil, HttpMethod.DELETE, getRequestEntity(), String.class);
        Assertions.assertThat(deleteResponse.getBody()).contains(DELETE_AUTOMOVIL_RESPONSE_FALSE);

    }

    @Test
    public void obtenerEstadisticasDeLaCreacionDeAutomoviles(){

        ResponseEntity<String> response = testRestTemplate.exchange(getBaseUrl() + "/stats", HttpMethod.GET, getRequestEntity(), String.class);

        Assertions.assertThat(response.getBody()).contains("count_car", "count_sedan", "percent_sedan", "count_coupe", "percent_coupe", "count_family", "percent_family", "count_tc", "percent_tc", "count_aa", "percent_aa", "count_abs", "percent_abs", "count_ab", "percent_ab", "count_ll", "percent_ll");

    }

    @Test
    public void obtenerEstadisticasDelNumeroTotalDeAutosCreados(){

        dadoQueExistenAutomoviles(3, Modelo.FAMILIAR);
        cuandoObtengoLasEstadisticasDeAutomoviles();
        entoncesObtengoUnaRespuestaConElNumeroTotalDeAutomoviles();

    }

    @Test
    public void obtenerEstadisticasDelPorcentajeDeModelosCreados(){

        dadoQueExistenAutomoviles(5, Modelo.FAMILIAR);
        dadoQueExistenAutomoviles(3, Modelo.SEDAN);
        dadoQueExistenAutomoviles(2, Modelo.COUPE);

        cuandoObtengoLasEstadisticasDeAutomoviles();

        comprueboElPorcentajeDeCadaModeloDeAutomovilCreado();
    }

    @Test
    public void obtenerEstadisticasDelPorcentajeDeOpcionalesCreados(){

        dadoQueExistenAutomoviles(5,Modelo.FAMILIAR, Arrays.asList("ABS", "LL", "TC"));
        dadoQueExistenAutomoviles(5,Modelo.SEDAN, Arrays.asList("AA", "AB"));

        cuandoObtengoLasEstadisticasDeAutomoviles();

        comprueboElPorcentaDeOpcionalesQueExisteParaElTotalDeAutomoviles();
    }

    private void comprueboElPorcentaDeOpcionalesQueExisteParaElTotalDeAutomoviles() {

        Statistics estadisticas = this.estadisticas.getBody();

        Assertions.assertThat(estadisticas.getPercent_aa()).isEqualTo(CINCUENTA_POR_CIENTO);
        Assertions.assertThat(estadisticas.getPercent_ab()).isEqualTo(CINCUENTA_POR_CIENTO);
        Assertions.assertThat(estadisticas.getPercent_abs()).isEqualTo(CINCUENTA_POR_CIENTO);
        Assertions.assertThat(estadisticas.getPercent_tc()).isEqualTo(CINCUENTA_POR_CIENTO);
        Assertions.assertThat(estadisticas.getPercent_ll()).isEqualTo(CINCUENTA_POR_CIENTO);
    }

    private void dadoQueExistenAutomoviles(int cantidad, Modelo modelo, List<String> opcionales) {
        Automovil automovil = new Automovil(modelo);
        for (String opcional : opcionales) {
            automovil.agregarOpcional(opcional);
        }

        for (int i = 0; i < cantidad ; i++) {
            testRestTemplate.postForEntity(getBaseUrl(),automovil,Automovil.class);
        }
    }

    private void comprueboElPorcentajeDeCadaModeloDeAutomovilCreado() {

        Statistics estadisticas = this.estadisticas.getBody();

        Assertions.assertThat(estadisticas.getCount_car()).isEqualTo(10);
        Assertions.assertThat(estadisticas.getPercent_family()).isEqualTo(CINCUENTA_POR_CIENTO);
        Assertions.assertThat(estadisticas.getPercent_sedan()).isEqualTo(TREINTA_POR_CIENTO);
        Assertions.assertThat(estadisticas.getPercent_coupe()).isEqualTo(VEINTE_POR_CIENTO);
    }

    private HttpEntity<String> getRequestEntity(){
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
        return entity;
    }

    private void entoncesObtengoUnaRespuestaConElNumeroTotalDeAutomoviles() {

        Statistics statistics = this.estadisticas.getBody();
        Assertions.assertThat(statistics.getCount_car()).isEqualTo(3);
    }

    private void cuandoObtengoLasEstadisticasDeAutomoviles() {
        this.estadisticas = testRestTemplate.getForEntity(getBaseUrl() + "/stats" , Statistics.class);
    }


    private void dadoQueExistenAutomoviles(int cantidad, Modelo modelo) {

        for (int i = 0; i < cantidad; i++) {
            testRestTemplate.postForEntity(getBaseUrl(),new Automovil(modelo),Automovil.class);
        }
    }

}
