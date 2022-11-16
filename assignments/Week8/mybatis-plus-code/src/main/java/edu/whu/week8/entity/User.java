package edu.whu.week8.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName("user")
public class User {

    @TableId(value = "name", type = IdType.INPUT)
    private String name;

    private String password;

    @TableField(exist = false)
    private List<Role> userRoles;

}
