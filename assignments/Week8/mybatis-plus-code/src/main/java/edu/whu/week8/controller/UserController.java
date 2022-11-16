package edu.whu.week8.controller;

import edu.whu.week8.entity.User;
import edu.whu.week8.exception.ConflictException;
import edu.whu.week8.exception.NotFoundException;
import edu.whu.week8.exception.SqlExecuteException;
import edu.whu.week8.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    private final IUserService service;

    @Autowired
    public UserController(IUserService service) {
        this.service = service;
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('user/query')")
    public List<User> findAllUser() {
        return service.findAllUser();
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('user/update')")
    public User addUser(@RequestBody User user) throws ConflictException {
        return service.addUser(user);
    }

    @DeleteMapping("/{name}")
    @PreAuthorize("hasAuthority('user/update')")
    public void deleteUser(@PathVariable String name) throws NotFoundException {
        service.deleteUser(name);
    }

    @GetMapping("/{name}")
    @PreAuthorize("hasAuthority('user/query') or #name == authentication.name")
    public User getUser(@PathVariable String name) throws NotFoundException {
        return service.getUser(name);
    }

    @PutMapping("/{name}")
    @PreAuthorize("hasAuthority('user/update') or #name == authentication.name")
    public User updateUser(@PathVariable String name, @RequestBody User user)
            throws ConflictException, SqlExecuteException, NotFoundException {
        return service.updateUser(name, user);
    }

}
