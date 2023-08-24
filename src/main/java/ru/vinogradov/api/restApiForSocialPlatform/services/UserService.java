package ru.vinogradov.api.restApiForSocialPlatform.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import ru.vinogradov.api.restApiForSocialPlatform.dto.UserDto;
import ru.vinogradov.api.restApiForSocialPlatform.entities.Role;
import ru.vinogradov.api.restApiForSocialPlatform.entities.User;
import ru.vinogradov.api.restApiForSocialPlatform.repositories.UserRepository;
import ru.vinogradov.api.restApiForSocialPlatform.util.JwtTokenUtil;
import ru.vinogradov.api.restApiForSocialPlatform.util.MessageUtils;
import ru.vinogradov.api.restApiForSocialPlatform.util.ResourceNotFoundException;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userRepository.findByUsername(username);
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
        } catch (Exception e) {
            throw new ResourceNotFoundException(MessageUtils.USER + username + MessageUtils.NOT_FOUND);
        }
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public String saveUser(UserDto userDto) {
        if(isUserDataUniq(userDto)){
            log.error(String.format(MessageUtils.REG_NOT_UNIQUE, userDto.getUsername(), userDto.getEmail()));
            throw new ResourceNotFoundException(String.format(MessageUtils.REG_NOT_UNIQUE, userDto.getUsername(), userDto.getEmail()));
        }
        String salt = BCrypt.gensalt();
        String password = BCrypt.hashpw(userDto.getPassword(), salt);
        List<Role> roleUser = roleService.findAllByName("ROLE_USER");
        userRepository.save(User.builder()
                .username(userDto.getUsername())
                .password(password)
                .email(userDto.getEmail())
                .roles(roleUser)
                .build());
        UserDetails userDetails = loadUserByUsername(userDto.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        log.info(String.format(MessageUtils.USER_NEW, userDto.getUsername(), userDto.getEmail()));
        return token;
    }

    private boolean isUserDataUniq (UserDto userDto) {
        return userRepository.existsByUsernameOrEmail(userDto.getUsername(), userDto.getEmail());
    }
}
