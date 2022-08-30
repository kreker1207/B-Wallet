package software.sigma.sip.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

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
   @Column(name = "is_active")
   private String isActive;
   @ElementCollection(targetClass = String.class)
   private List<String> favCurrencies;
   @OneToMany(cascade = CascadeType.ALL)
   @JoinColumn(name = "owner_id")
   private List<Wallet> walletList;
}
