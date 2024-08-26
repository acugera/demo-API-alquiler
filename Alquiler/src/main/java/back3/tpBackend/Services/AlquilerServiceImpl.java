package back3.tpBackend.Services;

import back3.tpBackend.Entities.Alquiler;
import back3.tpBackend.Entities.Tarifa;
import back3.tpBackend.Entities.dto.AlquilerDto;
import back3.tpBackend.Repositories.TarifaRepositoty;
import back3.tpBackend.Services.dto.AlquilerInicioDto;
import back3.tpBackend.Entities.dto.EstacionDto;
import back3.tpBackend.Repositories.AlquilerRepository;
import back3.tpBackend.Services.dto.AlquilierFinalizadoDtoIn;
import back3.tpBackend.Services.dto.AlquilierFinalizadoDtoOut;
import back3.tpBackend.Services.dto.MonedaDto;
import back3.tpBackend.Services.dto.mappers.Alquiler.AlquilerDtoMapper;
import back3.tpBackend.Services.dto.mappers.Alquiler.AlquilerInicioMapper;
import back3.tpBackend.Services.dto.mappers.Alquiler.AlquilerMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

@Service
public class AlquilerServiceImpl implements AlquilerService{

    private final AlquilerRepository alquilerRepository;
    private final AlquilerDtoMapper alquilerDtoMapper;
    private final AlquilerMapper alquilerMapper;

    private final AlquilerInicioMapper alquilerInicioMapper;
    private final TarifaRepositoty tarifaRepositoty;

    //Conexión con la API Estacion...
    // Trae una estación a partir del id de Estación que recibe como parámetros...
    public EstacionDto traerEstacion(Long idEstacion) {
        try {
            RestTemplate template = new RestTemplate();
                    ResponseEntity<EstacionDto> res = template.getForEntity(
                    "http://localhost:8082/api/estacion/{id}", EstacionDto.class, idEstacion
            );
                    // Si logra conectarse al otro microservicio devuelve la estacion dto...
            if (res.getStatusCode().is2xxSuccessful()) {
                System.out.println("Estacion obtenida correctamente Código de estado: " + res.getStatusCodeValue());
                return res.getBody();

                    // Si no pudo obtener la Estación del otro microservicio devuelve el código correspondiente...
            } else {
                System.out.println("Respuesta no exitosa Código de estado: " + res.getStatusCodeValue());
                return null;
            }

            // Captura la excepción en caso de que no sea posible conectarse con la API Estacion...
        } catch (HttpClientErrorException ex) {
            System.out.println("Error al llamar a la API Estacion: " + ex.getMessage());
            return null;
        }
    }

    // Conexión con la API estación...
    // Se llama a API estación para calcular la distancia entre dos estaciones (cuyos ids se pasan como parámetros)...
    public Double traerDistancia(Long id1, Long id2) {
        try {
            RestTemplate template = new RestTemplate();
            // Arma la uri para hacer la consulta a la otra API...
            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("http://localhost:8082/api/estacion/distancia")
                    .queryParam("estacion1", id1)
                    .queryParam("estacion2", id2);

            String url = builder.toUriString();
            ResponseEntity<Double> res = template.getForEntity(url, Double.class);
            if (res.getStatusCode().is2xxSuccessful()) {
                System.out.println("Distancia obtenida correctamente Código de estado: " + res.getStatusCodeValue());
                return res.getBody();
            } else {
                System.out.println("Respuesta no exitosa Código de estado: " + res.getStatusCodeValue());
                return null;
            }

        } catch (HttpClientErrorException ex) {
            System.out.println("Error al llamar a la API Estacion: " + ex.getMessage());
            return null;
        }
    }

    // Conexión con la API de monedas...
    // Convierte un importe que se le pase en una moneda que también se le pase como parámetro...
    public MonedaDto convertirMoneda(String monedaDestino, Double importe) {

        // URL de la API de monedas...
        String API_URL = "http://34.82.105.125:8080/convertir";

        // Configuración de encabezados HTTP...
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Armado de la solicitud HTTP en formato JSON utilizando la moneda y el importe (parámetros)...
        String requestBody = String.format(Locale.US,
                "{\"moneda_destino\":\"%s\",\"importe\":%f}", monedaDestino, importe);

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // Se realiza la llamada a la API...
        try {
            ResponseEntity<MonedaDto> res = new RestTemplate().exchange(
                    API_URL,
                    HttpMethod.POST,
                    requestEntity,
                    MonedaDto.class
            );

            if (res.getStatusCode().is2xxSuccessful()) {
                System.out.println("Moneda convertida correctamente. Código de estado: " + res.getStatusCodeValue());
                return res.getBody();
            } else {
                System.out.println("Error al llamar a la API. Código de estado: " + res.getStatusCodeValue());
            }
            // Captura la excepción si no se pudo conectar con la API de monedas...
        } catch (Exception e) {
            System.out.println("Error al llamar a la API Moneda: " + e.getMessage());
        }

        // Retorna null en caso de que haya habido un error...
        return null;
    }

    // Se define el constructor de la clase...
    public AlquilerServiceImpl(AlquilerRepository alquilerRepository,
                               AlquilerDtoMapper alquilerDtoMapper,
                               AlquilerMapper alquilerMapper,
                               AlquilerInicioMapper alquilerInicioMapper, TarifaRepositoty tarifaRepositoty) {

        this.alquilerRepository = alquilerRepository;
        this.alquilerDtoMapper = alquilerDtoMapper;
        this.alquilerMapper = alquilerMapper;
        this.alquilerInicioMapper = alquilerInicioMapper;
        this.tarifaRepositoty = tarifaRepositoty;
    }

    // Para crear un nuevo alquiler...
    public void iniciarAlquiler(AlquilerInicioDto entity) {
        Alquiler alquiler = Optional.of(entity).map(alquilerInicioMapper).get();

        if (traerEstacion(alquiler.getEstRetiro()) != null) {
            alquilerRepository.save(alquiler);
        }
    }

    // Finalizar un alquiler, se calculan y setean los atributos que se encuentran en null...
    public ResponseEntity<AlquilierFinalizadoDtoOut> finalizarAlquiler(AlquilierFinalizadoDtoIn alquilierFinalizadoDtoIn) {
        // Obtener el alquiler correspondiente a partir del ID proporcionado...
        Alquiler alquiler = alquilerRepository.findById(alquilierFinalizadoDtoIn.getAlquilerId()).orElse(null);

        // Obtener la estación relacionada al alquiler a partir del ID proporcionado
        EstacionDto estacion = traerEstacion(alquilierFinalizadoDtoIn.getEstacionId());

        // Devuelve un error 404 si no se encontró alguno de los dos datos...
        if (estacion == null || alquiler == null){
            return ResponseEntity.notFound().build();
        }

        // Devuelve un error 400 si el id de la estación no coincide con el que tiene el alquiler...
        if (estacion.getId() == alquiler.getEstRetiro()){
            return ResponseEntity.badRequest().build();
        }

        Tarifa tarifa = null;

        // Obtener todas las tarifas de descuento...
        List<Tarifa> tarifasDescuento = tarifaRepositoty.findAllByDefinicion("C");

        // Itera las tarifas de descuento para encontrar la correspondiente a la fecha de retiro del alquiler...
        for (Tarifa aux : tarifasDescuento) {
            if (alquiler.getRetiro().getYear()==aux.getAnio()
                    && alquiler.getRetiro().getMonth().getValue()==aux.getMes()
                    && alquiler.getRetiro().getDayOfMonth()==aux.getDiaMes()
                    )
            {
                tarifa = aux;
                break;
            }
        }

        // Si no se encontró una tarifa de descuento para la fecha de retiro, obtener la tarifa por día de la semana...
        if (tarifa == null){
            tarifa = tarifaRepositoty.findByDiaSemana(alquiler.getRetiro().getDayOfWeek().getValue());
        }

        LocalDateTime fechaActual = LocalDateTime.now();

        // Obtener la duración del alquiler en minutos...
        long minutos = Duration.between(alquiler.getRetiro(), fechaActual).toMinutes();

        int horas = (int) minutos/60;

        minutos %= 60;

        // Si restan más de 31 minutos, se cuenta una hora extra
        // y se restan 31 minutos porque se van a cobrar los minutos extra a esos 31...
        if (minutos >= 31){
            horas += 1;
            minutos -=31;
        }

        // Calcular la distancia en kilómetros entre las estaciones de retiro y devolución...
        int distanciaKm = (int)(traerDistancia(alquiler.getEstRetiro(),estacion.getId())/1000);

        // Calcular el monto del alquiler utilizando la tarifa y los datos calculados...
        double monto = tarifa.getMontoFijoAlq()
                +tarifa.getMontoHora()*horas
                +tarifa.getMontoMinFrac()*minutos
                +distanciaKm*tarifa.getMontoKm();

        // Crea una instancia de alquiler con todos los datos obtenidos...
        Alquiler nuevo = new Alquiler(alquiler.getId(), alquiler.getIdCliente(), 2,alquiler.getEstRetiro(),
                estacion.getId(),alquiler.getRetiro(),fechaActual,monto,tarifa);

        alquilerRepository.save(nuevo);


        // Convertir el monto a la moneda especificada o a ARS si la moneda es nula
        MonedaDto montoConvertido;

        if (alquilierFinalizadoDtoIn.getMoneda()==null){
             montoConvertido = new MonedaDto("ARS",monto);
        }
        else {
            montoConvertido = convertirMoneda(alquilierFinalizadoDtoIn.getMoneda(),monto);
        }
        // Crea y devuelvo un DTO con los datos del alquiler finalizado...
        return  ResponseEntity.ok(new AlquilierFinalizadoDtoOut(
                nuevo.getId(),
                nuevo.getIdCliente(),
                nuevo.getEstado(),
                nuevo.getEstRetiro(),
                nuevo.getEstDevolucion(),
                nuevo.getRetiro(),
                nuevo.getDevolucion(),
                montoConvertido.getMoneda(),
                montoConvertido.getImporte(),
                nuevo.getTarifa().getId()));
    }

    // Método genérico para encontrar coincidencias entre dos listas...
    public static <T> List<T> encontrarCoincidencias(List<T> lista1, List<T> lista2) {
        List<T> coincidencias = new ArrayList<>();

        // Itera sobre la primera lista y agrega elementos a la lista de coincidencias
        // si están presentes en la segunda lista...
        for (T elemento : lista1) {
            if (lista2.contains(elemento)) {
                coincidencias.add(elemento);
            }
        }

        return coincidencias;
    }

    // Método para filtrar alquileres a partir de diferentes parámetros...
    @Override
    public List<AlquilerDto> alquileresFiltrados(Long idEstacionRetiro, Long idEstacionDevo, LocalDateTime fechaRetiro, LocalDateTime fechaDevo) {

        List<Alquiler> filtrada = new ArrayList<Alquiler>();
        List<Alquiler> aux = new ArrayList<Alquiler>();
        boolean filtrado = false;

        // Filtrar por estación de retiro si se proporciona el ID...
        if (idEstacionRetiro != null){
            filtrada.addAll(alquilerRepository.findAllByEstRetiro(idEstacionRetiro));
            filtrado = true;
        }

        // Filtrar por estación de devolución si se proporciona el ID
        if (idEstacionDevo != null){
            aux.addAll(filtrada);
            if (filtrado){
                filtrada = encontrarCoincidencias(aux, alquilerRepository.findAllByEstDevolucion(idEstacionDevo));
            }
            else {
                filtrada.addAll(alquilerRepository.findAllByEstDevolucion(idEstacionDevo));
                filtrado = true;
            }
        }

        // Filtrar por fecha de retiro si se proporciona la fecha
        if (fechaRetiro != null){
            aux.clear();
            aux.addAll(filtrada);
            if (filtrado){
                filtrada = encontrarCoincidencias(aux, alquilerRepository.findAllByRetiro(fechaRetiro));
            }
            else {
                filtrada.addAll(alquilerRepository.findAllByRetiro(fechaRetiro));
                filtrado = true;
            }
        }

        // Filtrar por fecha de devolución si se proporciona la fecha
        if (fechaDevo != null){
            aux.clear();
            aux.addAll(filtrada);
            if (filtrado){
                filtrada = encontrarCoincidencias(aux, alquilerRepository.findAllByDevolucion(fechaDevo));
            }
            else {
                filtrada.addAll(alquilerRepository.findAllByDevolucion(fechaDevo));
            }
        }

        return filtrada.stream().map(alquilerDtoMapper).toList();
    }


    // Métodos CRUD generales para alquileres...
    @Override
    public void add(AlquilerDto entity) {
        Alquiler alquiler = Optional.of(entity).map(alquilerMapper).get();
        alquilerRepository.save(alquiler);
    }

    @Override
    public AlquilerDto getById(Long id) {
        Alquiler alquiler = alquilerRepository.findById(id).orElse(null);
        return alquiler != null ? alquilerDtoMapper.apply(alquiler) : null;
    }

    @Override
    public List<AlquilerDto> getAll() {
        List<Alquiler> values = alquilerRepository.findAll();
        return values.stream().map(alquilerDtoMapper).toList();
    }

    @Override
    public AlquilerDto delete(Long id) {
        Optional<Alquiler> optionalAlquiler = alquilerRepository.findById(id);
        if(optionalAlquiler.isPresent()){
            alquilerRepository.delete(optionalAlquiler.get());
            return alquilerDtoMapper.apply(optionalAlquiler.get());
        }else {
            return null;
        }
    }

    @Override
    public void update(AlquilerDto entity) {
        Optional<Alquiler> optionalAlquiler = Stream.of(entity).map(alquilerMapper).findFirst();
        optionalAlquiler.ifPresent(alquilerRepository::save);

    }
}
