package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepositoryImpl;

import java.util.List;

import static ru.practicum.shareit.user.dto.UserMapper.toUser;
import static ru.practicum.shareit.user.dto.UserMapper.toUserDto;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepositoryImpl userRepository;

    public List<User> getUsers() {
        return userRepository.getUsers();
    }

    public UserDto createUser(UserDto userDto) {
        return toUserDto(userRepository.create(toUser(userDto)));
    }

    public UserDto updateUser(UserDto userDto, Long id) {
        return toUserDto(userRepository.updateUser(userDto));
    }

    public UserDto getUser(Long id) {
        return toUserDto(userRepository.getUser(id));
    }

    public void deleteUser(Long id) {
        userRepository.deleteUser(id);
    }
}
