package back3.tpBackend.Services.dto.mappers.Tarifa;

import back3.tpBackend.Entities.Tarifa;
import back3.tpBackend.Entities.dto.TarifaDto;
import org.springframework.stereotype.Service;

import java.util.function.Function;

// Servicio para transformar un AlquilerDto a un Alquiler...
@Service
public class TarifaMapper implements Function<TarifaDto, Tarifa> {
    @Override
    public Tarifa apply(TarifaDto tarifaDto) {
        return new Tarifa(
                tarifaDto.getId(),
                tarifaDto.getTipoTarifa(),
                tarifaDto.getDefinicion(),
                tarifaDto.getDiaSemana(),
                tarifaDto.getDiaMes(),
                tarifaDto.getMes(),
                tarifaDto.getAnio(),
                tarifaDto.getMontoFijoAlq(),
                tarifaDto.getMontoMinFrac(),
                tarifaDto.getMontoKm(),
                tarifaDto.getMontoHora());
    }
}
