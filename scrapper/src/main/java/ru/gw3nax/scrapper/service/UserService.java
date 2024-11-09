package ru.gw3nax.scrapper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gw3nax.scrapper.entity.User;
import ru.gw3nax.scrapper.exception.exceptions.UserAlreadyRegisteredException;
import ru.gw3nax.scrapper.exception.exceptions.UserNotRegisteredException;
import ru.gw3nax.scrapper.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void register(Long chatId) {
        if (userRepository.existsById(chatId)) {
            throw new UserAlreadyRegisteredException("Can't register user: user already registered");
        }
        User user = User.builder()
                .chatId(chatId)
                .build();
        userRepository.save(user);
    }

    public void unregister(Long chatId) {
        if (!userRepository.existsById(chatId)) {
            throw new UserNotRegisteredException("Can't delete user: user does not exist");
        }
        userRepository.deleteById(chatId);
    }
}
