package com.ssung.travelDiary.domain.members;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("SELECT m.password FROM Member m WHERE username=:username")
    Optional<String> findByUsername(@Param("username") String nickname);
}
