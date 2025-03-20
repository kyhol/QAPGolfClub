package com.golfclub.controller;

import com.golfclub.model.Member;
import com.golfclub.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity<List<Member>> getAllMembers() {
        List<Member> members = memberService.getAllMembers();
        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        Member member = memberService.getMemberById(id);
        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Member> createMember(@Valid @RequestBody Member member) {
        Member newMember = memberService.createMember(member);
        return new ResponseEntity<>(newMember, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Member> updateMember(@PathVariable Long id, @Valid @RequestBody Member member) {
        Member updatedMember = memberService.updateMember(id, member);
        return new ResponseEntity<>(updatedMember, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Member>> searchMembers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) Integer membershipType,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate tournamentStartDate) {

        List<Member> members;

        if (tournamentStartDate != null) {
            members = memberService.findByTournamentStartDate(tournamentStartDate);
        } else {
            members = memberService.searchMembers(name, phoneNumber, membershipType);
        }

        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    @PostMapping("/{memberId}/tournaments/{tournamentId}")
    public ResponseEntity<Member> addMemberToTournament(
            @PathVariable Long memberId,
            @PathVariable Long tournamentId) {

        Member member = memberService.addMemberToTournament(memberId, tournamentId);
        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    @DeleteMapping("/{memberId}/tournaments/{tournamentId}")
    public ResponseEntity<Member> removeMemberFromTournament(
            @PathVariable Long memberId,
            @PathVariable Long tournamentId) {

        Member member = memberService.removeMemberFromTournament(memberId, tournamentId);
        return new ResponseEntity<>(member, HttpStatus.OK);
    }
}