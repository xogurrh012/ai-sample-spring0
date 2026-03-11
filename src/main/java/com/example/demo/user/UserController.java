package com.example.demo.user;

import org.springframework.stereotype.Controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final HttpSession session;

    @org.springframework.web.bind.annotation.GetMapping("/join-form")
    public String joinForm() {
        // 회원가입 페이지 이동
        return "join-form";
    }

    @org.springframework.web.bind.annotation.PostMapping("/join")
    public String join(UserRequest.Join reqDTO) {
        // 회원가입 서비스 호출
        userService.join(reqDTO);
        
        // 메인 페이지로 리다이렉트
        return "redirect:/";
    }
}
