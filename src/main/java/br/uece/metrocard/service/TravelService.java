package br.uece.metrocard.service;

import br.uece.metrocard.domain.dto.TravelDto;
import br.uece.metrocard.domain.entity.Account;
import br.uece.metrocard.domain.entity.Card;
import br.uece.metrocard.domain.entity.Travel;
import br.uece.metrocard.domain.enums.Tariff;
import br.uece.metrocard.repository.AccountRepository;
import br.uece.metrocard.repository.CardRepository;
import br.uece.metrocard.repository.TravelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Stream;

@Service
public class TravelService {

    private static final String ZONE_A = "A";
    private static final String ZONE_B = "B";
    private static final String ZONE_A_UNIC = "ZONE_A_UNIC";
    private static final String ZONE_A_DAY = "ZONE_A_DAY";
    private static final String ZONE_A_WEEK = "ZONE_A_WEEK";
    private static final String ZONE_A_MONTH = "ZONE_A_MONTH";

    private final TravelRepository travelRepository;
    private final CardRepository cardRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public TravelService(TravelRepository travelRepository,
                         CardRepository cardRepository,
                         AccountRepository accountRepository) {
        this.travelRepository = travelRepository;
        this.cardRepository = cardRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public TravelDto create(TravelDto travelDto) throws Exception {
        Card card = getCard(travelDto);
        Account account = card.getAccount();
        Tariff tariff = getTariff(travelDto);
        LocalDate expirationDate = LocalDate.now();
        LocalDate travelDate = LocalDate.now();

        if (card.getZoneType().equals(ZONE_A)) {

            if (tariff.getZone().equals(ZONE_B)) {
                throw new RuntimeException("Viagem não autorizada: a zona B não é permitida para este cartão.");
            }

            switch (tariff.toString()) {
                case ZONE_A_UNIC:
                    account.debit(tariff.getValue());
                    break;
                case ZONE_A_DAY:
                    Collection<Travel> travelsToday = travelRepository
                            .findAllByTariffAndTravelDateToday(tariff.toString());
                    if (travelsToday.isEmpty()) {
                        account.debit(tariff.getValue());
                    }
                    break;
                case ZONE_A_WEEK:
                    expirationDate = card.getAcquireDate().plusDays(7);
                    if (travelDate.isAfter(expirationDate)) {
                        card.setAcquireDate(travelDate);
                        account.debit(tariff.getValue());
                    }
                    else {
                        Collection<Travel> travelsWeek = travelRepository
                                .findAllByTariffAndTravelDateOnPeriod(tariff.toString(), card.getAcquireDate(), expirationDate);
                        if (travelsWeek.isEmpty()) {
                            account.debit(tariff.getValue());
                        }
                    }
                    break;
                case ZONE_A_MONTH:
                    expirationDate = card.getAcquireDate().plusMonths(1);
                    if (travelDate.isAfter(expirationDate)) {
                        card.setAcquireDate(travelDate);
                        account.debit(tariff.getValue());
                    }
                    else {
                        Collection<Travel> travelsWeek = travelRepository
                                .findAllByTariffAndTravelDateOnPeriod(tariff.toString(), card.getAcquireDate(), expirationDate);
                        if (travelsWeek.isEmpty()) {
                            account.debit(tariff.getValue());
                        }
                    }
                    break;
                default:
            }

        }

        Travel travel = new Travel();
        travel.setCard(card);
        travel.setTariff(Tariff.valueOf(travelDto.getTariff()));
        travel.setTravelDate(travelDate);

        Travel travelSaved = travelRepository.save(travel);
        return new TravelDto(travelSaved);
    }

    private Card getCard(TravelDto travelDto) {
        return cardRepository
                .findById(travelDto.getCardId())
                .orElseThrow(() -> new RuntimeException("Viagem não autorizada: cartão não encontrado."));
    }

    private Tariff getTariff(TravelDto travelDto) {
        return Stream.of(Tariff.values())
                .filter(t -> t.toString().equals(travelDto.getTariff()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Viagem não autorizada: a zona informada não foi encontrada."));
    }

}
