package back3.tpBackend.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Date;

// Se define que la siguiente clase se mapeará a una tabla de la BD...
@Entity
@Table(name = "ESTACIONES")
@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@RequiredArgsConstructor
public class Estacion {
    // Se identifica al siguiente atributo como la PK para la clase...
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final long id;

    private final String nombre;

    @Column(name = "FECHA_HORA_CREACION")
    private Date creacion;

    private final Double latitud;

    private final Double longitud;

}
