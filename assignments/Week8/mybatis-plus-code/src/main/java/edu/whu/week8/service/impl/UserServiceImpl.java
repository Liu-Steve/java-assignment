package edu.whu.week8.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.whu.week8.dao.RoleDao;
import edu.whu.week8.dao.UserDao;
import edu.whu.week8.entity.User;
import edu.whu.week8.exception.ConflictException;
import edu.whu.week8.exception.NotFoundException;
import edu.whu.week8.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements IUserService {
    private final UserDao dao;
    private final RoleDao roleDao;

    @Autowired
    public UserServiceImpl(UserDao dao, RoleDao roleDao) {
        this.dao = dao;
        this.roleDao = roleDao;
    }

    @Override
    public User addUser(User user) throws ConflictException {
        String name = user.getName();
        if (name == null) {
            throw new ConflictException("添加用户失败：用户名为空");
        }
        // 防止Name重复
        if (dao.selectById(name) != null) {
            throw new ConflictException(String.format("添加用户失败：用户 %s 已经存在", name));
        }
        dao.insert(user);
        return user;
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "user", key = "#name")
    public void deleteUser(String name) throws NotFoundException {
        if (dao.selectById(name) == null) {
            throw new NotFoundException(String.format("删除用户失败：用户 %s 不存在", name));
        }
        dao.deleteById(name);
    }

    @Override
    @Cacheable(cacheNames = "user",key = "#name",condition = "#name!=null")
    public User getUser(String name) throws NotFoundException {
        User user = dao.selectById(name);
        if (user == null) {
            throw new NotFoundException(String.format("获取用户失败：用户 %s 不存在", name));
        }
        user.setUserRoles(roleDao.findRolesByUser(user.getName()));
        return user;
    }

    @Override
    @CacheEvict(cacheNames = "user", key = "#name")
    public User updateUser(
            String name,
            User user) throws NotFoundException, ConflictException {
        if (dao.selectById(name) == null) {
            throw new NotFoundException(String.format("修改用户失败：用户 %s 不存在", name));
        }
        deleteUser(name);
        user.setName(name);
        return addUser(user);
    }

    @Override
    public List<User> findAllUser() {
        return dao.selectByMap(null);
    }
}
