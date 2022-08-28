package software.sigma.sip.application.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import software.sigma.sip.domain.entity.Role;
import software.sigma.sip.domain.entity.Status;
import software.sigma.sip.domain.entity.User;
import software.sigma.sip.domain.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
   @Mock
   private UserRepository userRepository;
   @InjectMocks
   private UserService userService;

   @Test
   void getUsers() {
      List<User> userList = Arrays.asList(
              new User(1L, "roman", "password", Role.USER, "Roman", "Dovzhenko", "romandovzhenko@gmail.com",
                      "+380999999999", "Ukraine", "2003.03.14", Status.ACTIVE,null),
              new User(1L, "roman", "password", Role.USER, "Roman", "Dovzhenko", "romandovzhenko@gmail.com",
                      "+380999999999", "Ukraine", "2003.03.14", Status.ACTIVE,null));

      Mockito.when(userRepository.findAll()).thenReturn(userList);
      List<User> expectedList = userService.getUsers();

      assertThat(expectedList).isEqualTo(userList);
   }

   @Test
   void getUser_success() {
      User sourceUser = new User(1L, "roman", "password", Role.USER, "Roman", "Dovzhenko", "romandovzhenko@gmail.com",
              "+380999999999", "Ukraine", "2003.03.14", Status.ACTIVE,null);

      Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(sourceUser));
      User expectedUser = userService.getUser(1L);

      assertThat(expectedUser).isEqualTo(sourceUser);
   }

   @Test
   void getUser_failure() {
      Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());

      Assertions.assertThrows(EntityNotFoundException.class, () -> userService.getUser(1L));
   }

   @Test
   void addUser() {
      User user = new User(1L, "roman", "password", Role.USER, "Roman", "Dovzhenko", "romandovzhenko@gmail.com",
              "+380999999999", "Ukraine", "2003.03.14", Status.ACTIVE,null);

      userService.addUser(user);

      Mockito.verify(userRepository).save(user);
   }

   @Test
   void updateUser_success() {
      User user = new User(1L, "roman", "password", Role.USER, "Roman", "Dovzhenko", "romandovzhenko@gmail.com",
              "+380999999999", "Ukraine", "2003.03.14", Status.ACTIVE,null);

      Mockito.when(userRepository.existsById(1L)).thenReturn(true);
      userService.updateUser(user);

      Mockito.verify(userRepository).save(user);
   }

   @Test
   void updateUser_failure() {
      User user = new User(1L, "roman", "password", Role.USER, "Roman", "Dovzhenko", "romandovzhenko@gmail.com",
              "+380999999999", "Ukraine", "2003.03.14", Status.ACTIVE,null);

      Mockito.when(userRepository.existsById(1L)).thenReturn(false);

      Assertions.assertThrows(EntityNotFoundException.class, () -> userService.updateUser(user));
   }

   @Test
   void deactivateUser_success() {
      User user = new User(1L, "roman", "password", Role.USER, "Roman", "Dovzhenko", "romandovzhenko@gmail.com",
              "+380999999999", "Ukraine", "2003.03.14", Status.ACTIVE,null);

      Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
      Mockito.when(userRepository.existsById(1L)).thenReturn(true);
      userService.deactivateUser(1L);

      Mockito.verify(userRepository).save(new User(1L, "roman", "password", Role.USER, "Roman", "Dovzhenko", "romandovzhenko@gmail.com",
              "+380999999999", "Ukraine", "2003.03.14", Status.ACTIVE,null));
   }

   @Test
   void deactivateUser_failure() {
      Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());

      Assertions.assertThrows(EntityNotFoundException.class, () -> userService.deactivateUser(1L));
   }
}