package com.example.demo.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void join(UserRequest.Join reqDTO) {
        // 1. DTO 데이터를 기반으로 User 엔티티 생성 (Builder 패턴 사용)
        var user = User.builder()
                .username(reqDTO.getUsername())
                .password(reqDTO.getPassword())
                .email(reqDTO.getEmail())
                .postcode(reqDTO.getPostcode())
                .address(reqDTO.getAddress())
                .detailAddress(reqDTO.getDetailAddress())
                .extraAddress(reqDTO.getExtraAddress())
                .build();
        
        // 2. 리포지토리를 통해 DB에 저장
        userRepository.save(user);
    }
}
