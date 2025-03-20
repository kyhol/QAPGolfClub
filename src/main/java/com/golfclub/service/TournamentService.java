package com.golfclub.service;

import com.golfclub.model.Member;
import com.golfclub.model.Tournament;
import com.golfclub.repository.TournamentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class TournamentService {

    private final TournamentRepository tournamentRepository;

    @Autowired
    public TournamentService(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    public List<Tournament> getAllTournaments() {
        return tournamentRepository.findAll();
    }

    public Tournament getTournamentById(Long id) {
        return tournamentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tournament not found with id: " + id));
    }

    public Tournament createTournament(Tournament tournament) {
        return tournamentRepository.save(tournament);
    }

    public Tournament updateTournament(Long id, Tournament tournamentDetails) {
        Tournament tournament = getTournamentById(id);

        tournament.setStartDate(tournamentDetails.getStartDate());
        tournament.setEndDate(tournamentDetails.getEndDate());
        tournament.setLocation(tournamentDetails.getLocation());
        tournament.setEntryFee(tournamentDetails.getEntryFee());
        tournament.setCashPrizeAmount(tournamentDetails.getCashPrizeAmount());

        return tournamentRepository.save(tournament);
    }

    public void deleteTournament(Long id) {
        Tournament tournament = getTournamentById(id);
        tournamentRepository.delete(tournament);
    }

    public List<Tournament> searchTournaments(LocalDate startDate, String location) {
        return tournamentRepository.searchTournaments(startDate, location);
    }

    public Set<Member> getTournamentMembers(Long id) {
        Tournament tournament = getTournamentById(id);
        return tournament.getMembers();
    }
}