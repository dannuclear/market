package ru.bisoft.market.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.tinkoff.piapi.core.InvestApi;

@RestController
@RequestMapping("/api/v1/main")
@RequiredArgsConstructor
public class MainController {
    private final InvestApi investApi;

    @GetMapping
    public ResponseEntity<String> all () {
        investApi.getInstrumentsService().getAllBonds()
        .thenAccept(bonds->{
            bonds.size();
        });
        return ResponseEntity.ok("ok");
    }
    
}
