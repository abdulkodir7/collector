package com.itransition.coursework.user;

import com.itransition.coursework.attachment.AttachmentService;
import com.itransition.coursework.user.role.Role;
import com.itransition.coursework.user.role.RoleEnum;
import com.itransition.coursework.user.role.RoleRepository;
import com.itransition.coursework.util.ThymeleafResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Collections;

import static com.itransition.coursework.util.Constants.*;
import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

/**
 * Abdulqodir Ganiev 6/16/2022 7:05 PM
 */


@Slf4j
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final AttachmentService attachmentService;

    @Lazy
    public UserService(UserRepository userRepository, RoleRepository roleRepository,
                       AuthenticationManager authenticationManager,
                       PasswordEncoder passwordEncoder,
                       AttachmentService attachmentService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.attachmentService = attachmentService;
    }

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

    public Integer getUsersSize(){
        return userRepository.findAll().size();
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
                .password(passwordEncoder.encode(password))
                .role(role)
                .isActive(true)
                .imgUrl(DEFAULT_PROFILE_IMAGE)
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

    public ThymeleafResponse deleteUser(Long id, User currentUser) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceAccessException(USER_NOT_FOUND));
            if (currentUser.getId().equals(user.getId()))

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
                .imgUrl(DEFAULT_PROFILE_IMAGE)
                .isActive(true)
                .joinedAt(LocalDateTime.now())
                .editedAt(LocalDateTime.now())
                .build();

        User savedUser = userRepository.save(newUser);
        log.info("new user {} registered ", savedUser);
        return new ThymeleafResponse(true, SUCCESS_REGISTER);
    }

    public ThymeleafResponse adminLogin(String email, String password, HttpServletRequest request) {
        try {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new ResourceAccessException(USER_NOT_FOUND));

            if (!passwordEncoder.matches(password, user.getPassword()))
                return new ThymeleafResponse(false, INVALID_PASSWORD);

            if (!user.getIsActive())
                return new ThymeleafResponse(false, USER_BLOCKED);

            if (user.getRole().getName().equals(RoleEnum.ROLE_SUPER_ADMIN)
                    || user.getRole().getName().equals(RoleEnum.ROLE_ADMIN)) {
                authenticateUser(email, password, user.getRole(), request);
                return new ThymeleafResponse(true, null);
            }
            return new ThymeleafResponse(false, USER_NOT_FOUND);
        } catch (Exception e) {
            return new ThymeleafResponse(false, e.getMessage());
        }

    }

    public void authenticateUser(String email, String password, Role role, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authReq
                = new UsernamePasswordAuthenticationToken(email, password, Collections.singleton(new SimpleGrantedAuthority(role.getName().name())));
        Authentication auth = authenticationManager.authenticate(authReq);

        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        HttpSession session = request.getSession(true);
        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);
    }

    public ThymeleafResponse editAdminProfile(MultipartFile profileImage,
                                              Long id, String name,
                                              String email) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceAccessException(USER_NOT_FOUND));
            if (userRepository.existsByEmail(email) && !user.getEmail().equals(email))
                return new ThymeleafResponse(false, EMAIL_EXISTS);

            user.setEmail(email);
            user.setName(name);
            if (!profileImage.isEmpty()) {
                String imageUrl = attachmentService.uploadImage(profileImage);
                user.setImgUrl(imageUrl);
            }
            userRepository.save(user);
            return new ThymeleafResponse(true, SUCCESS_CHANGE);
        } catch (Exception e) {
            return new ThymeleafResponse(false, e.getMessage());
        }

    }

    public User getCurrentUser(User currentUser) {
        try {
            return userRepository.findById(currentUser.getId())
                    .orElseThrow(() -> new ResourceAccessException(USER_NOT_FOUND));
        } catch (Exception e) {
            return null;
        }
    }
}
