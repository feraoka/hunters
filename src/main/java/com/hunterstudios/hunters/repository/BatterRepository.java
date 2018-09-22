package com.hunterstudios.hunters.repository;

import com.hunterstudios.hunters.entity.Batter;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BatterRepository {
    Batter get(Batter batter);
    void delete(int id);
    void insert(Batter batter);
    void update(Batter batter);
    List<Batter> getBatters(int eventid);
    List<Integer> getBatterList(int eventId);
}
