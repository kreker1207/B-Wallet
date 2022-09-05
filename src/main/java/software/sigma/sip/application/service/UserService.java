package software.sigma.sip.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.sigma.sip.domain.entity.Status;
import software.sigma.sip.domain.entity.User;
import software.sigma.sip.domain.repository.UserRepository;
import software.sigma.sip.exception.EntityNotFoundByIdException;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final String ERROR_TEMPLATE = "User was not found by id";
    private final UserRepository userRepository;
    private final CurrencyService currencyService;

    public List<User> getUsers() {
        return userRepository.findAll().stream().map(this::addValueForFavCurrencies).toList();
    }

    public User getUser(Long id) {
        return addValueForFavCurrencies(userRepository.findById(id).orElseThrow(() -> {
           throw new EntityNotFoundByIdException(ERROR_TEMPLATE);
        }));
    }

   private User addValueForFavCurrencies(User user) {
      user.getWalletList().forEach(wallet ->
              wallet.setConvertedCurrency(currencyService.getValue(wallet.getCurrency(), user.getFavCurrencies())
                      .entrySet().stream()
                      .collect(Collectors.toMap(Map.Entry::getKey, entry ->
                              new BigDecimal(entry.getValue()).multiply(new BigDecimal(wallet.getAmount())).toString()))));
      return user;
   }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public void updateUser(User user) {
        if (!userRepository.existsById(user.getId())) {
           throw new EntityNotFoundByIdException(ERROR_TEMPLATE);
        }
        userRepository.save(user);
    }

    public void deactivateUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
           throw new EntityNotFoundByIdException(ERROR_TEMPLATE);
        }
        User user = userOptional.get();
        user.setStatus(Status.DISABLED);
        updateUser(user);
    }
}
