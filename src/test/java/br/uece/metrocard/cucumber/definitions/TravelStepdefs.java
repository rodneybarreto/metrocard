package br.uece.metrocard.cucumber.definitions;

import br.uece.metrocard.domain.dto.TravelDto;
import br.uece.metrocard.domain.entity.Account;
import br.uece.metrocard.domain.entity.Card;
import br.uece.metrocard.repository.CardRepository;
import br.uece.metrocard.repository.TravelRepository;
import br.uece.metrocard.service.TravelService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TravelStepdefs extends CucumberContext {

    @Autowired
    private TravelService travelService;

    private Card card1;
    private Card card2;
    private TravelDto travelReq1;
    private TravelDto travelReq2;

    @Given("que eu possuo um cartão de zona A com identificador {int}")
    public void queEuPossuoUmCartaoDeZonaA(Integer cardId) {
        Account account = new Account();
        account.setId(1);
        account.setOwner("Bob");
        account.setBalance(6.0);

        card1 = new Card(cardId);
        card1.setZoneType("A");
        card1.setAccount(account);
    }

    @When("eu tentar realizar uma viagem usando a tarifa {string}")
    public void euTentarRealizarUmaViagemNaZonaB(String tariff) {
        travelReq1 = new TravelDto();
        travelReq1.setTariff(tariff);
        travelReq1.setCardId(1);
    }

    @Then("eu serei informado que cartão de zona A não é permitido na zona B")
    public void euSereiInformadoQueCartaoDeZonaANaoEPermitidoNaZonaB() {
        CardRepository cardRepository = mock(CardRepository.class);
        TravelRepository travelRepository = mock(TravelRepository.class);

        when(cardRepository.findById(travelReq1.getCardId())).thenReturn(Optional.of(card1));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> travelService.create(travelReq1));
        assertTrue(exception.getMessage().contains("zona B não é permitida para este cartão."));
    }

    @Given("que eu possuo um cartão de zona B com identificador {int}")
    public void queEuPossuoUmCartaoDeZonaBComIdentificador(int cardId) {
        Account account = new Account();
        account.setId(2);
        account.setOwner("Teddy");
        account.setBalance(126.0);

        card2 = new Card(cardId);
        card2.setZoneType("B");
        card2.setAccount(account);
    }

    @When("eu tentar realizar uma viagem na zona B usando a tarifa {string}")
    public void euTentarRealizarUmaViagemNaZonaBUsandoATarifa(String tariff) {
        travelReq2 = new TravelDto();
        travelReq2.setTariff(tariff);
        travelReq2.setCardId(2);
    }

    @Then("eu terei o valor da tarifa debitado da minha conta")
    public void euTereiOValorDaTarifaDebitadoDaMinhaConta() throws Exception {
        CardRepository cardRepository = mock(CardRepository.class);
        TravelRepository travelRepository = mock(TravelRepository.class);

        when(cardRepository.findById(travelReq2.getCardId())).thenReturn(Optional.of(card2));
        when(travelRepository.findAllByTariffAndTravelDate(any(String.class), any(LocalDate.class)))
                .thenReturn(Collections.emptyList());

        TravelDto travelDto = travelService.create(travelReq2);

        assertEquals(119.0, travelDto.getBalance());
    }

}
