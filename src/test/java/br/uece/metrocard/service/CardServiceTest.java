package br.uece.metrocard.service;

import br.uece.metrocard.domain.dto.CardDto;
import br.uece.metrocard.domain.entity.Account;
import br.uece.metrocard.domain.entity.Card;
import br.uece.metrocard.repository.AccountRepository;
import br.uece.metrocard.repository.CardRepository;
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
public class CardServiceTest {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private CardService cardService;

    @DisplayName("Deve cadastrar um cartao")
    @Test
    public void create() throws Exception {
        CardDto cardReq = new CardDto();
        cardReq.setAccountId(2);
        cardReq.setZoneType("A");

        Account account = new Account(cardReq.getAccountId());

        Card card = new Card();
        card.setId(1);
        card.setAccount(account);
        card.setZoneType("A");

        when(accountRepository.findById(any(Integer.class))).thenReturn(Optional.of(account));
        when(cardRepository.save(any(Card.class))).thenReturn(card);

        CardDto cardRes = cardService.create(cardReq);
        assertEquals(1, cardRes.getId());
        assertEquals(2, cardRes.getAccountId());
    }

}
