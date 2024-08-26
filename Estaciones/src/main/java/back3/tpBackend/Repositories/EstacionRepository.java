package back3.tpBackend.Repositories;

import back3.tpBackend.Entities.Estacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Se define el repositorio que permite hacer el CRUD de las estaciones
// Ya que extiende JpaRepository...
@Repository
public interface EstacionRepository extends JpaRepository <Estacion,Long> {
}
