package ru.gw3nax.scrapper.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "flights")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String fromPlace;
    String toPlace;
    LocalDate fromDate;
    LocalDate toDate;
    String link;
}