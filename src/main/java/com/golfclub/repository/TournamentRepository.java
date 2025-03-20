package com.golfclub.repository;

import com.golfclub.model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {

    List<Tournament> findByStartDate(LocalDate startDate);

    List<Tournament> findByLocationContaining(String location);

    @Query("SELECT t FROM Tournament t WHERE " +
            "(:startDate IS NULL OR t.startDate = :startDate) AND " +
            "(:location IS NULL OR t.location LIKE %:location%)")
    List<Tournament> searchTournaments(
            @Param("startDate") LocalDate startDate,
            @Param("location") String location);
}