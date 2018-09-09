package com.hunterstudios.hunters.repository;

import com.hunterstudios.hunters.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserRepository {
    User select(String account);
}
