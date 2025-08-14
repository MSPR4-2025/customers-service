package io.github.mspr4_2025.customers_service.service;

import io.github.mspr4_2025.customers_service.entity.UserEntity;
import io.github.mspr4_2025.customers_service.mapper.UserMapper;
import io.github.mspr4_2025.customers_service.model.user.UserCreateDto;
import io.github.mspr4_2025.customers_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * @throws ResponseStatusException when no entity exist with the given uid.
     *                                 This exception is handled by the controllers, returning a response with the corresponding http status.
     */
    public UserEntity getUserByUid(UUID uid) throws ResponseStatusException {
        Optional<UserEntity> entity = userRepository.findByUid(uid);

        if (entity.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return entity.get();
    }

    @Override
    public UserEntity loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> entity = userRepository.findByUsername(username);

        if (entity.isEmpty()) {
            throw new UsernameNotFoundException("Username not found");
        }

        return entity.get();
    }

    public UserEntity createUser(UserCreateDto userCreateDto) {
        UserEntity entity = userMapper.fromCreateDto(userCreateDto);

        return userRepository.save(entity);
    }

    public void deleteUser(UUID uid) {
        UserEntity entity = this.getUserByUid(uid);

        userRepository.delete(entity);
    }
}
