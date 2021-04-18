package br.uece.metrocard.service;

import br.uece.metrocard.domain.dto.CardDto;
import br.uece.metrocard.domain.entity.Card;
import br.uece.metrocard.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardService {

    private CardRepository cardRepository;

    @Autowired
    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public CardDto create(CardDto cardDto) {
        Card card = cardRepository.save(new Card(cardDto));
        return new CardDto(card);
    }

}
