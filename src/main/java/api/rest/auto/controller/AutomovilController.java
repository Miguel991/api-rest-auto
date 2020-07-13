package api.rest.auto.controller;

import api.rest.auto.exceptions.InvalidOpcionalException;
import api.rest.auto.exceptions.ResourceNotFoundException;
import api.rest.auto.modelo.Automovil;
import api.rest.auto.modelo.Statistics;
import api.rest.auto.service.AutomovilService;
import api.rest.auto.service.EstadisticaAutomovilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1")
public class AutomovilController {

    private AutomovilService automovilService;
    private EstadisticaAutomovilService estadisticaAutomovilService;

    @Autowired
    public AutomovilController(AutomovilService automovilService, EstadisticaAutomovilService estadisticaAutomovilService){
        this.automovilService = automovilService;
        this.estadisticaAutomovilService = estadisticaAutomovilService;
    }

    @GetMapping("/automoviles")
    public List<Automovil> getAll(){
        return this.automovilService.findAll();
    }

    @GetMapping("/automoviles/{id}")
    public ResponseEntity<Automovil> getById(@PathVariable(value = "id") long id) throws ResourceNotFoundException {

        Automovil response = this.automovilService.findById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/automoviles")
    public Automovil create(@RequestBody Automovil automovil) throws InvalidOpcionalException {
        return automovilService.create(automovil);
    }

    @PutMapping("/automoviles/{id}")
    public ResponseEntity<Automovil> update(@PathVariable(value = "id") Long id,@Valid @RequestBody Automovil automovil) throws ResourceNotFoundException, InvalidOpcionalException {

        Automovil response = this.automovilService.update(id, automovil);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/automoviles/{id}")
    public Map<String, Object> delete(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {

        Map<String, Object> response = new HashMap<>();
        try{
            this.automovilService.delete(id);
            response.put("deleted", Boolean.TRUE);
        }catch (Exception e){
            response.put("message", e.getMessage());
            response.put("deleted", Boolean.FALSE);
        }
        return response;
    }

    @GetMapping("/automoviles/stats")
    public ResponseEntity<Statistics> stats(){
        Statistics statistics =  this.estadisticaAutomovilService.obtenerEstadisticas();
        return ResponseEntity.ok(statistics);
    }
}