package back3.tpBackend.Services.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class AlquilierFinalizadoDtoOut {
    private final long id;

    private final Long idCliente;

    private final Integer estado;

    private final Long  idEstRetiro;

    private final Long idEstDevolucion;

    private final LocalDateTime retiro;

    private final LocalDateTime devolucion;

    private final String moneda;

    private final Double monto;

    private final Long idTarifa;
}
