package com.bookstore_service.swagger
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.OpenAPI
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("Bookstore Service")
                    .version("1.0.0")
                    .description("These are the available endpoints for Bookstore API.")

            )
    }
}