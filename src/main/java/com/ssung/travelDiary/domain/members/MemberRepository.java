package com.ssung.travelDiary.domain.members;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
    Optional<Member> findByUsername(String username);
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @Query("select m from Member m where m.id <> :member_id and m.username like %:value%")
    List<Member> findByMemberIdAndMoreType(@Param("member_id") Long member_id, @Param("value") String value);
}
