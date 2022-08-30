package software.sigma.sip.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.sigma.sip.domain.entity.User;
import software.sigma.sip.domain.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
   private static final String ERROR_TEMPLATE = "User was not found by id";
   private final UserRepository userRepository;
   private final CurrencyService currencyService;

   public List<User> getUsers() {
      List<User> users = userRepository.findAll();
      users.forEach(this::addValueForFavCurrencies);
      return users;
   }

   public User getUser(Long id) {
      return addValueForFavCurrencies(userRepository.findById(id).orElseThrow(() -> {
         throw new EntityNotFoundException(ERROR_TEMPLATE);
      }));
   }

   private User addValueForFavCurrencies(User user) {
      user.getWalletList()
              .forEach(wallet -> {
                 Map<String, String> map = currencyService.getValue(wallet.getCurrency(), user.getFavCurrencies());
                 map.forEach((key, value) -> {
                    map.put(key, new BigDecimal(value).multiply(new BigDecimal(wallet.getAmount())).toString());
                 });
                 wallet.setConvertedCurrency(map);
              });
      return user;
   }

   public void addUser(User user) {
      userRepository.save(user);
   }

   public void updateUser(User user) {
      if (!userRepository.existsById(user.getId())) {
         throw new EntityNotFoundException(ERROR_TEMPLATE);
      }
      userRepository.save(user);
   }

   public void deactivateUser(Long id) {
      Optional<User> userOptional = userRepository.findById(id);
      if (userOptional.isEmpty()) {
         throw new EntityNotFoundException(ERROR_TEMPLATE);
      }
      User user = userOptional.get();
      user.setIsActive("false");
      updateUser(user);
   }
}
