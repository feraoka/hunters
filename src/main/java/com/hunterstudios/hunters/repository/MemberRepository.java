package com.hunterstudios.hunters.repository;

import com.hunterstudios.hunters.entity.Member;
import com.hunterstudios.hunters.entity.MemberAttendance;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MemberRepository {
    /**
     * get member list sort by recent activities
     * members who did not attend any activities won't be included
     * @return member list
     */
    List<Member> getMembersSortByRecentActivity();

    /**
     * get member list sort by id
     * @return member list
     */
    List<Member> getMembers();

    /**
     * find member by nickname
     * @param nickname nickname to search
     * @return member whose name is same. returns null if no member found
     */
    Member findByNickname(String nickname);

    /**
     * add a new member
     * @param member member to be added
     */
    void add(Member member);

    /**
     * get member attendance of the period
     * @param period to query
     * @return list of members who attende activities in the period
     */
    List<MemberAttendance> getMemberAttendance(Period period);
}
