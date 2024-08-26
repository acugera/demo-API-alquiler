package back3.tpBackend.Services.dto.mappers.Estacion;

import back3.tpBackend.Entities.Estacion;
import back3.tpBackend.Entities.dto.EstacionDto;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class EstacionDtoMapper implements Function<Estacion, EstacionDto> {
    @Override
    public EstacionDto apply(Estacion estacion) {
        return new EstacionDto(estacion.getId(),estacion.getNombre(),estacion.getCreacion(),estacion.getLatitud(),estacion.getLongitud());
    }
}
