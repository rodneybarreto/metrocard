package br.uece.metrocard.domain.dto;

import br.uece.metrocard.domain.entity.Travel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TravelDto {

    private Integer id;
    private String tariff;
    private Integer cardId;
    private LocalDate travelDate;

    public TravelDto() {
    }

    public TravelDto(Travel travel) {
        this.id = travel.getId();
        this.tariff = travel.getTariff().toString();
        this.cardId = travel.getCard().getId();
        this.travelDate = travel.getTravelDate();
    }

}
