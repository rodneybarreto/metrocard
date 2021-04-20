package br.uece.metrocard.service;

import br.uece.metrocard.domain.dto.TravelDto;
import br.uece.metrocard.domain.entity.Account;
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

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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

    @DisplayName("Cadastra uma viagem na zona A ou B com tarifa única")
    @Test
    public void create1() throws Exception {
        TravelDto travelReq = new TravelDto();
        travelReq.setTariff("ZONE_A_UNIC");
        travelReq.setCardId(1);

        Account account = new Account();
        account.setId(1);
        account.setOwner("Bob");
        account.setBalance(6.0);

        Card card = new Card(travelReq.getCardId());
        card.setZoneType("A");
        card.setAccount(account);

        Travel travel = new Travel();
        travel.setId(1);
        travel.setTariff(Tariff.ZONE_A_UNIC);
        travel.setCard(card);
        travel.setTravelDate(LocalDate.now());
        travel.setCheckin(true);
        travel.setCheckout(false);

        when(cardRepository.findById(any(Integer.class))).thenReturn(Optional.of(card));
        when(travelRepository.save(any(Travel.class))).thenReturn(travel);

        TravelDto travelRes = travelService.create(travelReq);
        assertEquals(1, travelRes.getId());
        assertEquals(1, travelRes.getCardId());
    }

    @DisplayName("Retorna uma exceção na tentativa de usar cartão zona A na zona B")
    @Test
    public void create2() {
        TravelDto travelReq = new TravelDto();
        travelReq.setTariff("ZONE_B_UNIC");
        travelReq.setCardId(1);

        Account account = new Account();
        account.setId(1);
        account.setOwner("Bob");
        account.setBalance(6.0);

        Card card = new Card(travelReq.getCardId());
        card.setZoneType("A");
        card.setAccount(account);

        when(cardRepository.findById(any(Integer.class))).thenReturn(Optional.of(card));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            TravelDto travelRes = travelService.create(travelReq);
        });
        assertTrue(exception.getMessage().contains("zona B não é permitida para este cartão."));
    }

}
