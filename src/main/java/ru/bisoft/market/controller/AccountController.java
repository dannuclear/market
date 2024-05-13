package ru.bisoft.market.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.bisoft.market.dto.AccountDTO;
import ru.tinkoff.piapi.contract.v1.MoneyValue;
import ru.tinkoff.piapi.core.InvestApi;
import ru.tinkoff.piapi.core.models.Portfolio;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final InvestApi investApi;

    @GetMapping
    public ResponseEntity<Page<AccountDTO>> findAll() {
        List<AccountDTO> accounts = investApi.getUserService().getAccounts()
                .thenApply(list -> list.stream().map(AccountDTO::fromProto).toList()).join();
        Page<AccountDTO> page = new PageImpl<>(accounts, Pageable.ofSize(10), accounts.size());
        return ResponseEntity.ok(page);
    }

    // Начиная 25.03.2024 откройте в "песочнице" не более двух счетов, участвующих в
    // конкурсе. Названия счетов должны быть в следующем формате:
    // "contest2024:login/repo:1", где login - ваш логин на гитхабе, repo - название
    // репозитория, 1 - порядковый номер счета (1 или 2).

    @PostMapping("new")
    public ResponseEntity<String> add(@RequestBody AccountDTO dto) {
        investApi.getSandboxService().openAccountSync(dto.getName());
        return ResponseEntity.ok("Success");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        investApi.getSandboxService().closeAccountSync(id.toString());
        return ResponseEntity.ok("success");
    }

    @GetMapping("{id}/portfolio")
    public ResponseEntity<Portfolio> getMarginAttributes(@PathVariable UUID id) {
        Portfolio portfolio = investApi.getOperationsService().getPortfolioSync(id.toString());
        return ResponseEntity.ok(portfolio);
    }

    @PostMapping("{id}/payIn")
    public ResponseEntity<String> payIn(@PathVariable UUID id, @RequestParam BigDecimal value) {
        MoneyValue moneyValue = MoneyValue.newBuilder().setCurrency("rub").setUnits(value.intValue()).build();
        investApi.getSandboxService().payIn(id.toString(), moneyValue);
        return ResponseEntity.ok("success");
    }
}
