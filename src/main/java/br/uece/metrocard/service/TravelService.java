package br.uece.metrocard.service;

import br.uece.metrocard.domain.dto.TravelDto;
import br.uece.metrocard.domain.entity.Card;
import br.uece.metrocard.domain.entity.Travel;
import br.uece.metrocard.domain.enums.Tariff;
import br.uece.metrocard.repository.TravelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TravelService {

    private TravelRepository travelRepository;

    @Autowired
    public TravelService(TravelRepository travelRepository) {
        this.travelRepository = travelRepository;
    }

    public TravelDto create(TravelDto travelDto) {
        Card card = new Card(travelDto.getCardId());

        Travel travel = new Travel();
        travel.setCard(card);
        travel.setTariff(Tariff.valueOf(travelDto.getTariff()));

        Travel travelSaved = travelRepository.save(travel);
        return new TravelDto(travelSaved);
    }

}
