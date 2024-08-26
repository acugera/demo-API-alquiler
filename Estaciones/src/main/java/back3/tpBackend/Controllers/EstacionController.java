package back3.tpBackend.Controllers;


import back3.tpBackend.Entities.dto.EstacionDto;
import back3.tpBackend.Services.EstacionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estacion")
public class EstacionController {

    private final EstacionService estacionService;

    // Constructor que recibe una instancia de EstacionService mediante inyección de dependencias...
    public EstacionController(EstacionService estacionService) {
        this.estacionService = estacionService;
    }

    // Endpoint para obtener todas las estaciones...
    @GetMapping
    public ResponseEntity<List<EstacionDto>> getAll(){
        List<EstacionDto> values = estacionService.getAll();
        if (!values.isEmpty()){
            return ResponseEntity.ok(values);
        } else return ResponseEntity.noContent().build();
    }

    // Endpoint para obtener una estación en particular...
    @GetMapping("/{id}")
    public ResponseEntity<EstacionDto> getById(@PathVariable("id") long id) {
        EstacionDto estacion = estacionService.getById(id);
        if (estacion != null){
            return ResponseEntity.ok(estacion);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para crear una nueva estación...
    @PostMapping
    public ResponseEntity<Void> add(@RequestBody EstacionDto entity) {
        estacionService.add(entity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    // Endpoint para actualizar una estación existente...
    @PutMapping
    public ResponseEntity<Void> update(@RequestBody EstacionDto entity) {
        estacionService.update(entity);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // Endpoint para eliminar una estación por su id...
    @DeleteMapping("/{id}")
    public ResponseEntity<EstacionDto> delete(@PathVariable Long id) {
        EstacionDto estacion = estacionService.delete(id);
        if (estacion != null){
            return ResponseEntity.ok(estacion);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para obtener la estación más cercana a una ubicación dada...
    @GetMapping("/cercana")
    public ResponseEntity<EstacionDto> getEstacionCercana(@RequestParam("latitud") Float latitud, @RequestParam("longitud") Float longitud) {
        EstacionDto estacionCercana = estacionService.estacionCercana(latitud, longitud);
        return ResponseEntity.ok(estacionCercana);
    }

    // Endpoint para obtener la distancia entre dos estaciones dadas...
    @GetMapping("/distancia")
    public ResponseEntity<Double> getDistanciaEstacion(@RequestParam("estacion1") Long idEstacion1, @RequestParam("estacion2") Long idEstacion2){
        return ResponseEntity.ok(estacionService.distaciaEntre(idEstacion1,idEstacion2));
    }

}
