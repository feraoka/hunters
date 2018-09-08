package com.hunterstudios.hunters.controller;

import com.hunterstudios.hunters.service.MemberService;
import com.hunterstudios.hunters.view.MemberPointView;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class MemberController {

    @NonNull
    private MemberService memberService;

    @GetMapping("/members")
    public String getMemberList(Model model) {
        MemberPointView members = memberService.getMembersInLastNEvents(6);
        model.addAttribute("members", members);
        return "member_list";
    }
}
