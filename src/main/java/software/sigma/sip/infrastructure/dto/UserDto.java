package software.sigma.sip.infrastructure.dto;

import lombok.Builder;
import lombok.Data;
import software.sigma.sip.domain.entity.Role;
import software.sigma.sip.domain.entity.Status;
import software.sigma.sip.domain.entity.User;

import java.util.Collections;
import java.util.List;

@Data
@Builder
public class UserDto {
   private Long id;
   private String username;
   private String password;
   private Role roles;
   private String name;
   private String surname;
   private String email;
   private String phone;
   private String country;
   private String birthDate;
   private Status status;
   private List<String> favCurrencies;
   private List<WalletDto> walletList;

   public static UserDto toUserDto(User user) {
      return UserDto.builder().id(user.getId())
              .username(user.getUsername())
              .password(user.getPassword())
              .roles(user.getRoles())
              .name(user.getName())
              .surname(user.getSurname())
              .email(user.getEmail())
              .phone(user.getPhone())
              .country(user.getCountry())
              .birthDate(user.getBirthDate())
              .status(user.getStatus())
              .favCurrencies(user.getFavCurrencies())
              .walletList(user.getWalletList().stream().map(WalletDto::toWalletDto).toList()).build();
   }

   public User toUser() {
      return new User(id, username, password, roles, name, surname, email, phone, country, birthDate, status, favCurrencies,
              walletList == null ? Collections.emptyList() : walletList.stream().map(WalletDto::toWallet).toList());
   }
}
