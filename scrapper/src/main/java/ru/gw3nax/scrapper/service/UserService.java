package ru.gw3nax.scrapper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gw3nax.scrapper.entity.UserEntity;
import ru.gw3nax.scrapper.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public String getClientTopicName(String userId){
        return userRepository.getClientNameByUserId(userId);
    }

    public UserEntity registerUser(String userId, String header) {
        if (userRepository.findByUserId(userId).isPresent()) return userRepository.findByUserId(userId).get();
        var userEntity = UserEntity.builder()
                .clientName(header)
                .userId(userId)
                .build();
        return userRepository.save(userEntity);
    }

    public Long findUserByUserId(String userId) {
        var optional = userRepository.findByUserId(userId);
        if (optional.isPresent()) {
            return optional.get().getId();
        }
        return null;
    }
}
