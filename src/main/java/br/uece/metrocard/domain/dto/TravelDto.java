package br.uece.metrocard.domain.dto;

import br.uece.metrocard.domain.entity.Travel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TravelDto {

    private Integer id;
    private String tariff;
    private Integer cardId;

    public TravelDto() {
    }

    public TravelDto(Travel travel) {
        this.id = travel.getId();
        this.tariff = travel.getTariff().toString();
        this.cardId = travel.getCard().getId();
    }

}
