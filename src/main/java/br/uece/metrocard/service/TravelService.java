package br.uece.metrocard.service;

import br.uece.metrocard.domain.dto.TravelDto;
import br.uece.metrocard.domain.entity.Card;
import br.uece.metrocard.domain.entity.Travel;
import br.uece.metrocard.domain.enums.Tariff;
import br.uece.metrocard.repository.CardRepository;
import br.uece.metrocard.repository.TravelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TravelService {

    private TravelRepository travelRepository;
    private CardRepository cardRepository;

    @Autowired
    public TravelService(TravelRepository travelRepository, CardRepository cardRepository) {
        this.travelRepository = travelRepository;
        this.cardRepository = cardRepository;
    }

    public TravelDto create(TravelDto travelDto) throws Exception {
        Card card = getCard(travelDto);

        Travel travel = new Travel();
        travel.setCard(card);
        travel.setTariff(Tariff.valueOf(travelDto.getTariff()));

        Travel travelSaved = travelRepository.save(travel);
        return new TravelDto(travelSaved);
    }

    private Card getCard(TravelDto travelDto) {
        return cardRepository
                .findById(travelDto.getCardId())
                .orElseThrow(() -> new RuntimeException("Viagem não autorizada: cartão não encontrado."));
    }

}
