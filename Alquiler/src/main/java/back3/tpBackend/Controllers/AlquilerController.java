package back3.tpBackend.Controllers;

import back3.tpBackend.Entities.dto.AlquilerDto;
import back3.tpBackend.Services.dto.AlquilerInicioDto;
import back3.tpBackend.Services.AlquilerService;
import back3.tpBackend.Services.dto.AlquilierFinalizadoDtoIn;
import back3.tpBackend.Services.dto.AlquilierFinalizadoDtoOut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/alquiler")
public class AlquilerController {

    private final AlquilerService alquilerService;

    // Constructor que recibe una instancia de AlquilerService mediante inyección de dependencias...
    public AlquilerController(AlquilerService alquilerService) {
        this.alquilerService = alquilerService;
    }

    // Endpoint para obtener todos los alquileres...
    @GetMapping
    public ResponseEntity<List<AlquilerDto>> getAll(){
        List<AlquilerDto> values = alquilerService.getAll();
        if (!values.isEmpty()){
            return ResponseEntity.ok(values);
        } else return ResponseEntity.noContent().build();
    }

    // Endpoint para obtener un alquiler por su id...
    @GetMapping("/{id}")
    public ResponseEntity<AlquilerDto> getById(@PathVariable("id") long id) {
        AlquilerDto alquilerDto = alquilerService.getById(id);
        if (alquilerDto != null) {
            return ResponseEntity.ok(alquilerDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para crear un alquler a partir de un body que recibe...
    @PostMapping
    public ResponseEntity<Void> add(@RequestBody AlquilerInicioDto entity) {
        alquilerService.iniciarAlquiler(entity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // Endpoint para actualizar un alquiler existente...
    @PutMapping
    public ResponseEntity<Void> update(@RequestBody AlquilerDto entity) {
        alquilerService.update(entity);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // Endpoint para eliminar un alquiler por ID...
    @DeleteMapping("/{id}")
    public ResponseEntity<AlquilerDto> delete(@PathVariable Long id) {
        AlquilerDto alquilerDto = alquilerService.delete(id);
        if (alquilerDto != null) {
            return ResponseEntity.ok(alquilerDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para finalizar un alquiler...
    @PatchMapping("/finalizado")
    public ResponseEntity<AlquilierFinalizadoDtoOut> devolper(@RequestBody AlquilierFinalizadoDtoIn entity){
        ResponseEntity<AlquilierFinalizadoDtoOut> finalizado = alquilerService.finalizarAlquiler(entity);
        return finalizado;
    }

    // Endpoint para filtrar alquileres según varios criterios...
    @GetMapping("/filtrar")
    public ResponseEntity<List<AlquilerDto>> filtrado(
            // Required = false para determinar que el parámetro no es obligatorio...
            @RequestParam(value = "idEstRetiro", required = false) Long idEstRetiro,
            @RequestParam(value = "idEstDevo", required = false) Long idEstDevo,
            @RequestParam(value = "fechaRetiro", required = false) LocalDateTime fechaRetiro,
            @RequestParam(value = "fechaDevo", required = false) LocalDateTime fechaDevo
    ){
        List<AlquilerDto> values = alquilerService.alquileresFiltrados(idEstRetiro,idEstDevo,fechaRetiro,fechaDevo);
        if (!values.isEmpty()){
            return ResponseEntity.ok(values);
        }else {
            return ResponseEntity.noContent().build();
        }

    }

}
