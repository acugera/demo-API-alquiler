package back3.tpBackend.Repositories;

import back3.tpBackend.Entities.Alquiler;
import back3.tpBackend.Entities.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

// Clase repositorio de alquileres (se relaciona con la BD)...
// Extiende JpaRepository que permite realizar el CRUD de la entidad...
@Repository
public interface AlquilerRepository extends JpaRepository<Alquiler,Long> {

    //Métodos que son implementados por Spring Data JPA...
    List<Alquiler> findAllByEstRetiro(Long estRetiro);
    List<Alquiler> findAllByEstDevolucion(Long estDevolucion);

    List<Alquiler> findAllByRetiro(LocalDateTime retiro);
    List<Alquiler> findAllByDevolucion(LocalDateTime devolucion);
}
