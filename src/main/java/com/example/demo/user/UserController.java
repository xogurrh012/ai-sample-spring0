package com.example.demo.user;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final HttpSession session;

    /**
     * 회원 가입 처리
     * @param joinDTO 회원가입 정보
     * @param errors 유효성 검사 오류 (ValidAspect에서 처리)
     * @return redirect:/login-form
     */
    @PostMapping("/join")
    public String join(@Valid UserRequest.JoinDTO joinDTO, Errors errors) {
        userService.join(joinDTO);
        return "redirect:/login-form";
    }

    /**
     * 회원가입 폼 이동
     * @return user/join-form.mustache
     */
    @GetMapping("/join-form")
    public String joinForm() {
        return "user/join-form";
    }

}
