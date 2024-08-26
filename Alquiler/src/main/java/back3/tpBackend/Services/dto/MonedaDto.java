package back3.tpBackend.Services.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class MonedaDto {
    private String moneda;
    private Double importe;
}
