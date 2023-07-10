package com.example.friendling.services;

import com.example.friendling.models.dtos.UserDto;
import com.example.friendling.models.entities.User;
import com.example.friendling.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomUserDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto createUser(UserDto userDto) {
        User entity = toEntity(userDto);
        entity = userRepository.save(entity);

        return toDto(entity);
    }



    public List<UserDto> getUserList() {
        List<User> users = userRepository.findAll();
        List<UserDto> result = new ArrayList<>();
        for (User user : users) {
            result.add(toDto(user));
        }
        return result;
    }

    public UserDto getUser(long id) {
        User entity = getEntity(id);
        return toDto(entity);

    }

    public UserDto updateUser(long id, UserDto userDto) {
        User entity = getEntity(id);
        entity.setEmail(userDto.getEmail());
        entity.setFirstName(userDto.getFirstName());
        entity.setLastName(userDto.getLastName());
        entity.setNickname(userDto.getNickname());
        entity.setPassword(passwordEncoder.encode(userDto.getPassword()));

        User user = userRepository.save(entity);

        return toDto(user);
    }

    public void deleteUser(long id) {

        userRepository.deleteById(id);
    }

    private User getEntity(long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new RuntimeException("User not found");
        }
    }
    private static UserDto toDto(User user) {
        return new UserDto(user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getNickname(),
                user.getRole());
    }
    private User toEntity(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setNickname(userDto.getNickname());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return user;
    }
}

