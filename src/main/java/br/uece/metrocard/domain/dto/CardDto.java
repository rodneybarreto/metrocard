package br.uece.metrocard.domain.dto;

import br.uece.metrocard.domain.entity.Card;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CardDto {

    private Integer id;
    private Integer accountId;
    private String zoneType;
    private LocalDate acquireDate;

    public CardDto() {
    }

    public CardDto(Card card) {
        this.id = card.getId();
        this.accountId = card.getAccount().getId();
        this.zoneType = card.getZoneType();
        this.acquireDate = card.getAcquireDate();
    }

}
