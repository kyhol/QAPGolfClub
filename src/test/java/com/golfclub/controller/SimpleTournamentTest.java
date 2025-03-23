package com.golfclub.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.golfclub.GolfClubApp;
import com.golfclub.model.Tournament;
import com.golfclub.repository.TournamentRepository;
import com.golfclub.service.TournamentService;

@SpringBootTest(classes = GolfClubApp.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class SimpleTournamentTest {

    @Autowired
    private TournamentService tournamentService;

    @Autowired
    private TournamentRepository tournamentRepository;

    @Test
    public void testCreateTournament() {
        // Create a test tournament
        Tournament testTournament = new Tournament();
        testTournament.setStartDate(LocalDate.now().plusDays(30));
        testTournament.setEndDate(LocalDate.now().plusDays(33));
        testTournament.setLocation("Test Golf Course");
        testTournament.setEntryFee(new BigDecimal("150.00"));
        testTournament.setCashPrizeAmount(new BigDecimal("5000.00"));

        // Save the tournament
        Tournament savedTournament = tournamentService.createTournament(testTournament);

        // Verify
        assertNotNull(savedTournament.getId());
        assertEquals("Test Golf Course", savedTournament.getLocation());
        assertEquals(0, new BigDecimal("150.00").compareTo(savedTournament.getEntryFee()));

        // Clean up
        tournamentRepository.deleteById(savedTournament.getId());
    }
}