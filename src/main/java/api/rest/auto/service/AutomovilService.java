package api.rest.auto.service;

import api.rest.auto.exceptions.InvalidOpcionalException;
import api.rest.auto.exceptions.ResourceNotFoundException;
import api.rest.auto.modelo.Automovil;
import api.rest.auto.repository.AutomovilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class AutomovilService {

    private static final List<String> opcionalesValidos = Arrays.asList("ABS", "LL", "TC", "AA", "AB");

    private AutomovilRepository automovilRepository;
    private AdministracionService administracionService;


    @Autowired
    public AutomovilService (AutomovilRepository automovilRepository, AdministracionService administracionService){
        this.automovilRepository = automovilRepository;
        this.administracionService = administracionService;
    }

    public Automovil create(Automovil automovil) throws InvalidOpcionalException {
        Automovil auto = null;
        if(opcionalesValidos(automovil.getOpcionales())){
            Double precio = administracionService.calcularPrecioFinalAutomovil(automovil);
            automovil.setPrecio(precio);
            auto =  this.automovilRepository.save(automovil);
        }else {
            throw new InvalidOpcionalException("Invalid Opcional parameter. Valid parameters : " + this.opcionalesValidos.toString());
        }
        return auto;
    }

    public Automovil update(long id, Automovil automovil) throws ResourceNotFoundException, InvalidOpcionalException {

        Automovil oldAutomovil = null;

        if(opcionalesValidos(automovil.getOpcionales())){
            oldAutomovil = this.automovilRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("car with id: " + id + "not found."));
            oldAutomovil.setOpcionales(automovil.getOpcionales());
        }else{
            throw new InvalidOpcionalException("Invalid Opcional parameter. Valid parameters : " + this.opcionalesValidos.toString());
        }
        return this.automovilRepository.save(oldAutomovil);
    }

    public Automovil findById(long id) throws ResourceNotFoundException {

        return this.automovilRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Car with id: " + id + " not found."));
    }

    public List<Automovil> findAll() {
        return this.automovilRepository.findAll();
    }

    public void delete(Long id) throws ResourceNotFoundException {
        Automovil automovil = this.automovilRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Car with id: " + id + " not found."));
        this.automovilRepository.delete(automovil);
    }

    private boolean opcionalesValidos(List<String> opcionales) {

        Boolean opcionalValido = true;
        for (String opcional: opcionales) {
            if(!opcionalesValidos.contains(opcional)){
                opcionalValido = false;
            }
        }
        return opcionalValido;
    }
}
