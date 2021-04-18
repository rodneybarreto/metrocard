package br.uece.metrocard.service;

import br.uece.metrocard.domain.dto.TravelDto;
import br.uece.metrocard.domain.entity.Card;
import br.uece.metrocard.domain.entity.Travel;
import br.uece.metrocard.domain.enums.Tariff;
import br.uece.metrocard.repository.CardRepository;
import br.uece.metrocard.repository.TravelRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TravelServiceTest {

    @Mock
    private TravelRepository travelRepository;

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private TravelService travelService;

    @DisplayName("Deve cadastrar uma viagem")
    @Test
    public void create() throws Exception {
        TravelDto travelReq = new TravelDto();
        travelReq.setTariff("ZONE_A_UNIC");
        travelReq.setCardId(1);

        Card card = new Card(travelReq.getCardId());

        Travel travel = new Travel();
        travel.setId(1);
        travel.setTariff(Tariff.ZONE_A_UNIC);
        travel.setCard(card);

        when(cardRepository.findById(any(Integer.class))).thenReturn(Optional.of(card));
        when(travelRepository.save(any(Travel.class))).thenReturn(travel);

        TravelDto travelRes = travelService.create(travelReq);
        assertEquals(1, travelRes.getId());
        assertEquals(1, travelRes.getCardId());
    }

}
