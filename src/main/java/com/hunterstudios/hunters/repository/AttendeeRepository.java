package com.hunterstudios.hunters.repository;

import com.hunterstudios.hunters.entity.Attendee;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AttendeeRepository {
    List<Attendee> getAttendees(int eventId);

    Attendee select(Attendee attendee);
    void upsert(Attendee attendee);
    void delete(Attendee attendee);
}
