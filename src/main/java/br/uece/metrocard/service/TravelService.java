package br.uece.metrocard.service;

import br.uece.metrocard.domain.dto.TravelDto;
import br.uece.metrocard.domain.entity.Card;
import br.uece.metrocard.domain.entity.Travel;
import br.uece.metrocard.domain.enums.Tariff;
import br.uece.metrocard.repository.CardRepository;
import br.uece.metrocard.repository.TravelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class TravelService {

    private static final String ZONE_A = "A";
    private static final String ZONE_B = "B";

    private final TravelRepository travelRepository;
    private final CardRepository cardRepository;

    @Autowired
    public TravelService(TravelRepository travelRepository, CardRepository cardRepository) {
        this.travelRepository = travelRepository;
        this.cardRepository = cardRepository;
    }

    public TravelDto create(TravelDto travelDto) throws Exception {
        Card card = getCard(travelDto);

        if (card.getZoneType().equals(ZONE_A)) {

            Tariff tariff = getTariff(travelDto);
            if (tariff.getZone().equals(ZONE_B)) {
                throw new RuntimeException("Viagem não autorizada: a zona B não é permitida para este cartão.");
            }

        }

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

    private Tariff getTariff(TravelDto travelDto) {
        return Stream.of(Tariff.values())
                .filter(t -> t.toString().equals(travelDto.getTariff()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Viagem não autorizada: a zona informada não foi encontrada."));
    }

}
