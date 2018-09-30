package com.hunterstudios.hunters.validator;

import com.hunterstudios.hunters.entity.Member;
import com.hunterstudios.hunters.entity.MemberForm;
import com.hunterstudios.hunters.repository.MemberRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@RequiredArgsConstructor
@Component
public class MemberValidator implements Validator {

    @NonNull
    private MemberRepository memberRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return MemberForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        MemberForm form = (MemberForm) target;

        Member member = memberRepository.findByNickname(form.getNickname());
        if (member != null) {
            errors.rejectValue("nickname",
                    "MemberValidator.duplicateNickname", "同じ名前が存在します");
        }

        if (!Member.isValidStatus(form.getStatus())) {
            errors.rejectValue("status", "MemberValidator.invalidStatus", "サポートしていないステータスです");
        }
    }
}
