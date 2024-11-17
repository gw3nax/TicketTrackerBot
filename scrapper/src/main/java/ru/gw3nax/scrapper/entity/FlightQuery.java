package ru.gw3nax.scrapper.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "flight_queries")
public class FlightQuery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String fromPlace;
    private String toPlace;
    private LocalDate fromDate;
    private LocalDate toDate;

    private String currency;

    private BigDecimal price;
}