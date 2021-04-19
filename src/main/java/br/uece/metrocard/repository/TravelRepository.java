package br.uece.metrocard.repository;

import br.uece.metrocard.domain.entity.Travel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface TravelRepository extends JpaRepository<Travel, Integer> {

    @Query(value = "SELECT * FROM travels WHERE tariff = :pTariff AND travel_date = TODAY", nativeQuery = true)
    Collection<Travel> findAllByTariffAndTravelDateToday(@Param("pTariff") String tariff);

}
