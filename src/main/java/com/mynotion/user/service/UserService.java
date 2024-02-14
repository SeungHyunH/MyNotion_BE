package com.mynotion.user.service;

import com.mynotion.oauth.domain.OAuth;
import com.mynotion.user.model.User;
import com.mynotion.user.model.UserDto;
import com.mynotion.user.model.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void registerUser(UserDto userDto) {
        User user = convertToEntity(userDto);
        userRepository.save(user);
    }

    private User convertToEntity(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());

        OAuth oauth = new OAuth();
        oauth.setOauthId(userDto.getOauthId());
        user.setOauth(oauth);
        return user;
    }
}


