package com.golfclub.repository;

import com.golfclub.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByNameContaining(String name);

    List<Member> findByPhoneNumberContaining(String phoneNumber);

    List<Member> findByMembershipDuration(Integer membershipDuration);

    @Query("SELECT m FROM Member m JOIN m.tournaments t WHERE t.startDate = :startDate")
    List<Member> findByTournamentStartDate(@Param("startDate") LocalDate startDate);

    @Query("SELECT m FROM Member m JOIN m.tournaments t WHERE " +
            "(:name IS NULL OR m.name LIKE %:name%) AND " +
            "(:phoneNumber IS NULL OR m.phoneNumber LIKE %:phoneNumber%) AND " +
            "(:membershipType IS NULL OR m.membershipDuration = :membershipType)")
    List<Member> searchMembers(
            @Param("name") String name,
            @Param("phoneNumber") String phoneNumber,
            @Param("membershipType") Integer membershipType);
}