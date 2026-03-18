package com.example.demo.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

public class UserRequest {

    @Data
    public static class JoinDTO {
        @NotEmpty(message = "아이디는 필수 항목입니다.")
        @Size(min = 4, max = 20, message = "아이디는 4~20자 이내여야 합니다.")
        private String username;

        @NotEmpty(message = "비밀번호는 필수 항목입니다.")
        @Size(min = 4, max = 20, message = "비밀번호는 4~20자 이내여야 합니다.")
        private String password;

        @NotEmpty(message = "이메일은 필수 항목입니다.")
        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "올바른 이메일 형식이 아닙니다.")
        private String email;

        public User toEntity() {
            return User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .build();
        }
    }

    @Data
    public static class Login {
        private String username;
        private String password;
    }

}
