package br.uece.metrocard.service;

import br.uece.metrocard.domain.entity.Account;
import br.uece.metrocard.repository.AccountRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@CucumberContextConfiguration
@SpringBootTest
public class CardStepsTest {

    @Autowired
    private AccountRepository accountRepository;

    private Optional<Account> acoount;

    @Given("que o usuário possue uma conta de número {int}")
    public void given(Integer accountId) {
        acoount = accountRepository.findById(accountId);
        assertEquals(accountId, acoount.get().getId());
    }

    @When("o usuário efetuar a solicitação de um novo cartão de zona {string}")
    public void when(String zone) {
        List<String> zones = Arrays.asList("A", "B");
        assertEquals(zone, zones.stream().filter(z -> z.equals(zone)).findFirst().get());
    }

    @Then("o usuário receberá um novo cartão da zona solicitada")
    public void then() {
    }
}
