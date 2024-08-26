package back3.tpBackend.Services.dto.mappers.Tarifa;

import back3.tpBackend.Entities.Tarifa;
import back3.tpBackend.Entities.dto.TarifaDto;
import org.springframework.stereotype.Service;

;import java.util.function.Function;

// Servicio para transformar una Tarifa que se obtenga de la BD a un DTO para presentar los datos...
@Service
public class TarifaDtoMapper implements Function<Tarifa, TarifaDto> {

    // Se realiza la conversión de Tarifa a TarifaDto con el constructor...
    @Override
    public TarifaDto apply(Tarifa tarifa) {
        return new TarifaDto(
                tarifa.getId(),
                tarifa.getTipoTarifa(),
                tarifa.getDefinicion(),
                tarifa.getDiaSemana(),
                tarifa.getDiaMes(),
                tarifa.getMes(),
                tarifa.getAnio(),
                tarifa.getMontoFijoAlq(),
                tarifa.getMontoMinFrac(),
                tarifa.getMontoKm(),
                tarifa.getMontoHora());
    }
}
