package com.hunterstudios.hunters.controller;

import com.hunterstudios.hunters.entity.MemberForm;
import com.hunterstudios.hunters.service.MemberService;
import com.hunterstudios.hunters.validator.MemberValidator;
import javax.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/members")
public class AdminMemberController {

    @NonNull
    private MemberService memberService;

    @NonNull
    private MemberValidator memberValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(memberValidator);
    }

    @GetMapping("")
    public String getMembersForm(Model model) {
        model.addAttribute("members", memberService.getMemberList());
        model.addAttribute("form", new MemberForm());
        return "edit_member";
    }

    @PostMapping("")
    public String addMembers(@ModelAttribute("form") @Valid MemberForm form, BindingResult result, Model model) {
        model.addAttribute("members", memberService.getMemberList());
        if (result.hasErrors()) {
            return "edit_member";
        }
        memberService.addMember(form);
        return "redirect:/admin/members";
    }
}
