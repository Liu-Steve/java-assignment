package edu.whu.week8.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author steve-liu
 * @since 2022-10-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("product")
public class Product implements Serializable {

    public Product() {}

    private static final long serialVersionUID = 1L;

//    public Product(String id, String name, BigDecimal price, Long quantity) {
//        this.id = id;
//        this.name = name;
//        this.price = price;
//        this.quantity = quantity != null ? quantity : 0;
//    }

    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    private String name;

    private BigDecimal price;

    private long quantity;

    @TableField(exist = false)
    private List<Supplier> suppliers;

}
