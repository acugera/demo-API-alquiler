package back3.tpBackend.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

// Se especifica que la clase será mapeada a una tabla de la base de datos...
@Entity
@Table(name = "TARIFAS")
@Data
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class Tarifa {

    // Se especifica el atributo que representa la PK en la tabla de la BD...
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;

    @Column(name = "TIPO_TARIFA")
    private final Long tipoTarifa;

    private final String definicion;

    @Column(name = "DIA_SEMANA")
    private final Integer diaSemana;

    @Column(name = "DIA_MES")
    private final Integer diaMes;

    private final Integer mes;

    private final Integer anio;

    @Column(name = "MONTO_FIJO_ALQUILER")
    private final Float montoFijoAlq;

    @Column(name = "MONTO_MINUTO_FRACCION")
    private final Float montoMinFrac;

    @Column(name = "MONTO_KM")
    private final Float montoKm;

    @Column(name = "MONTO_HORA")
    private final Float montoHora;
}
