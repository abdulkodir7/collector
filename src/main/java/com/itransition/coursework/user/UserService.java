package com.itransition.coursework.user;

import com.itransition.coursework.user.role.Role;
import com.itransition.coursework.user.role.RoleEnum;
import com.itransition.coursework.user.role.RoleRepository;
import com.itransition.coursework.util.ThymeleafResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.time.LocalDateTime;

import static com.itransition.coursework.util.Constants.*;

/**
 * Abdulqodir Ganiev 6/16/2022 7:05 PM
 */


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
            log.info("trying to log {}", user);
            return user;

        } catch (Exception e) {
            log.info("logging failed {}", e.getMessage());
            return null;
        }
    }


    public Page<User> getAllUsers(int size, int page) {
        Pageable pageable = PageRequest.of(
                page - 1,
                size
        );

        return userRepository.findAll(pageable);
    }

    public ThymeleafResponse saveUser(Long id, String name, String email,
                                      String password, String confirmPassword, Long role) {

        if (!password.equals(confirmPassword))
            return new ThymeleafResponse(false, PASSWORD_NOT_MATCH);

        try {
            Role selectedRole = roleRepository.findById(role)
                    .orElseThrow(() -> new ResourceAccessException(ROLE_NOT_FOUND));

            if (id != null)
                return editUser(id, name, email, password, selectedRole);
            else
                return addNewUser(name, email, password, selectedRole);
        } catch (Exception e) {
            return new ThymeleafResponse(false, e.getMessage());
        }
    }

    private ThymeleafResponse addNewUser(String name, String email, String password, Role role) {
        if (userRepository.existsByEmail(email))
            return new ThymeleafResponse(false, EMAIL_EXISTS);

        User newUser = User.builder()
                .name(name)
                .email(email)
                .password(password)
                .role(role)
                .isActive(true)
                .joinedAt(LocalDateTime.now())
                .editedAt(LocalDateTime.now())
                .build();
        userRepository.save(newUser);
        log.info("new user saved {}", newUser);
        return new ThymeleafResponse(true, SUCCESS_MESSAGE);
    }

    private ThymeleafResponse editUser(Long id, String name, String email, String password, Role role) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceAccessException(USER_NOT_FOUND));
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);
            user.setRole(role);
            user.setEditedAt(LocalDateTime.now());
            User savedUser = userRepository.save(user);
            log.info("user edited {}", savedUser);
            return new ThymeleafResponse(true, SUCCESS_MESSAGE);

        } catch (Exception e) {
            log.info("throw exception {}", e.getMessage());
            return new ThymeleafResponse(false, e.getMessage());
        }
    }

    public ThymeleafResponse deleteUser(Long id) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceAccessException(USER_NOT_FOUND));
            userRepository.delete(user);
            return new ThymeleafResponse(true, SUCCESS_DELETE);
        } catch (Exception e) {
            return new ThymeleafResponse(false, e.getMessage());
        }

    }

    public ThymeleafResponse blockUser(Long id) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceAccessException(USER_NOT_FOUND));
            user.setIsActive(false);
            userRepository.save(user);
            return new ThymeleafResponse(true, SUCCESS_CHANGE);
        } catch (Exception e) {
            return new ThymeleafResponse(false, e.getMessage());
        }
    }

    public ThymeleafResponse unblockUser(Long id) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceAccessException(USER_NOT_FOUND));
            user.setIsActive(true);
            userRepository.save(user);
            return new ThymeleafResponse(true, SUCCESS_CHANGE);
        } catch (Exception e) {
            return new ThymeleafResponse(false, e.getMessage());
        }
    }

    public ThymeleafResponse registerUser(UserRegistrationDto dto) {
        if (!dto.getPassword().equals(dto.getConfirmPassword()))
            return new ThymeleafResponse(false, PASSWORD_NOT_MATCH);

        if (userRepository.existsByEmail(dto.getEmail()))
            return new ThymeleafResponse(false, EMAIL_EXISTS);

        Role roleCreator = roleRepository.getByName(RoleEnum.ROLE_CREATOR);
        User newUser = User.builder()
                .name(dto.getFullName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .role(roleCreator)
                .isActive(true)
                .joinedAt(LocalDateTime.now())
                .editedAt(LocalDateTime.now())
                .build();

        User savedUser = userRepository.save(newUser);
        log.info("new user {} registered ", savedUser);
        return new ThymeleafResponse(true, SUCCESS_REGISTER);
    }
}
