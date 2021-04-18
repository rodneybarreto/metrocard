package br.uece.metrocard.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MessageDto {

    private String message;

    public MessageDto() {
    }

    public MessageDto(String message) {
        this.message = message;
    }

}
