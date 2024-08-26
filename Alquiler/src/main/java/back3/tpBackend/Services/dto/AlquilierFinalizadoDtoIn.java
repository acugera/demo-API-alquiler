package back3.tpBackend.Services.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class AlquilierFinalizadoDtoIn {
    private final Long alquilerId;
    private final Long estacionId;
    private final String moneda;
}
