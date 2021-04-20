package br.uece.metrocard.service;

import br.uece.metrocard.domain.dto.CardDto;
import br.uece.metrocard.domain.entity.Account;
import br.uece.metrocard.domain.entity.Card;
import br.uece.metrocard.repository.AccountRepository;
import br.uece.metrocard.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CardService {

    private final CardRepository cardRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public CardService(CardRepository cardRepository, AccountRepository accountRepository) {
        this.cardRepository = cardRepository;
        this.accountRepository = accountRepository;
    }

    public CardDto create(CardDto cardDto) throws Exception {
        Account account = getAccount(cardDto.getAccountId());

        Card card = new Card();
        card.setAccount(account);
        card.setZoneType(cardDto.getZoneType());
        card.setAcquireDate(LocalDate.now());

        Card cardSaved = cardRepository.save(card);
        return new CardDto(cardSaved);
    }

    private Account getAccount(Integer accountId)  {
        return accountRepository
                .findById(accountId)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada."));
    }

}
