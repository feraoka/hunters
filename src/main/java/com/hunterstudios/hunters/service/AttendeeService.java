package com.hunterstudios.hunters.service;

import com.hunterstudios.hunters.entity.Batter;
import com.hunterstudios.hunters.entity.BatterForm;
import com.hunterstudios.hunters.entity.Member;
import com.hunterstudios.hunters.repository.AttendeeRepository;
import com.hunterstudios.hunters.repository.BatterRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AttendeeService {

    @NonNull
    private AttendeeRepository attendeeRepository;

    @NonNull
    private BatterRepository batterRepository;

    public BatterForm getBatterForm(int eventId) {
        BatterForm form = new BatterForm();
        List<Member> members = attendeeRepository.getMembers(eventId);
        form.setOrderList(getBattingOrder(eventId, members));
        form.setEventId(eventId);
        return form;
    }

    public List<Member> getAttendeeList(int eventId) {
        return attendeeRepository.getMembers(eventId);
    }

    @Transactional
    public BatterForm updateMemberList(BatterForm form) {
        List<Integer> orderList = form.getOrderList();
        List<Batter> records = batterRepository.getBatters(form.getEventId());
        records.stream().filter(r -> !orderList.contains(r.getMemberId())).forEach(r -> batterRepository.delete(r.getId()));

        Batter batter = new Batter();
        batter.setEventId(form.getEventId());
        for (int i = 0; i < orderList.size(); i++) {
            batter.setMemberId(orderList.get(i));
            batter.setBatOrder(i + 1);
            Batter record = batterRepository.get(batter);
            if (record == null) {
                batterRepository.insert(batter);
            } else {
                batter.setId(record.getId());
                batterRepository.update(batter);
            }
        }
        return form;
    }

    /**
     * 打順を返す。登録されていない場合、てきとーに割り当てる
     * @param eventId event id
     * @param members members who attended the event
     * @return batting order
     */
    private List<Integer> getBattingOrder(int eventId, List<Member> members) {
        List<Integer> list = batterRepository.getBatters(eventId).stream().map(Batter::getMemberId).collect(Collectors.toList());
        if (list.size() < members.size()) {
            for (Member member : members) {
                if (!list.contains(member.getId())) {
                    list.add(member.getId());
                }
            }
        }
        return list;
    }
}
