package ru.bisoft.market.controller;

import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.protobuf.util.JsonFormat;

import lombok.RequiredArgsConstructor;
import ru.tinkoff.piapi.contract.v1.Bond;
import ru.tinkoff.piapi.core.InvestApi;

@RestController
@RequestMapping("/api/v1/instruments")
@RequiredArgsConstructor
public class InstrumentController {
    private final InvestApi investApi;

    @GetMapping
    public ResponseEntity<List<Bond>> findAll(Pageable pageable) {
        JsonFormat.Printer printer = JsonFormat.printer();
        // printer.print(null)
        List<Bond> bonds = investApi.getInstrumentsService().getAllBondsSync();
        //bonds.stream().map(Bond::getAllFields).map(b->b.entrySet().get);
        return ResponseEntity.ok(bonds);
    }
}
