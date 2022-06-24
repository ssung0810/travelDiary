package com.ssung.travelDiary.domain.members;

import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRepositoryCustom {

    List<Member> findByMemberIdAndMoreType(Long member_id, String value);
}
