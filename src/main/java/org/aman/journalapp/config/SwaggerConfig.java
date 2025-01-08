package org.aman.journalapp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    public SwaggerConfig() {
        System.out.println("SwaggerConfig initialized");
    }

    @Bean
    public OpenAPI myCustomConfig(){
        return new OpenAPI()
                .info(new Info()
                        .title("Journal App API")
                        .description("text-techs: Java, MongoDB")
                        .version("1.0.0"));
    }


}
