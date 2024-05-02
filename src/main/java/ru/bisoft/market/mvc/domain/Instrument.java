package ru.bisoft.market.mvc.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table
@Entity
public class Instrument {
    @Id
    private Integer id;
}
