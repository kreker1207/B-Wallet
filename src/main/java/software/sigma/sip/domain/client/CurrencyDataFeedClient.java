package software.sigma.sip.domain.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import software.sigma.sip.infrastructure.dto.CurrencyDto;

@FeignClient(name = "currencyDataFeedClient", url = "${currency-api.url}")
public interface CurrencyDataFeedClient {
   @GetMapping(value = "/source_currency.php")
   CurrencyDto getCourse(@RequestParam String source, @RequestParam String target, @RequestParam String token);
}
