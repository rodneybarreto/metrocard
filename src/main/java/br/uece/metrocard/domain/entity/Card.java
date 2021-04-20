package br.uece.metrocard.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private Account account;

    @Column(name = "zone_type")
    private String zoneType;

    @Column(name = "acquire_date")
    private LocalDate acquireDate;

    public Card() {
    }

    public Card(Integer id) {
        this.id = id;
    }

}
