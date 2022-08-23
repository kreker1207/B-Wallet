package software.sigma.sip.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Getter
@Setter
@ToString
@Entity
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "wallet")
public class Wallet {
    @Id
    @Column(name = "id")
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "owner_id")
    private Long ownerId;
    @Column(name = "name")
    private String name;
    @Column(name = "currency")
    private String currency;
    @Column(name = "amount")
    private String amount;
    @Column(name = "created_at")
    String createdAt;
}
