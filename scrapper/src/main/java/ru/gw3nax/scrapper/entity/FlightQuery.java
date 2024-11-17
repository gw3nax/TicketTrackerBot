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
    Long id;
    String userId;
    String fromPlace;
    String toPlace;
    LocalDate fromDate;
    LocalDate toDate;

    String currency;

    BigDecimal price;
}