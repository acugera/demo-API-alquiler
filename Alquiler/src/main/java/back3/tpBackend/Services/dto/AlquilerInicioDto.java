package back3.tpBackend.Services.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

// Dto con los atributos que se reciben cuando se crea un nuevo alquiler...
@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class AlquilerInicioDto {
    private final long id;

    private final Long idCliente;

    private final Long  idEstRetiro;

    private final LocalDateTime retiro;
}
