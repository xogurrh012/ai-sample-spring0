package com.example.demo.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원 가입
     * @param joinDTO 회원가입 정보
     */
    @Transactional
    public void join(UserRequest.JoinDTO joinDTO) {
        // 비밀번호 암호화
        String encPassword = passwordEncoder.encode(joinDTO.getPassword());
        joinDTO.setPassword(encPassword);

        // 엔티티 변환 후 저장
        userRepository.save(joinDTO.toEntity());
    }

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
