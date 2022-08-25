package software.sigma.sip.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class CurrencyDto {
   private String status;
   private List<Map<String, String>> currency;
}
