package back3.tpBackend.Controllers;

import back3.tpBackend.Entities.dto.TarifaDto;
import back3.tpBackend.Services.TarifaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tarifa")
public class TarifaController {

    private final TarifaService tarifaService;

    // Constructor que recibe una instancia de TarifaService mediante inyección de dependencias...
    public TarifaController(TarifaService tarifaService) {
        this.tarifaService = tarifaService;
    }

    // Endpoint que obtiene todas las tarifas...
    @GetMapping
    public ResponseEntity<List<TarifaDto>> getAll(){
        List<TarifaDto> values = tarifaService.getAll();
        if (!values.isEmpty()){
            return ResponseEntity.ok(values);
        } else return ResponseEntity.noContent().build();
    }

    // Endpoint que obtiene una tarifa por su id...
    @GetMapping("/{id}")
    public ResponseEntity<TarifaDto> getById(@PathVariable("id") long id) {
        TarifaDto tarifaDto = tarifaService.getById(id);
        if (tarifaDto != null) {
            return ResponseEntity.ok(tarifaDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // Endpoint para crear una nueva tarifa...
    @PostMapping
    public ResponseEntity<Void> add(@RequestBody TarifaDto entity) {
        tarifaService.add(entity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // Endpoint para actualizar una tarifa ya existente...
    @PutMapping
    public ResponseEntity<Void> update(@RequestBody TarifaDto entity) {
        tarifaService.update(entity);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // Endpoint para eliminar una tarifa por su id...
    @DeleteMapping("/{id}")
    public ResponseEntity<TarifaDto> delete(@PathVariable Long id) {
        TarifaDto tarifaDto = tarifaService.delete(id);
        if (tarifaDto != null) {
            return ResponseEntity.ok(tarifaDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
