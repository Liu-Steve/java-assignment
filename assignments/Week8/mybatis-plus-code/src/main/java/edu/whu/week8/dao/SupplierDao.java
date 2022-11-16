package edu.whu.week8.dao;

import edu.whu.week8.entity.Supplier;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author steve-liu
 * @since 2022-10-31
 */
@Mapper
public interface SupplierDao extends BaseMapper<Supplier> {

    @Select("SELECT * FROM supplier s" +
            " INNER JOIN product_supply ps ON s.id=ps.supplier_id" +
            " WHERE ps.product_id = #{productId}")
    List<Supplier> findSuppliersByProduct(String productId);

    @Delete("DELETE FROM product_supply WHERE supplier_id = #{supplierId}")
    void deleteProductSupplyBySupplier(String supplierId);
}
