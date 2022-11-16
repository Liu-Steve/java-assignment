package edu.whu.week8.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.persistence.Convert;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@TableName("role")
public class Role {

    @TableId(value = "name", type = IdType.INPUT)
    private String name;

//    @Convert(converter = StringListConverter.class)
//    private List<String> authorities = new ArrayList<>();
    private String authorities;

}
