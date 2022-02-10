package com.ssung.travelDiary.domain.members;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Query("SELECT m.password FROM Member m WHERE nickname=:nickname")
    Optional<String> findByNickname(@Param("nickname") String nickname);
}
