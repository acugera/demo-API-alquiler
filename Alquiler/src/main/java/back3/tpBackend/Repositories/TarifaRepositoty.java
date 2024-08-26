package back3.tpBackend.Repositories;


import back3.tpBackend.Entities.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Clase repositorio de alquileres (se relaciona con la BD)...
//// Extiende JpaRepository que permite realizar el CRUD de la entidad...
@Repository
public interface TarifaRepositoty extends JpaRepository<Tarifa,Long> {

    // Métodos implementados por Spring Data JPA...
    List<Tarifa> findAllByDefinicion(String tipoTarifa);
    Tarifa findByDiaSemana(int dia);
}
