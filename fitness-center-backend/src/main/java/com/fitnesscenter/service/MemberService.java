package com.fitnesscenter.service;

import com.fitnesscenter.model.Member;
import com.fitnesscenter.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public List<Member> getActiveMembers() {
        return memberRepository.findByActiveTrue();
    }

    public Optional<Member> getMemberById(Long id) {
        return memberRepository.findById(id);
    }

    public Optional<Member> getMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public List<Member> searchMembersByLastName(String lastName) {
        return memberRepository.findByLastNameContainingIgnoreCase(lastName);
    }

    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }

    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }

    public boolean deactivateMember(Long id) {
        Optional<Member> memberOpt = memberRepository.findById(id);
        if (memberOpt.isPresent()) {
            Member member = memberOpt.get();
            member.setActive(false);
            memberRepository.save(member);
            return true;
        }
        return false;
    }
}