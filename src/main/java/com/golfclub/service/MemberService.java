package com.golfclub.service;

import com.golfclub.model.Member;
import com.golfclub.model.Tournament;
import com.golfclub.repository.MemberRepository;
import com.golfclub.repository.TournamentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final TournamentRepository tournamentRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository, TournamentRepository tournamentRepository) {
        this.memberRepository = memberRepository;
        this.tournamentRepository = tournamentRepository;
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Member getMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + id));
    }

    public Member createMember(Member member) {
        return memberRepository.save(member);
    }

    public Member updateMember(Long id, Member memberDetails) {
        Member member = getMemberById(id);

        member.setName(memberDetails.getName());
        member.setAddress(memberDetails.getAddress());
        member.setEmail(memberDetails.getEmail());
        member.setPhoneNumber(memberDetails.getPhoneNumber());
        member.setMembershipStartDate(memberDetails.getMembershipStartDate());
        member.setMembershipDuration(memberDetails.getMembershipDuration());

        return memberRepository.save(member);
    }

    public void deleteMember(Long id) {
        Member member = getMemberById(id);
        memberRepository.delete(member);
    }

    public List<Member> searchMembers(String name, String phoneNumber, Integer membershipType) {
        return memberRepository.searchMembers(name, phoneNumber, membershipType);
    }

    public List<Member> findByTournamentStartDate(LocalDate startDate) {
        return memberRepository.findByTournamentStartDate(startDate);
    }

    @Transactional
    public Member addMemberToTournament(Long memberId, Long tournamentId) {
        Member member = getMemberById(memberId);
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new EntityNotFoundException("Tournament not found with id: " + tournamentId));

        member.addTournament(tournament);
        return memberRepository.save(member);
    }

    @Transactional
    public Member removeMemberFromTournament(Long memberId, Long tournamentId) {
        Member member = getMemberById(memberId);
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new EntityNotFoundException("Tournament not found with id: " + tournamentId));

        member.removeTournament(tournament);
        return memberRepository.save(member);
    }
}