package com.main.spring.user.service;

import com.main.spring.domain.User;
import com.main.spring.user.dto.UserRequestSaveDTO;

public interface UserService {
	
    User userJoin(UserRequestSaveDTO userRequestDTO);
}
