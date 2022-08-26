package software.sigma.sip.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Builder
@EqualsAndHashCode
@Table(name = "user")
public class User {
   @Id
   @Column(name = "id")
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   @Column(name = "username")
   private String userName;
   @Column(name = "password")
   private String password;
   @Enumerated(value = EnumType.STRING)
   @Column(name = "roles")
   private Role roles;
   @Column(name = "name")
   private String name;
   @Column(name = "surname")
   private String surname;
   @Column(name = "email")
   private String email;
   @Column(name = "phone")
   private String phone;
   @Column(name = "country")
   private String country;
   @Column(name = "birthDate")
   private String birthDate;
   @Enumerated(value = EnumType.STRING)
   @Column(name = "status")
   private Status status;
}
