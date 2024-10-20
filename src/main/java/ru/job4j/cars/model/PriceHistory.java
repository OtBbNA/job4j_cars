package ru.job4j.cars.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "PRICE_HISTORY")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PriceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    private long before;
    private long after;
    private LocalDateTime created;
}
