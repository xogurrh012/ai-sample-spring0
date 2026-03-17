package com.example.demo.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    /**
     * 아이디 중복 체크 (AJAX)
     * @param username 중복 확인할 사용자 아이디
     * @return true: 사용 가능, false: 중복됨
     */
    public boolean checkUsername(String username) {
        var userOP = userRepository.findByUsername(username);
        // 존재하면 중복(false), 없으면 사용 가능(true)
        return userOP.isEmpty();
    }

}
