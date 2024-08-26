package back3.tpBackend.Services.dto.mappers.Alquiler;

import back3.tpBackend.Entities.Alquiler;
import back3.tpBackend.Entities.Tarifa;
import back3.tpBackend.Entities.dto.AlquilerDto;
import org.springframework.stereotype.Service;

import java.util.function.Function;

// Servicio para transformar un Alquiler que se obtenga de la BD a un DTO para presentar los datos...
@Service
public class AlquilerDtoMapper implements Function<Alquiler, AlquilerDto> {
    @Override
    public AlquilerDto apply(Alquiler alquiler) {

        // Se obtiene únicamente el id de la tarifa a la que se relaciona el alquiler...
        Long tarifa = null;
        if(alquiler.getTarifa() != null) {
            tarifa = alquiler.getTarifa().getId();
        }

        // Se realiza la conversión de Alquiler a ALquilerDto con el constructor...
        return new AlquilerDto(
                alquiler.getId(),
                alquiler.getIdCliente(),
                alquiler.getEstado(),
                alquiler.getEstRetiro(),
                alquiler.getEstDevolucion(),
                alquiler.getRetiro(),
                alquiler.getDevolucion(),
                alquiler.getMonto(),
                tarifa);
    }
}
