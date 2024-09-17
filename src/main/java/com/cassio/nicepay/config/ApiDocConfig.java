package com.cassio.nicepay.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import java.io.IOException;
import java.io.InputStream;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

@Configuration
public class ApiDocConfig {

    private final ObjectMapper objectMapper;
    private final OpenAPI openAPI;

    @Autowired
    public ApiDocConfig(ObjectMapper objectMapper, ResourceLoader resourceLoader) throws IOException {
        this.objectMapper = objectMapper;
        this.openAPI = loadApiDocs(resourceLoader.getResource("classpath:/api-docs.json").getInputStream());
    }

    private OpenAPI loadApiDocs(InputStream inputStream) throws IOException {
        return objectMapper.readValue(inputStream, OpenAPI.class);
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return openAPI;
    }

    @Bean
    public SpringDocUtils configureSpringDoc() {
        return SpringDocUtils.getConfig()
            .addAnnotationsToIgnore(OpenAPIDefinition.class);
    }

}