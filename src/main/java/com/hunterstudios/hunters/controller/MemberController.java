package com.hunterstudios.hunters.controller;

import com.hunterstudios.hunters.service.EventService;
import com.hunterstudios.hunters.service.MemberService;
import com.hunterstudios.hunters.view.MemberPointView;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class MemberController {

    @NonNull
    private MemberService memberService;

    @NonNull
    private EventService eventService;

    @GetMapping("/members")
    public String getMemberList(Model model) {
        MemberPointView members = memberService.getMembersInLastNEvents(6);
        model.addAttribute("members", members);
        return "member_list";
    }

    @GetMapping("/members/attendance")
    public String getMemberAttendance(@RequestParam(name = "year", required = false) Integer requestedYear, Model model) {
        int year = requestedYear == null ? eventService.getRecentYear() : requestedYear;
        model.addAttribute("members", memberService.getMemberAttendance(year));
        return "member_attendance_list";
    }
}
