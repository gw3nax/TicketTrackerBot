package ru.gw3nax.scrapper.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(name = "from_place")
    String fromPlace;

    @Column(name = "to_place")
    String toPlace;

    @Column(name = "from_date")
    LocalDate fromDate;

    @Column(name = "to_date")
    LocalDate toDate;

    String link;
}