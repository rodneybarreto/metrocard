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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

    @DisplayName("Retorna uma exceção na tentativa de usar cartão zona A na zona B")
    @Test
    void create1() {
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

        RuntimeException exception = assertThrows(RuntimeException.class, () -> travelService.create(travelReq));
        assertTrue(exception.getMessage().contains("zona B não é permitida para este cartão."));
    }

    @DisplayName("Cadastra uma viagem na zona A com tarifa única debitando da conta do usuário")
    @Test
    void create2() throws Exception {
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
        when(travelRepository.findAllByTariffAndTravelDate(any(String.class), any(LocalDate.class)))
                .thenReturn(Collections.emptyList());
        when(travelRepository.save(any(Travel.class))).thenReturn(travel);

        TravelDto travelRes = travelService.create(travelReq);
        assertEquals(1, travelRes.getId());
        assertEquals(0, account.getBalance());
    }

    @DisplayName("Cadastra uma viagem na zona B com tarifa única fazendo o checkout da viagem anterior")
    @Test
    void create3() throws Exception {
        TravelDto travelReq = new TravelDto();
        travelReq.setTariff("ZONE_B_UNIC");
        travelReq.setCardId(1);

        Account account = new Account();
        account.setId(1);
        account.setOwner("Bob");
        account.setBalance(26.0);

        Card card = new Card(travelReq.getCardId());
        card.setZoneType("B");
        card.setAccount(account);

        Travel travel = new Travel();
        travel.setId(1);
        travel.setTariff(Tariff.ZONE_B_UNIC);
        travel.setCard(card);
        travel.setTravelDate(LocalDate.now());
        travel.setCheckin(true);
        travel.setCheckout(false);

        Travel t1 = new Travel();
        travel.setId(1);
        travel.setTariff(Tariff.ZONE_B_UNIC);
        travel.setCard(card);
        travel.setTravelDate(LocalDate.now());
        travel.setCheckin(true);
        travel.setCheckout(false);

        List<Travel> previouslyTravels = new ArrayList<>();
        previouslyTravels.add(t1);

        when(cardRepository.findById(any(Integer.class))).thenReturn(Optional.of(card));
        when(travelRepository.findAllByTariffAndTravelDate(any(String.class), any(LocalDate.class)))
                .thenReturn(previouslyTravels);
        when(travelRepository.save(any(Travel.class))).thenReturn(travel);

        TravelDto travelRes = travelService.create(travelReq);
        assertEquals(19.0, account.getBalance());
        assertTrue(previouslyTravels.get(0).getCheckout());
    }

    @DisplayName("Cadastra uma viagem na zona A com tarifa dia debitando da conta do usuário")
    @Test
    void create4() throws Exception {
        TravelDto travelReq = new TravelDto();
        travelReq.setTariff("ZONE_A_DAY");
        travelReq.setCardId(1);

        Account account = new Account();
        account.setId(1);
        account.setOwner("Bob");
        account.setBalance(26.0);

        Card card = new Card(travelReq.getCardId());
        card.setZoneType("A");
        card.setAccount(account);

        Travel travel = new Travel();
        travel.setId(1);
        travel.setTariff(Tariff.ZONE_A_DAY);
        travel.setCard(card);
        travel.setTravelDate(LocalDate.now());
        travel.setCheckin(true);
        travel.setCheckout(false);

        when(cardRepository.findById(any(Integer.class))).thenReturn(Optional.of(card));
        when(travelRepository.findAllByTariffAndTravelDate(any(String.class), any(LocalDate.class)))
                .thenReturn(Collections.emptyList());
        when(travelRepository.save(any(Travel.class))).thenReturn(travel);

        TravelDto travelRes = travelService.create(travelReq);
        assertEquals(1, travelRes.getId());
        assertEquals(16.0, account.getBalance());
    }

    @DisplayName("Cadastra uma viagem na zona B com tarifa dia fazendo o checkout da viagem anterior")
    @Test
    void create5() throws Exception {
        TravelDto travelReq = new TravelDto();
        travelReq.setTariff("ZONE_B_DAY");
        travelReq.setCardId(1);

        Account account = new Account();
        account.setId(1);
        account.setOwner("Bob");
        account.setBalance(26.0);

        Card card = new Card(travelReq.getCardId());
        card.setZoneType("B");
        card.setAccount(account);

        Travel travel = new Travel();
        travel.setId(1);
        travel.setTariff(Tariff.ZONE_B_DAY);
        travel.setCard(card);
        travel.setTravelDate(LocalDate.now());
        travel.setCheckin(true);
        travel.setCheckout(false);

        Travel t1 = new Travel();
        travel.setId(1);
        travel.setTariff(Tariff.ZONE_B_DAY);
        travel.setCard(card);
        travel.setTravelDate(LocalDate.now());
        travel.setCheckin(true);
        travel.setCheckout(false);

        List<Travel> previouslyTravels = new ArrayList<>();
        previouslyTravels.add(t1);

        when(cardRepository.findById(any(Integer.class))).thenReturn(Optional.of(card));
        when(travelRepository.findAllByTariffAndTravelDate(any(String.class), any(LocalDate.class)))
                .thenReturn(previouslyTravels);
        when(travelRepository.save(any(Travel.class))).thenReturn(travel);

        TravelDto travelRes = travelService.create(travelReq);
        assertEquals(26.0, account.getBalance());
        assertTrue(previouslyTravels.get(0).getCheckout());
    }

}
