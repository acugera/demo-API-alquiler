package back3.tpBackend.Services.dto.mappers.Alquiler;

import back3.tpBackend.Entities.Alquiler;
import back3.tpBackend.Entities.Tarifa;
import back3.tpBackend.Entities.dto.AlquilerDto;
import back3.tpBackend.Repositories.TarifaRepositoty;
import org.springframework.stereotype.Service;

import java.util.function.Function;

// Servicio para transformar un AlquilerDto a un Alquiler...
@Service
public class AlquilerMapper implements Function<AlquilerDto, Alquiler> {

    // Se trae el repositorio de tarifas para buscar en la BD la tarifa que se relacione al alquiler...
    private final TarifaRepositoty tarifaRepositoty;

    public AlquilerMapper(TarifaRepositoty tarifaRepositoty) {
        this.tarifaRepositoty = tarifaRepositoty;
    }

    @Override
    public Alquiler apply(AlquilerDto alquilerDto) {
        // Se busca la tarifa por el id de tarifa especificado en el alquilerDto...
        Tarifa tarifa = tarifaRepositoty.findById(alquilerDto.getIdTarifa()).orElse(null);
        return new Alquiler(
                alquilerDto.getId(),
                alquilerDto.getIdCliente(),
                alquilerDto.getEstado(),
                alquilerDto.getIdEstRetiro(),
                alquilerDto.getIdEstDevolucion(),
                alquilerDto.getRetiro(),
                alquilerDto.getDevolucion(),
                alquilerDto.getMonto(),
                tarifa);
    }
}
