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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TravelStepdefs extends CucumberContext {

    @Autowired
    private TravelService travelService;

    private Card card;
    private TravelDto travelReq;

    @Given("que eu possuo um cartão de zona A com identificador {int}")
    public void queEuPossuoUmCartaoDeZonaA(Integer cardId) {
        Account account = new Account();
        account.setId(1);
        account.setOwner("Bob");
        account.setBalance(6.0);

        card = new Card(cardId);
        card.setZoneType("A");
        card.setAccount(account);
    }

    @When("eu tentar realizar uma viagem usando a tarifa {string}")
    public void euTentarRealizarUmaViagemNaZonaB(String tariff) {
        travelReq = new TravelDto();
        travelReq.setTariff(tariff);
        travelReq.setCardId(1);
    }

    @Then("eu serei informado que cartão de zona A não é permitido na zona B")
    public void euSereiInformadoQueCartaoDeZonaANaoEPermitidoNaZonaB() {
        CardRepository cardRepository = mock(CardRepository.class);
        TravelRepository travelRepository = mock(TravelRepository.class);

        when(cardRepository.findById(travelReq.getCardId())).thenReturn(Optional.of(card));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> travelService.create(travelReq));
        assertTrue(exception.getMessage().contains("zona B não é permitida para este cartão."));
    }

}
