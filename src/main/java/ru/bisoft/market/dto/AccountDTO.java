package ru.bisoft.market.dto;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.bisoft.market.utils.ProtoUtils;
import ru.tinkoff.piapi.contract.v1.AccessLevel;
import ru.tinkoff.piapi.contract.v1.Account;
import ru.tinkoff.piapi.contract.v1.AccountStatus;
import ru.tinkoff.piapi.contract.v1.AccountType;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
        // Идентификатор счёта.
        private String id;

        // Тип счёта.
        private AccountType type;

        // Название счёта.
        private String name;

        // Статус счёта.
        private AccountStatus status;

        // Дата открытия счёта в часовом поясе UTC.
        private LocalDateTime openedDate;

        // Дата закрытия счёта в часовом поясе UTC.
        private LocalDateTime closedDate;

        // Уровень доступа к текущему счёту (определяется токеном).
        private AccessLevel accessLevel;

        public static AccountDTO fromProto(Account account) {
                return AccountDTO.builder()
                                .id(account.getId())
                                .type(account.getType())
                                .name(account.getName())
                                .status(account.getStatus())
                                .openedDate(ProtoUtils.fromTimestamp(account.getOpenedDate()))
                                .closedDate(ProtoUtils.fromTimestamp(account.getClosedDate()))
                                .accessLevel(account.getAccessLevel())
                                .build();
        }
}
