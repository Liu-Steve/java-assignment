package edu.whu.week7.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import edu.whu.week7.entity.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author steve-liu
 * @since 2022-10-31
 */
@Mapper
public interface ProductDao extends BaseMapper<Product> {

    @Insert("INSERT INTO product_supply (product_id, supplier_id) VALUES (#{productId}, #{supplierId})")
    void insertProductSupply(String productId, String supplierId) throws SQLIntegrityConstraintViolationException;

    @Delete("DELETE FROM product_supply WHERE product_id = #{productId}")
    void deleteProductSupplyByProduct(String productId);

    @Select("SELECT DISTINCT p.* FROM product p" +
            " LEFT JOIN product_supply ps ON p.id=ps.product_id" +
            " LEFT JOIN supplier s ON ps.supplier_id=s.id" +
            " ${ew.customSqlSegment}")
    @Results({@Result(id = true, property = "id", column = "id"),
            @Result(property = "suppliers", column = "id",
                    many = @Many(select = "edu.whu.week7.dao.SupplierDao.findSuppliersByProduct"))})
    // 返回值类型 IPage<Product> 绝对不能删
    // 否则会导致 Mybatis Plus 找不到 Product 对应的构造函数
    IPage<Product> findProductsBySupplier(
            IPage<Product> page, @Param(Constants.WRAPPER) QueryWrapper<Product> wrapper);

}
