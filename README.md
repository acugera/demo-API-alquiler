# Simulación de una API de alquiler de bicicletas
   
## Introducción: 
En este Trabajo Práctico en grupo diseñado por la catedra de **Backend de aplicaciones** de la **Universidad Tecnológica Nacional Facultad Regional Córdoba (UTN FRC)**, se desarrolló un sistema de Backend para un servicio de alquiler de bicicletas. Se abordaron diversos temas que fueron tratados a lo largo del curso, incluyendo la implementación de EndPoints, la configuración de API Gateway y la integración de medidas de seguridad con un servidor externo, en este caso, Keycloak.
## Desarrollo:

### Estructura:
Para la estructura y organización de servicios se uso el siguiente diagrama proporcionado por la catedra:
![estructura tpi](https://drive.google.com/uc?export=download&id=1hXvJizhoUyrQ6UbMm49sueHmHh0mtAGF)

###  Base de Datos:
En el presente trabajo, se empleó una base de datos SQLite proporcionada por la catedra que consta de tres tablas fundamentales:

1.  **Alquileres:** Almacena la información relativa a los alquileres, tanto los solicitados como los finalizados, proporcionando un registro de dichas transacciones.
    
2.  **Estaciones:** Contiene los datos esenciales y la ubicación de cada centro de alquiler de bicicletas.
    
3.  **Tarifas:** Registra los precios asociados a cada día de la semana, así como a días promocionales, proporcionando una estructura organizada para la gestión de los costos asociados al servicio de alquiler.

![imagen base de datos](https://drive.google.com/uc?export=download&id=1XRjrfz5BjdkBV3tanvaCkE8yj1tHGHqL)

### Seguridad: 

En el ámbito de la seguridad, se implementó un servidor de Keycloak proporcionado por la cátedra.

Para obtener los tokens correspondientes a los usuarios designados para cada rol, se detallan las credenciales asociadas:

Rol **CLIENTES**:

-   **Usuario:** mail institucional. Ejemplo: 76004@sistemas.frc.utn.edu.ar
-   **Contraseña:** {legajo}-2023. Ejemplo: 76004-2023

Rol **ADMINISTRADORES:**

-   **Usuario:** nombre de grupo en minúsculas. Ejemplo: grutpi-31
-   **Contraseña:** {grupo-minúsculas}-2023#. Ejemplo: grutpi-31-2023#

### Endpoints:

En este proyecto se implementaron dos categorías de Endpoints, diferenciados por su accesibilidad:

**Endpoints Accesibles:** Estos Endpoints son públicos y pueden ser accedidos desde el cliente. Están expuestos en el puerto 8080, facilitando la interacción del usuario con el sistema de alquiler de bicicletas. Cabe destacar que estos pueden ser accedidos tanto por clientes como por administradores, dependiendo de sus privilegios dentro del sistema.
- Listar estaciones de la ciudad:
    - GET: http://localhost:8080/api/estacion
    - Autorizado: CLIENTE

	Lista todas las estaciones de la ciudad.
- Obtener estación por Id:
    - GET: http://localhost:8080/api/estacion/{id}
    - Autorizado: CLIENTE

	Trae la estación con el Id pasado por parámetro.
- Crear nueva estación:
    - POST: http://localhost:8080/api/estacion
    -  Autorizado: ADMINISTRADOR

	Guarda una nueva estación en la base de datos con sus datos pasados por JSON en el cuerpo de la petición.
- Obtener estación mas cercana:
    - GET: http://localhost:8080/api/estacion/cercana?latitud={Latitud}&longitud={Longitud}
    - Autorizado: CLIENTE

	Trae la estación mas cercana a la latitud y longitud pasadas por parámetro.
- Listar todos los alquileres:
    - GET: http://localhost:8080/api/alquiler
    - Autorizado: ADMINISTRADOR

	Lista todos los alquileres iniciados y finalizados.
- Obtener alquiler por Id:
    - GET: http://localhost:8080/api/alquiler/{id}
    - Autorizado: ADMINISTRADOR

	Trae el alquiler con el Id pasado por parámetro.
- Iniciar un alquiler:
    - POST: http://localhost:8080/api/alquiler
    -  Autorizado: CLIENTE

	Guarda un alquiler en la base de datos con sus datos iniciales.
- Finalizar un alquiler:
    - PATCH: http://localhost:8080/api/alquiler
    -  Autorizado: CLIENTE

	Actualiza un alquiler con los datos de devolución, calcula el monto final y informa todos los datos del alquiler con el monto expresado en la moneda que solicite el cliente, siendo no solicitada pesos argentinos por defecto.
- Listar alquileres filtrados:
    - GET: http://localhost:8080/api/alquiler/filtrar?idEstRetiro={idRetiro}&idEstDevo={idDevolucion}&fechaRetiro={fechaRetiro}&fechaDevo={fechaDevolucion}
    - Autorizado: ADMINISTRADOR

	Trae una lista de los alquileres filtrado por uno o mas de los siguientes datos: estación de retiro, pasada por Id, estación de devolución pasada por Id, fecha de retiro y fecha de devolución.

 

**Endpoints Restringidos:** Los Endpoints en esta categoría no están accesibles directamente desde el cliente. Se encuentran expuestos en los puertos 8081 y 8082, y su acceso está restringido para uso interno o futuras actualizaciones.
- Borrar estación por Id:
    - DELETE: http://localhost:8082/api/estacion/{id}

    Borra la estación con el Id pasado por parámetro.
- Actualizar estación:
    - PUT: http://localhost:8082/api/estacion

    Sobrescribe todos los datos de una estación pasada en el cuerpo de la petición con nuevos datos.
 - Obtener distancia entre estaciones:
    - GET: http://localhost:8082/api/estacion/distancia?estacion1={id1}&estacion2={id2}

    Trae la distancia entre 2 estaciones con sus Id parados por parámetro.
  - Listar tarifas:
    - GET: http://localhost:8081/api/tarifa

	Lista todas las tarifas.
- Obtener tarifa por Id:
    - GET: http://localhost:8081/api/tarifa/{id}

	Trae la tarifa con el Id pasado por parámetro.
- Crear nueva tarifa:
    - POST: http://localhost:8081/api/tarifa

	Guarda una nueva tarifa en la base de datos con sus datos pasados por JSON en el cuerpo de la petición.
- Borrar tarifa por Id:
    - DELETE: http://localhost:8081/api/tarifa/{id}

    Borra la tarifa con el Id pasado por parámetro.
- Actualizar tarifa:
    - PUT: http://localhost:8081/api/tarifa

    Sobrescribe todos los datos de una tarifa pasada en el cuerpo de la petición con nuevos datos.
 - Borrar alquiler por Id:
    - DELETE: http://localhost:8081/api/alquiler/{id}

    Borra el alquiler con el Id pasado por parámetro.
- Actualizar alquiler:
    - PUT: http://localhost:8081/api/alquiler

    Sobrescribe todos los datos de un alquiler pasado en el cuerpo de la petición con nuevos datos.


Estas separaciones se llevaron a cabo gracias a la implementación de API Gateway y Keycloak con Spring Security.

## Testing:
Se ha proporcionado un archivo JSON para importar en Postman con el objetivo de simplificar la prueba de los Endpoints.
