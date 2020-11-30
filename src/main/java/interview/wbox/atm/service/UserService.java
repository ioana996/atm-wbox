package interview.wbox.atm.service;

import interview.wbox.atm.dto.UserDto;
import interview.wbox.atm.model.Role;
import interview.wbox.atm.model.RoleType;
import interview.wbox.atm.model.User;
import interview.wbox.atm.repository.RoleRepository;
import interview.wbox.atm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by ioana on 11/29/2020.
 */
@Transactional
@Service(value = "userService")
public class UserService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        Set<GrantedAuthority> grantedAuthorities = getAuthorities(user);

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), grantedAuthorities);
    }

    public Set<GrantedAuthority> getAuthorities(User user) {
        Set<Role> userRoles = user.getRoles();
        final Set<GrantedAuthority> authorities = userRoles.stream().map(role ->
                new SimpleGrantedAuthority("ROLE_" +
                        role.getName().toString().toUpperCase())).collect(Collectors.toSet());
        return authorities;
    }

    public User save(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setUsername(userDto.getUsername());
        user.setTelephone(userDto.getTelephone());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Set<RoleType> roleTypes = new HashSet<>();
        userDto.getRole().stream().map(role -> roleTypes.add(RoleType.valueOf(role)));
        for(int j = 0; j < userDto.getRole().size(); j++) {
            roleTypes.add(RoleType.valueOf(userDto.getRole().get(0)));
        }
        user.setRoles(roleRepository.find(roleTypes));
        userRepository.save(user);
        return user;
    }
}
