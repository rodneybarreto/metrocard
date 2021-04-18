package br.uece.metrocard.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String owner; // proprietário da conta

    private Double balance; // saldo

    public Account() {
    }

    public Account(Integer id) {
        this.id = id;
    }

    public void debit(Double value) {
        this.balance -= value;
    }

}
