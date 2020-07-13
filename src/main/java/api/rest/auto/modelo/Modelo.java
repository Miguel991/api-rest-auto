package api.rest.auto.modelo;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Modelo {

    @JsonProperty("Sedan")
    SEDAN("Sedan"),
    @JsonProperty("Familiar")
    FAMILIAR("Familiar"),
    @JsonProperty("Coupe")
    COUPE("Coupe");

    public String value;

    Modelo(String modelo) {
        this.value = modelo;
    }
}
