package software.sigma.sip.infrastructure.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import software.sigma.sip.application.service.CurrencyService;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/currency")
public class CurrencyController {
   private final CurrencyService service;

   @GetMapping("/{source}/{target}")
   @ResponseStatus(HttpStatus.OK)
   public Map<String, Map<String, String>> getCourse(@PathVariable String source, @PathVariable String target) {
      return Map.of(source, service.getValue(source, target));
   }

   @GetMapping("/{source}/{target}/{amount}")
   @ResponseStatus(HttpStatus.OK)
   public Map<String, Map<String, String>> getCourse(@PathVariable String source, @PathVariable String target, @PathVariable int amount) {
      Map<String, String> map = service.getValue(source, target);
      map.forEach((key, value) -> {
         map.put(key, new BigDecimal(value).multiply(new BigDecimal(amount)).toString());
      });
      return Map.of(source, map);
   }
}
