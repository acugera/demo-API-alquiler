package back3.tpBackend.Services.dto.mappers.Alquiler;

import back3.tpBackend.Entities.Alquiler;
import back3.tpBackend.Services.dto.AlquilerInicioDto;
import back3.tpBackend.Repositories.TarifaRepositoty;
import org.springframework.stereotype.Service;

import java.util.function.Function;

//Servicio para mapear un Dto de alquiler que se quiere crear a un Alquiler...
@Service
public class AlquilerInicioMapper implements Function<AlquilerInicioDto, Alquiler> {

    // Se mapea a la BD, seteando como null los atributos que no se reciben al crearse el Alquiler...
    public Alquiler apply(AlquilerInicioDto alquilerInicioDto) {

        return new Alquiler(alquilerInicioDto.getId(),
                alquilerInicioDto.getIdCliente(),
                1,
                alquilerInicioDto.getIdEstRetiro(),
                null,
                alquilerInicioDto.getRetiro(),
                null,
                null,
                null);
    }
}

