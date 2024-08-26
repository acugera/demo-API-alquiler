package back3.tpBackend.Services.dto.mappers.Estacion;

import back3.tpBackend.Entities.Estacion;
import back3.tpBackend.Entities.dto.EstacionDto;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class EstacionMapper implements Function<EstacionDto, Estacion> {
    @Override
    public Estacion apply(EstacionDto estacionDto) {
        return new Estacion(estacionDto.getId(),estacionDto.getNombre(),estacionDto.getCreacion(),estacionDto.getLatitud(),estacionDto.getLongitud());
    }
}
