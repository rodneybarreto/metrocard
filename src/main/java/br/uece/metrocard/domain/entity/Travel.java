package br.uece.metrocard.domain.entity;

import br.uece.metrocard.domain.enums.Tariff;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "travels")
public class Travel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private Tariff tariff;

    @OneToOne
    private Card card;

}
