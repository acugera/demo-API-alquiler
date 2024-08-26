package back3.tpBackend.Services;

import back3.tpBackend.Entities.Tarifa;
import back3.tpBackend.Entities.dto.TarifaDto;
import back3.tpBackend.Repositories.TarifaRepositoty;
import back3.tpBackend.Services.dto.mappers.Tarifa.TarifaDtoMapper;
import back3.tpBackend.Services.dto.mappers.Tarifa.TarifaMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class TarifaServiceImpl implements TarifaService{

    private final TarifaRepositoty tarifaRepositoty;

    private final TarifaDtoMapper tarifaDtoMapper;

    private final TarifaMapper tarifaMapper;

    // Constructor de la clase servicio de la tarifa...
    public TarifaServiceImpl(TarifaRepositoty tarifaRepositoty, TarifaDtoMapper tarifaDtoMapper, TarifaMapper tarifaMapper) {
        this.tarifaRepositoty = tarifaRepositoty;
        this.tarifaDtoMapper = tarifaDtoMapper;
        this.tarifaMapper = tarifaMapper;
    }

    @Override
    public void add(TarifaDto entity) {
        Tarifa tarifa = Optional.of(entity).map(tarifaMapper).get();
        tarifaRepositoty.save(tarifa);
    }

    @Override
    public TarifaDto getById(Long id) {
        Tarifa tarifa = tarifaRepositoty.findById(id).orElse(null);
        return tarifa != null ? tarifaDtoMapper.apply(tarifa) : null;
    }

    @Override
    public List<TarifaDto> getAll() {
        List<Tarifa> values = tarifaRepositoty.findAll();
        return values.stream().map(tarifaDtoMapper).toList();
    }

    @Override
    public TarifaDto delete(Long id) {
        Optional<Tarifa> optionalTarifa = tarifaRepositoty.findById(id);
        if (optionalTarifa.isPresent()){
            tarifaRepositoty.delete(optionalTarifa.get());
            return tarifaDtoMapper.apply(optionalTarifa.get());
        }else {
            return null;
        }
    }

    @Override
    public void update(TarifaDto entity) {
        Optional<Tarifa> tarifaOptional = Stream.of(entity).map(tarifaMapper).findFirst();
        tarifaOptional.ifPresent(tarifaRepositoty::save);
    }
}
