package edu.whu.week8.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.whu.week8.entity.Supplier;
import edu.whu.week8.entity.User;
import edu.whu.week8.exception.ConflictException;
import edu.whu.week8.exception.NotFoundException;
import edu.whu.week8.exception.SqlExecuteException;

import java.util.List;

public interface IUserService extends IService<User> {

    User addUser(User user) throws ConflictException;

    void deleteUser(String id) throws NotFoundException;

    User getUser(String id) throws NotFoundException;

    User updateUser(
            String id,
            User user) throws NotFoundException, SqlExecuteException, ConflictException;

    List<User> findAllUser();

}
