package com.example.demo.user;

import com.example.demo._core.utils.Resp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;
    private final HttpSession session;

    /**
     * 아이디 중복 체크 API (AJAX)
     * @param username 중복 확인할 사용자 아이디
     * @return 200 OK (true: 사용 가능, false: 중복됨)
     */
    @GetMapping("/api/check-username")
    public ResponseEntity<?> checkUsername(@RequestParam("username") String username) {
        boolean isAvailable = userService.checkUsername(username);
        return Resp.ok(isAvailable);
    }

}
