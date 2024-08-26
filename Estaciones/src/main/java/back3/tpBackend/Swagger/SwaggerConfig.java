package back3.tpBackend.Swagger;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class SwaggerConfig {

    @Bean
    public OpenAPI custom() {
        return new OpenAPI().info(new Info()
                .title("Documentación API Trabajo Practico Integrador")
                .description("BackEnd Applications UTN FRC")
                .version("2.0"));
    }
}