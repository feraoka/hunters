package com.hunterstudios.hunters.service;

import com.hunterstudios.hunters.entity.*;
import com.hunterstudios.hunters.helper.DateHelper;
import com.hunterstudios.hunters.repository.EventRepository;
import com.hunterstudios.hunters.repository.MemberRepository;
import com.hunterstudios.hunters.repository.Period;
import com.hunterstudios.hunters.view.MemberPointView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {

    @NonNull
    private EventRepository eventRepository;

    @NonNull
    private BattingService battingService;

    @NonNull
    private MemberRepository memberRepository;

    public MemberPointView getMembersInLastNEvents(int n) {
        MemberPointView view = new MemberPointView();
        List<EventAttendee> attendees = eventRepository.getEventAttendeesInLastNEvents(n);
        Map<Integer, MemberPointView.MemberPoint> memberMap = createMemberMap(n, attendees);
        for (EventAttendee e : attendees) {
            MemberPointView.Event event = new MemberPointView.Event();
            event.setDate(DateHelper.toDate(e.getDate()));
            switch (e.getType()) {
                case "練習":
                case "合宿":
                    event.setName("練習");
                    break;
                case "オーシャンリーグ":
                case "練習試合":
                    event.setName("試合");
                    break;
                default:
                    throw new RuntimeException("unexpected event type: " + e.getType());
            }
            view.getEventList().add(event);

            memberMap.values().forEach(m -> {
                boolean attend = e.hasMember(m.getId());
                m.getAttended().add(attend);
                if (attend) {
                    if (event.getName().equals("試合")) {
                        m.addDefensePoint(2);
                        m.addOffensePoint(2);
                    } else if (event.getName().equals("練習")) {
                        m.addDefensePoint(3);
                        m.addOffensePoint(3);
                    }
                }
            });
        }

        Map<Integer, BattingSummary> battingMap = battingService.getBattingSummaryListOfLastNGames(3);
        memberMap.keySet().forEach(m -> {
            BattingSummary batting = battingMap.get(m);
            if (batting != null) {
                memberMap.get(m).addOffensePoint((float)batting.getNoi() / 100f);
                memberMap.get(m).setNoi(batting.getNoi());
            }
        });

        List<MemberPointView.MemberPoint> memberList = new ArrayList<>(memberMap.values());
        memberList.sort((a, b) -> {
           if (a.getDefensePoint() == b.getDefensePoint()) {
               return b.getNoi() - a.getNoi();
           }
           return b.getDefensePoint() - a.getDefensePoint();
        });
        view.setMemberList(memberList);

        return view;
    }

    private Map<Integer, MemberPointView.MemberPoint> createMemberMap(int n, List<EventAttendee> attendees) {
        Map<Integer, MemberPointView.MemberPoint> map = new HashMap<>();
        for (EventAttendee e : attendees) {
            for (Member m : e.getMembers()) {
                if (!map.containsKey(m.getId())) {
                    MemberPointView.MemberPoint member = new MemberPointView.MemberPoint();
                    member.setId(m.getId());
                    member.setName(m.getNickname());
                    member.setDefensePoint(0);
                    member.setOffensePoint(0f);
                    member.setAttended(new ArrayList<>());
                    map.put(member.getId(), member);
                }
            }
        }
        return map;
    }

    public List<Member> getMemberList() {
        return memberRepository.getMembers();
    }

    public void addMember(MemberForm form) {
        Member member = new Member();
        member.setNickname(form.getNickname());
        member.setStatus(form.getStatus());
        memberRepository.add(member);
    }

    public List<MemberAttendance> getMemberAttendance(int year) {
        Period period = DateHelper.createYearPeriod(year);
        return memberRepository.getMemberAttendance(period);
    }
}
