package software.sigma.sip.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.sigma.sip.domain.entity.Status;
import software.sigma.sip.domain.entity.User;
import software.sigma.sip.domain.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
   private static final String ERROR_TEMPLATE = "User was not found by id";
   private final UserRepository userRepository;

   public List<User> getUsers() {
      return userRepository.findAll();
   }

   public User getUser(Long id) {
      return userRepository.findById(id).orElseThrow(() -> {
         throw new EntityNotFoundException(ERROR_TEMPLATE);
      });
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
      user.setStatus(Status.DISABLED);
      updateUser(user);
   }
}
