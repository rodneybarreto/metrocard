package br.uece.metrocard.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    private Integer id;

    private String username;

    private String password;

    private Integer accountId;

}
