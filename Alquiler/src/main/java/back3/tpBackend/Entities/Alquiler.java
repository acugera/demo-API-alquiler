package back3.tpBackend.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


// Se especifica que la siguiente clase será mapeada a una tabla en la base de datos...
@Entity
@Table(name = "ALQUILERES")
@Data
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class Alquiler {
    // Se determina la PK de esta clase...
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final long id;

    @Column(name = "ID_CLIENTE")
    private final Long idCliente;

    private final Integer estado;

    @Column(name = "ESTACION_RETIRO")
    private final Long estRetiro;


    @Column(name = "ESTACION_DEVOLUCION")
    private final Long estDevolucion;

    @Column(name = "FECHA_HORA_RETIRO")
    private final LocalDateTime retiro;

    @Column(name = "FECHA_HORA_DEVOLUCION")
    private final LocalDateTime devolucion;

    private final Double monto;

    // Se determina la relación de muchos a uno que existe entre alquiler y tarifa...
    @ManyToOne
    @JoinColumn(name = "ID_TARIFA")
    private final Tarifa tarifa;
}
