package com.main.spring.user.serviceImpl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.main.spring.domain.User;
import com.main.spring.user.dto.UserRequestSaveDTO;
import com.main.spring.user.repository.UserRepository;
import com.main.spring.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 일반인 회원가입
    @Override
    public User userJoin(UserRequestSaveDTO userRequestDTO) {
        String password = bCryptPasswordEncoder.encode(userRequestDTO.getPassword());
        userRequestDTO.setRole("ROLE_USER");
        userRequestDTO.setProvider("사이트 가입자");
        userRequestDTO.setPassword(password);
        User user = userRequestDTO.toEntity();
        return userRepository.save(user);
    }
}
