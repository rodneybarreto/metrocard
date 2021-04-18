package br.uece.metrocard.controller;

import br.uece.metrocard.domain.dto.TravelDto;
import br.uece.metrocard.service.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("travels")
public class TravelController {

    private TravelService travelService;

    @Autowired
    public TravelController(TravelService travelService) {
        this.travelService = travelService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<TravelDto> create(@RequestBody TravelDto travelReq) {
        TravelDto travelRes = travelService.create(travelReq);
        return ResponseEntity.ok(travelRes);
    }

}
