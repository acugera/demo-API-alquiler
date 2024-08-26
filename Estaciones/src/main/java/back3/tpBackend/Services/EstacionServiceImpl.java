package back3.tpBackend.Services;

import back3.tpBackend.Entities.Estacion;
import back3.tpBackend.Entities.dto.EstacionDto;
import back3.tpBackend.Repositories.EstacionRepository;
import back3.tpBackend.Services.dto.mappers.Estacion.EstacionDtoMapper;
import back3.tpBackend.Services.dto.mappers.Estacion.EstacionMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class EstacionServiceImpl implements EstacionService {


    private final EstacionRepository estacionRepository;

    private final EstacionDtoMapper estacionDtoMapper;

    private final EstacionMapper estacionMapper;

    // Se define el constructor del servicio al cual se le inyectan las dependecias del repositorio y los mappers...
    public EstacionServiceImpl(EstacionRepository estacionRepository, EstacionDtoMapper estacionDtoMapper, EstacionMapper estacionMapper) {
        this.estacionRepository = estacionRepository;
        this.estacionDtoMapper = estacionDtoMapper;
        this.estacionMapper = estacionMapper;
    }

    // Método para agregar una nueva estación...
    @Override
    public void add(EstacionDto entity) {
        Estacion estacion = Optional.of(entity).map(estacionMapper).get();
        estacionRepository.save(estacion);

    }

    // Obtiene una estación buscandola por su id...
    @Override
    public EstacionDto getById(Long id) {
        Estacion estacion = estacionRepository.findById(id).orElse(null);
        return estacion != null ? estacionDtoMapper.apply(estacion):null;
    }

    // Obtiene todas las estaciones desde el repositorio...
    @Override
    public List<EstacionDto> getAll() {
        List<Estacion> values = estacionRepository.findAll();
        return values
                .stream()
                .map(estacionDtoMapper)
                .toList();
    }

    // Elimina una estació por su id...
    @Override
    public EstacionDto delete(Long id) {
        Optional<Estacion> optionalEstacion = estacionRepository.findById(id);
        if (optionalEstacion.isPresent()){
            estacionRepository.delete(optionalEstacion.get());
            return estacionDtoMapper.apply(optionalEstacion.get());
        }else {
            return null;
        }
    }

    // Actualiza una estación con los datos que recibe como parámetro...
    @Override
    public void update(EstacionDto entity) {
        Optional<Estacion> company = Stream.of(entity).map(estacionMapper).findFirst();
        company.ifPresent(estacionRepository::save);
    }

    // Determina cual es la estación más cercana a unas coordenadas dadas...
    @Override
    public EstacionDto estacionCercana(Float latitud, Float longitud) {
        // Ajusta las coordenadas multiplicándolas por 110000
        // (un valor aproximado para convertir coordenadas en distancias en metros)...
        latitud*=110000;
        longitud*=110000;
        // Obtiene todas las estaciones desde el repositorio...
        List<Estacion> estaciones = estacionRepository.findAll();

        // Inicializa la estación más cercana como la primera en la lista...
        Estacion estacionCercana = estaciones.get(0);

        // Calcula las coordenadas ajustadas de la primera estación...
        double latitud2 = estacionCercana.getLatitud()*110000;
        double longitud2 = estacionCercana.getLongitud()*110000;

        // Calcula la distancia euclídea inicial...
        double distanciaAnt = Math.sqrt(Math.pow(latitud - latitud2, 2) + Math.pow(longitud - longitud2, 2));

        // Itera sobre todas las estaciones para encontrar la más cercana...
        for (Estacion estacion : estaciones) {
            latitud2 = estacion.getLatitud()*110000;
            longitud2 = estacion.getLongitud()*110000;

            // Calcula la distancia actual...
            double distancia = Math.sqrt(Math.pow(latitud - latitud2, 2) + Math.pow(longitud - longitud2, 2));

            // Actualiza la estación más cercana si la distancia actual es menor...
            if (distancia < distanciaAnt) {
                distanciaAnt = distancia;
                estacionCercana = estacion;
            }
        }
        return estacionDtoMapper.apply(estacionCercana);
    }

    // Define la distancia entre dos estaciones que recibe como parámetros...
    @Override
    public Double distaciaEntre(Long idEstacion1, Long idEstacion2) {

        // Obtiene las estaciones por sus IDs desde el repositorio...
        Estacion estacion1 = estacionRepository.findById(idEstacion1).get();
        Estacion estacion2 = estacionRepository.findById(idEstacion2).get();

        // Calcula las coordenadas ajustadas de ambas estaciones...
        double latitud = estacion1.getLatitud()*110000;
        double longitud = estacion1.getLongitud()*110000;

        double latitud2 = estacion2.getLatitud()*110000;
        double longitud2 = estacion2.getLongitud()*110000;

        // Calcula la distancia entre las dos estaciones...
        double distancia = Math.sqrt(Math.pow(latitud - latitud2, 2) + Math.pow(longitud - longitud2, 2));

        return distancia;
    }
}
