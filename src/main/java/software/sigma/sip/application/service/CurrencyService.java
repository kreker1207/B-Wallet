package software.sigma.sip.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.sigma.sip.client.exception.CurrencyNotFoundException;
import software.sigma.sip.domain.client.CurrencyDataFeedClient;
import software.sigma.sip.infrastructure.dto.CurrencyDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CurrencyService {
   @Value("${currency-api.access-token}")
   private String accessToken;
   private final CurrencyDataFeedClient client;

   public Map<String, String> getValue(String source, List<String> target) {
      CurrencyDto currencyDto = client.getCourse(source, String.join(" ", target), accessToken);
      Map<String, String> map = new HashMap<>();
      if (currencyDto.getCurrency() != null) {
         currencyDto.getCurrency().forEach(currency -> {
            if (currency.get("error") != null) {
               throw new CurrencyNotFoundException(currency.get("error"));
            } else {
               map.put(currency.get("currency"), currency.get("value"));
            }
         });
      }
      return map;
   }
}
