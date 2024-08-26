package com.utn.frc.apigw.configs;

import org.springframework.http.HttpMethod;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtGrantedAuthoritiesConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class GatewayConfig {

    // Configuración de las rutas del gateway...
    @Bean
    public RouteLocator configurarRutas(RouteLocatorBuilder builder,
                                        @Value("${apigw.url-microservicio-estacion}") String uriEstacion,
                                        @Value("${apigw.url-microservicio-alquiler}") String uriAlquiler){
        return builder.routes()
                .route(p -> p
                        .path("/api/alquiler/**")
                        .uri(uriAlquiler)
                )
                .route(p -> p
                        .path("/api/tarifa/**")
                        .uri(uriAlquiler)
                )
                .route(p -> p
                        .path("/api/estacion/**")
                        .uri(uriEstacion)
                )
                .build();
    }

    // Configuración de seguridad del gateway...
    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) throws Exception {
        http.authorizeExchange(exchanges -> exchanges
                        .pathMatchers(HttpMethod.DELETE, "/api/alquiler/**").denyAll()
                        .pathMatchers(HttpMethod.DELETE, "/api/estacion/**").denyAll()
                        .pathMatchers("/api/estacion/distancia").denyAll()
                        .pathMatchers(HttpMethod.GET, "/api/estacion/**").hasRole("CLIENTE")
                        .pathMatchers(HttpMethod.POST,"/api/alquiler").hasRole("CLIENTE")
                        .pathMatchers(HttpMethod.PATCH,"/api/alquiler/finalizado").hasRole("CLIENTE")
                        .pathMatchers(HttpMethod.POST, "/api/estacion").hasRole("ADMINISTRADOR")
                        .pathMatchers(HttpMethod.GET, "/api/alquiler/**").hasRole("ADMINISTRADOR")
                        .pathMatchers(HttpMethod.PUT, "/api/estacion/**").denyAll()
                        .pathMatchers(HttpMethod.PUT, "/api/alquiler/**").denyAll()
                        .pathMatchers("/api/tarifa/**").denyAll()
                        .anyExchange()
                        .authenticated()
                )
                // Configuración del servidor de recursos OAuth2 y deshabilitación de CSRF...
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .csrf(csrf -> csrf.disable());
        return http.build();
    }

    // Configuración del convertidor de autenticación JWT...
    @Bean
    public ReactiveJwtAuthenticationConverter jwtAuthenticationConverter() {
        var jwtAuthenticationConverter = new ReactiveJwtAuthenticationConverter();
        var grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

        grantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");

        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(
                new ReactiveJwtGrantedAuthoritiesConverterAdapter(grantedAuthoritiesConverter));

        return jwtAuthenticationConverter;
    }

}
