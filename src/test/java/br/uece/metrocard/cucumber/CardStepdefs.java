package br.uece.metrocard.cucumber;

import br.uece.metrocard.domain.entity.Account;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@CucumberContextConfiguration
public class CardStepdefs {

    private List<Account> accounts;
    private List<String> zones;

    @Given("que o sistema possui contas previamente cadastradas")
    public void queOSistemaPossuiContasPreviamenteCadastradas() {
        Account a1 = new Account();
        a1.setId(1);
        a1.setOwner("Bob");
        a1.setBalance(6.0);

        Account a2 = new Account();
        a2.setId(2);
        a2.setOwner("Teddy");
        a2.setBalance(126.0);

        accounts = Arrays.asList(a1, a2);
    }

    @And("que o sistema possui zonas previamente cadastradas")
    public void queOSistemaPossuiZonasPreviamenteCadastradas() {
        zones = Arrays.asList("A", "B");
    }

    @Given("que eu possuo uma conta de número {int}")
    public void queEuPossuoUmaContaDeNumeroAccountId(Integer accountId) {
        assertEquals(accountId,
                accounts.stream().filter(a -> a.getId().equals(accountId)).findFirst().get().getId());
    }

    @When("eu efetuar a solicitação de um novo cartão de zona {string}")
    public void euEfetuarASolicitacaoDeUmNovoCartaoDeZonaZone(String zone) {
        assertEquals(zone, zones.stream().filter(z -> z.equals(zone)).findFirst().get());
    }

    @Then("eu espero receber um novo cartão da zona solicitada")
    public void euEsperoReceberUmNovoCartaoDaZonaSolicitada() {
    }

}
