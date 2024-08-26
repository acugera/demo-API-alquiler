package back3.tpBackend.Services;

import back3.tpBackend.Entities.dto.AlquilerDto;
import back3.tpBackend.Services.dto.AlquilerInicioDto;
import back3.tpBackend.Services.dto.AlquilierFinalizadoDtoIn;
import back3.tpBackend.Services.dto.AlquilierFinalizadoDtoOut;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

// Servicio de Alquileres que extiende los métodos para CRUD de Service...
public interface AlquilerService extends Service<AlquilerDto,Long>{

    // Se definen los métodos necesarios para iniciar, finalizar y filtrar alquileres...
    public void iniciarAlquiler(AlquilerInicioDto alquilerInicioDto);

    public ResponseEntity<AlquilierFinalizadoDtoOut> finalizarAlquiler (AlquilierFinalizadoDtoIn alquilierFinalizadoDtoIn);

    public List<AlquilerDto> alquileresFiltrados (Long idEstacionRetiro, Long idEstacionDevo, LocalDateTime fechaRetiro, LocalDateTime fechaDevo);

}
