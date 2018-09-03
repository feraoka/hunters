package com.hunterstudios.hunters.repository;

import com.hunterstudios.hunters.entity.Member;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MemberRepository {
    Member getMember(int id);
}
