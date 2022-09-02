package software.sigma.sip.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
public class OpenAPIConfig {
   private static final String SECURITY_SCHEME_NAME = "basicAuth";
   private static final String SCHEME = "basic";
   private static final Schema coefficientMapSchema = new Schema<>().addProperty("sourceCurrency", new Schema<>()
           .addProperty("targetCurrency1", new StringSchema()._default("coefficient"))
           .addProperty("targetCurrency2", new StringSchema()._default("coefficient"))
           .addProperty("targetCurrency3", new StringSchema()._default("coefficient")));
   private static final Schema valueMapSchema = new Schema<>().addProperty("sourceCurrency", new Schema<>()
           .addProperty("targetCurrency1", new StringSchema()._default("value"))
           .addProperty("targetCurrency2", new StringSchema()._default("value"))
           .addProperty("targetCurrency3", new StringSchema()._default("value")));

   private SecurityScheme createSecurityScheme() {
      return new SecurityScheme()
              .name(SECURITY_SCHEME_NAME)
              .type(SecurityScheme.Type.HTTP)
              .scheme(SCHEME);
   }

   @Bean
   public OpenAPI customOpenAPI() {
      return new OpenAPI()
              .components(new Components()
                      .addSchemas("CoefficientMapSchema", coefficientMapSchema)
                      .addSchemas("ValueMapSchema", valueMapSchema)
                      .addSecuritySchemes(SECURITY_SCHEME_NAME, createSecurityScheme())
              )
              .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME));
   }
}
