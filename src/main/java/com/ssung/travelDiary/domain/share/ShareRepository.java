package com.ssung.travelDiary.domain.share;

import com.ssung.travelDiary.domain.members.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShareRepository extends JpaRepository<Share, Long> {
    @Query("select s from Share s join s.shareMember sm on sm.member = :member")
    List<Share> findList(@Param("member") Member member);
}
