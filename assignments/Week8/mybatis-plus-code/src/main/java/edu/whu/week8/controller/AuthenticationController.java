package edu.whu.week8.controller;

import edu.whu.week8.entity.User;
import edu.whu.week8.exception.AuthenticationException;
import edu.whu.week8.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("authenticate")
public class AuthenticationController {

    private final AuthenticationManager manager;

    private final JwtTokenUtil util;

    private final UserDetailsService service;

    @Autowired
    public AuthenticationController(
            AuthenticationManager manager, JwtTokenUtil util, UserDetailsService service) {
        this.manager = manager;
        this.util = util;
        this.service = service;
    }

    @PostMapping("login")
    public String login(@RequestBody User user) throws AuthenticationException {
        try {
            manager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword()));
            final UserDetails userDetails = service.loadUserByUsername(user.getName());
            return util.generateToken(userDetails);
        } catch (Exception e) {
            throw new AuthenticationException("用户认证未通过");
        }
    }

}
