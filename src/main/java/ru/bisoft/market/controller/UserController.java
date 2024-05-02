package ru.bisoft.market.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.tinkoff.piapi.contract.v1.GetInfoResponse;
import ru.tinkoff.piapi.core.InvestApi;

@RestController
@RequestMapping("/api/v1/user/current")
@RequiredArgsConstructor
public class UserController {
    private final InvestApi investApi;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getUserInfo() {
        GetInfoResponse response = investApi.getUserService().getInfoSync();
        Map<String, Object> data = new HashMap<>();
        data.put("tariff", response.getTariff());
        return ResponseEntity.ok(data);
    }

}
