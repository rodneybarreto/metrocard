package br.uece.metrocard.repository;

import br.uece.metrocard.domain.entity.Travel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;

@Repository
public interface TravelRepository extends JpaRepository<Travel, Integer> {

    @Query(value = "SELECT * FROM travels WHERE tariff = :pTariff AND travel_date = :pTravelDate", nativeQuery = true)
    Collection<Travel> findAllByTariffAndTravelDate(
            @Param("pTariff") String tariff,
            @Param("pTravelDate") LocalDate travelDate
    );

    @Query(
            value = "SELECT * FROM travels "+
                    "WHERE tariff = :pTariff "+
                    "AND travel_date >= :pAcquireDate AND travel_date <= :pExpirationDate",
            nativeQuery = true
    )
    Collection<Travel> findAllByTariffAndTravelDateOnPeriod(
            @Param("pTariff") String tariff,
            @Param("pAcquireDate") LocalDate acquireDate,
            @Param("pExpirationDate") LocalDate expirationDate
    );

}
