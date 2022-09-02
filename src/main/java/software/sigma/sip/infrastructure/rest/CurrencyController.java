package software.sigma.sip.infrastructure.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import software.sigma.sip.application.service.CurrencyService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/currency")
@Tag(name = "Currency", description = "Receiving courses from an external API")
public class CurrencyController {
   private final CurrencyService service;

   @GetMapping("/{source}/{target}")
   @ResponseStatus(HttpStatus.OK)
   @Operation(summary = "Get courses for the source currency", responses = {
           @ApiResponse(responseCode = "200", description = "Found courses",
                   content = {
                           @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/CoefficientMapSchema"))
                   }),
           @ApiResponse(responseCode = "404", description = "Currency was not found", content = @Content),
           @ApiResponse(responseCode = "401", description = "Access denied for unauthorized user", content = @Content),
           @ApiResponse(responseCode = "403", description = "Not enough permissions", content = @Content)
   })
   public Map<String, Map<String, String>> getCourse(@PathVariable String source, @PathVariable List<String> target) {
      return Map.of(source, service.getValue(source, target));
   }

   @GetMapping("/{source}/{target}/{amount}")
   @ResponseStatus(HttpStatus.OK)
   @Operation(summary = "Get converted source currency to target", responses = {
           @ApiResponse(responseCode = "200", description = "Found values",
                   content = {
                           @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/ValueMapSchema"))
                   }),
           @ApiResponse(responseCode = "404", description = "Currency was not found", content = @Content),
           @ApiResponse(responseCode = "401", description = "Access denied for unauthorized user", content = @Content),
           @ApiResponse(responseCode = "403", description = "Not enough permissions", content = @Content)
   })
   public Map<String, Map<String, String>> getCourse(@PathVariable String source, @PathVariable List<String> target, @PathVariable int amount) {
      Map<String, String> map = service.getValue(source, target);
      map.forEach((key, value) -> {
         map.put(key, new BigDecimal(value).multiply(new BigDecimal(amount)).toString());
      });
      return Map.of(source, map);
   }
}
