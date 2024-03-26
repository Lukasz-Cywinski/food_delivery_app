package pl.project.infrastructure.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.project.infrastructure.security.db.RoleEntity;
import pl.project.infrastructure.security.db.RoleRepository;
import pl.project.infrastructure.security.db.UserEntity;
import pl.project.infrastructure.security.db.UserRepository;
import pl.project.infrastructure.security.db.mapper.UserEntityMapper;
import pl.project.infrastructure.security.exception.RoleNotFoundException;
import pl.project.infrastructure.security.exception.UserCreationException;
import pl.project.infrastructure.security.exception.UserNotFoundException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectUserDetailsService implements UserDetailsService {

    private final String ROLE_NOT_FOUND_EXCEPTION = "Cannot found role: %s";
    private final String ROLE_NOT_FOUND_EXCEPTION_ANY = "Cannot find any role";
    private final String USER_NOT_FOUND_EXCEPTION = "Cannot find user: %s";
    private final String USER_CREATION_EXCEPTION = "Cannot create user: %s";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserEntityMapper userEntityMapper;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUserName(username)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_EXCEPTION.formatted(username)));
        List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
        return buildUserForAuthentication(user, authorities);
    }

    @Transactional
    public pl.project.infrastructure.security.User saveUserAndAssignRoles(pl.project.infrastructure.security.User user) {
        Set<RoleEntity> roles = user.getRoles().stream()
                .map(role -> roleRepository.findByRole(role.name())
                        .orElseThrow(() -> new RoleNotFoundException(ROLE_NOT_FOUND_EXCEPTION.formatted(role.name()))))
                .collect(Collectors.toSet());
        if (roles.isEmpty()) throw new RoleNotFoundException(ROLE_NOT_FOUND_EXCEPTION_ANY);
        UserEntity userEntity = userEntityMapper.mapToEntity(user);
        userEntity.setRoles(roles);

        try {
            return userEntityMapper.mapFromEntity(
                    userRepository.save(userEntity)
            );
        } catch (Exception e) {
            throw new UserCreationException(USER_CREATION_EXCEPTION.formatted(user.getUserName()));
        }
    }

    @Transactional
    public pl.project.infrastructure.security.User getUserAndRoleByUserName(String username) {
        return userEntityMapper.mapFromEntity(userRepository.findByUserName(username)
                        .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_EXCEPTION.formatted(username))))
                .withPassword("");
    }

    @Transactional
    public pl.project.infrastructure.security.User getUserAndRoleByEmail(String email) {
        return userEntityMapper.mapFromEntity(userRepository.findByEmail(email)
                        .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_EXCEPTION.formatted(email))))
                .withPassword(null);
    }

    @Transactional
    public String getUserEmail(String userName) {
        return userRepository.findEmailByUserName(userName)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_EXCEPTION.formatted(userName)));
    }

//    @Transactional
    public Integer changeEmail(String newEmail, String oldEmail){
        return userRepository.changeEmail(newEmail, oldEmail);
    }

    private List<GrantedAuthority> getUserAuthority(Set<RoleEntity> userRoles) {
        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .distinct()
                .collect(Collectors.toList());
    }

    private UserDetails buildUserForAuthentication(UserEntity user, List<GrantedAuthority> authorities) {
        return new User(
                user.getUserName(),
                user.getPassword(),
                user.getActive(),
                true,
                true,
                true,
                authorities
        );
    }
}
