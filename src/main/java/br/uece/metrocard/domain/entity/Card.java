package br.uece.metrocard.domain.entity;

import br.uece.metrocard.domain.dto.CardDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "account_id")
    private Integer accountId;

    @Column(name = "zone_type")
    private String zoneType;

    public Card() {
    }

    public Card(Integer id) {
        this.id = id;
    }

    public Card(CardDto cardDto) {
        this.accountId = cardDto.getAccountId();
        this.zoneType = cardDto.getZoneType();
    }

}
