package com.ssung.travelDiary.domain.members;

import java.util.List;

public interface MemberRepositoryCustom {

    List<Member> findByMemberIdAndMoreType(Long member_id, String value);
}
