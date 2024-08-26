package back3.tpBackend.Services;

import back3.tpBackend.Entities.Estacion;
import back3.tpBackend.Entities.dto.EstacionDto;

// Define los métodos que implementará el servicio concreto de Estación...
public interface EstacionService extends Service<EstacionDto,Long>{

    // Metodos que luego la clase concreta del servicio debe implementar...
    EstacionDto estacionCercana(Float latitud, Float longitud);

    Double distaciaEntre(Long idEstacion1, Long idEstacion2);
}
