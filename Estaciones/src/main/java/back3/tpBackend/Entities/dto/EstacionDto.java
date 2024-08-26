package back3.tpBackend.Entities.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class EstacionDto {
    private final long id;
    private final String nombre;
    private Date creacion;
    private final Double latitud;
    private final Double longitud;
}
