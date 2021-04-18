package br.uece.metrocard.domain.dto;

import br.uece.metrocard.domain.entity.Card;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardDto {

    private Integer id;
    private Integer accountId;
    private String zoneType;

    public CardDto() {
    }

    public CardDto(Card card) {
        this.id = card.getId();
        this.accountId = card.getAccount().getId();
        this.zoneType = card.getZoneType();
    }

}
