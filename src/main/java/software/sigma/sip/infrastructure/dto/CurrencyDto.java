package software.sigma.sip.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class CurrencyDto {
   @JsonProperty("status")
   private String status;
   @JsonProperty("currency")
   private List<Map<String, String>> currency;
}
