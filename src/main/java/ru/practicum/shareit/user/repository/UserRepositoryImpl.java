package ru.practicum.shareit.user.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.DuplicateEmailException;
import ru.practicum.shareit.exception.DuplicateException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.*;

@Slf4j
@Repository
public class UserRepositoryImpl {

    private final Map<Long, User> userRepository = new HashMap<>();

    private long id = 0L;

    public User create(User user) {
        validate(user);
        if (userRepository.containsKey(user.getId())) {
            throw new DuplicateException(String.format("Пользователь с id: %d уже существует", user.getId()));
        } else {
            user.setId(++id);
            userRepository.put(id, user);
        }
        log.info("Пользователь создан: {}", user);
        return user;
    }

    public User updateUser(UserDto userDto) {
        if (!userRepository.containsKey(id)) {
            throw new NotFoundException(String.format("Пользователь с id: %d не существует", id));
        }

        User user = userRepository.get(id);
        if (userDto.getName() != null) {
            user.setName(userDto.getName());
        }
        if (userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }

        validate(user);
        userRepository.put(user.getId(), user);
        log.info("Пользователь обновлен: {}", user);
        return user;
    }

    public void deleteUser(Long id) {
        userRepository.remove(id);
    }

    public User getUser(Long id) {
        User user = userRepository.get(id);
        if (user == null) {
            throw new NotFoundException(String.format("Пользователь с id: %d не существует", id));
        }
        return user;
    }

    public List<User> getUsers() {
        return new ArrayList<>(userRepository.values());
    }

    private void validate(User user) throws DuplicateEmailException {
        if (userRepository.values().stream()
                .anyMatch(u -> u.getEmail().equals(user.getEmail()) && !Objects.equals(u.getId(), user.getId()))) {
            throw new DuplicateEmailException("Этот email уже используется");
        }
    }

}
