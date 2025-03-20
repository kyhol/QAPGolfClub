package com.golfclub.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tournaments")
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Entry fee is required")
    @Positive(message = "Entry fee must be positive")
    private BigDecimal entryFee;

    @NotNull(message = "Cash prize amount is required")
    @Positive(message = "Cash prize amount must be positive")
    private BigDecimal cashPrizeAmount;

    @ManyToMany(mappedBy = "tournaments", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Member> members = new HashSet<>();
}