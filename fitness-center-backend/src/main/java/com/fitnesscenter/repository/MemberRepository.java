package com.fitnesscenter.repository;

import com.fitnesscenter.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByActiveTrue();
    Optional<Member> findByEmail(String email);
    List<Member> findByLastNameContainingIgnoreCase(String lastName);
}