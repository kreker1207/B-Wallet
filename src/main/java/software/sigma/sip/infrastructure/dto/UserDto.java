package software.sigma.sip.infrastructure.dto;

import lombok.Builder;
import lombok.Data;
import software.sigma.sip.domain.entity.User;
import software.sigma.sip.domain.entity.Wallet;

import java.util.List;

@Data
@Builder
public class UserDto {
   private Long id;
   private String name;
   private String surname;
   private String email;
   private String phone;
   private String country;
   private String birthDate;
   private String isActive;
   private List<Wallet> walletList;

   public static UserDto toUserDto(User user) {
      return UserDto.builder().id(user.getId())
              .name(user.getName())
              .surname(user.getSurname())
              .email(user.getEmail())
              .phone(user.getPhone())
              .country(user.getCountry())
              .birthDate(user.getBirthDate())
              .isActive(user.getIsActive())
              .walletList(user.getWalletList()).build();
   }

   public User toUser() {
      return new User(id, name, surname, email, phone, country, birthDate, isActive, walletList);
   }
}
