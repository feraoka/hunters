package com.hunterstudios.hunters.service;

import com.hunterstudios.hunters.entity.*;
import com.hunterstudios.hunters.exception.NotFoundException;
import com.hunterstudios.hunters.repository.AttendeeRepository;
import com.hunterstudios.hunters.repository.EventRepository;
import com.hunterstudios.hunters.repository.MemberRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AdminEventService {

    @NonNull
    private MemberRepository memberRepository;

    @NonNull
    private EventRepository eventRepository;

    @NonNull
    private AttendeeRepository attendeeRepository;

    private final int DEFAULT_MEMBERS_NUM = 15;

    public EventForm createEventForm(Integer id) {
        EventForm form = new EventForm();
        if (id != null) {
            Event event = eventRepository.select(id);
            if (event == null) {
                throw new NotFoundException("invalid event id: " + id);
            }
            form.setId(event.getId());
            form.setType(event.getType());
            form.setExpense(event.isExpense());
            form.setOpponent(event.getOpponent());
            form.setDateTime(event.getDate());
            form.setLocation(event.getLocation());
            form.setLocationNumber(event.getGround());
            form.setNote(event.getNote());
            List<Attendee> attendees = attendeeRepository.getAttendees(event.getId());
            Stream<Integer> attendeesId = attendeeRepository.getAttendees(event.getId()).stream().map(Attendee::getMemberId);
            Stream<Integer> blankId = IntStream.range(attendees.size(), DEFAULT_MEMBERS_NUM).mapToObj(i -> 0);
            form.setAttendees(Stream.concat(attendeesId, blankId).collect(Collectors.toList()));
        } else {
            List<Integer> attendees = createBlankAttendeeList(DEFAULT_MEMBERS_NUM);
            form.setAttendees(attendees);
        }
        return form;
    }

    public EventForm upsertEvent(EventForm form) {
        Event event = new Event();
        event.setId(form.getId());
        event.setDate(form.getDateTime());
        event.setLocation(form.getLocation());
        event.setGround(form.getLocationNumber());
        event.setType(form.getType());
        event.setOpponent(form.getOpponent());
        event.setStatus(EventStatus.DONE);
        event.setNote(form.getNote());
        event.setExpense(form.isExpense());
        if (form.getId() == 0) {
            eventRepository.insert(event);
            form.setId(event.getId());
        } else {
            eventRepository.update(event);
        }
        return form;
    }

    /*
    private void upsertAtendee(int eventId, List<Integer> attendees) {
        attendeeRepository.getAttendees(eventId)
                .stream().filter(a -> !attendees.contains(a.getMemberId()))
                .forEach(a -> attendeeRepository.delete(a.getId()));

        for (Integer a : attendees) {
            if (a == 0) {
                continue;
            }
            Attendee attendee = new Attendee();
            attendee.setEventId(eventId);
            attendee.setMemberId(a);
            Attendee result = attendeeRepository.get(attendee);

        }
    }
    */

    public List<Member> getMemberList() {
        Member blank = new Member();
        blank.setId(0);
        blank.setNickname("");
        List<Member> na = new ArrayList<>();
        na.add(blank);
        List<Member> members = memberRepository.getMembers();
        return Stream.concat(na.stream(), members.stream()).collect(Collectors.toList());
    }

    private List<Integer> createBlankAttendeeList(final int n) {
        return IntStream.range(0, n).mapToObj(i -> 0).collect(Collectors.toList());
    }
}
