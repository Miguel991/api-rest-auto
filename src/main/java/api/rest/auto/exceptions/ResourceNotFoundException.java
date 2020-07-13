package api.rest.auto.exceptions;

public class ResourceNotFoundException extends Exception {

    public ResourceNotFoundException(String mensaje){
        super(mensaje);
    }
}
