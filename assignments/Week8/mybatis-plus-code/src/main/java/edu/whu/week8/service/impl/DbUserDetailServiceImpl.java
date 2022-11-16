package edu.whu.week8.service.impl;

import edu.whu.week8.entity.Role;
import edu.whu.week8.exception.NotFoundException;
import edu.whu.week8.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DbUserDetailServiceImpl implements UserDetailsService {

    private final IUserService service;

    @Autowired
    public DbUserDetailServiceImpl(IUserService userService) {
        service = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        edu.whu.week8.entity.User user;
        try {
            user = service.getUser(username);
        } catch (NotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : user.getUserRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getAuthorities()));
        }
        return User.builder()
                .username(username)
                .password(new BCryptPasswordEncoder().encode(user.getPassword()))
                .authorities(authorities.toArray(new GrantedAuthority[authorities.size()]))
                .build();
    }
}
