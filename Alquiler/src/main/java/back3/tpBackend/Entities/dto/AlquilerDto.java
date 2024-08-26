package back3.tpBackend.Entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class AlquilerDto {
    private final long id;

    private final Long idCliente;

    private final Integer estado;

    private final Long  idEstRetiro;

    private final Long idEstDevolucion;

    private final LocalDateTime retiro;

    private final LocalDateTime devolucion;

    private final Double monto;

    private final Long idTarifa;
}
