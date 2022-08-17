package software.sigma.sip.domain.entity;

import lombok.*;
import javax.persistence.*;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Builder
@AllArgsConstructor
@Table(name = "wallets")

public class Wallet {
    @Id
    @Column(name = "id")
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
