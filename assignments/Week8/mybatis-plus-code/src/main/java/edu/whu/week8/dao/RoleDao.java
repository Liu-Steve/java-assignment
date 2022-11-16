package edu.whu.week8.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.whu.week8.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleDao extends BaseMapper<Role> {

    @Select("SELECT * FROM role r" +
            " INNER JOIN user_role ur ON r.name=ur.role_name" +
            " WHERE ur.user_name = #{userName}")
    List<Role> findRolesByUser(String userName);

}
