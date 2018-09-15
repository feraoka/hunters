package com.hunterstudios.hunters.repository;

import com.hunterstudios.hunters.entity.Member;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MemberRepository {
    /**
     * get member list sort by recent activities
     * @return member list
     */
    List<Member> getMembers();
}
