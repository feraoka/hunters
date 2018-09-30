package com.hunterstudios.hunters.entity;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MemberForm {
    private int id;
    @NotBlank(message="必須です")
    private String nickname;
    private int status;
}
