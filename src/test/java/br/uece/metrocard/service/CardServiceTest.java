package br.uece.metrocard.service;

import br.uece.metrocard.domain.dto.CardDto;
import br.uece.metrocard.domain.entity.Card;
import br.uece.metrocard.repository.CardRepository;
import br.uece.metrocard.service.CardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CardServiceTest {

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private CardService cardService;

    @DisplayName("Deve cadastrar um cartao")
    @Test
    public void create() {
        CardDto cardReq = new CardDto();
        cardReq.setAccountId(2);
        cardReq.setZoneType("A");

        Card card = new Card();
        card.setId(1);
        card.setAccountId(2);
        card.setZoneType("A");

        when(cardRepository.save(any(Card.class))).thenReturn(card);

        CardDto cardRes = cardService.create(cardReq);
        assertEquals(1, cardRes.getId());
    }

}
