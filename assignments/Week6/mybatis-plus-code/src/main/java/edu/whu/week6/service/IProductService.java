package edu.whu.week6.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import edu.whu.week6.condition.ProductCondition;
import edu.whu.week6.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.whu.week6.exception.ConflictException;
import edu.whu.week6.exception.NotFoundException;
import edu.whu.week6.exception.NotSupportArgumentException;
import edu.whu.week6.exception.SqlExecuteException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author steve-liu
 * @since 2022-10-31
 */
public interface IProductService extends IService<Product> {

    Product addProduct(Product product) throws ConflictException, SqlExecuteException;

    void deleteProduct(String id) throws NotFoundException;

    Product getProduct(String id) throws NotFoundException;

    Product updateProduct(
            String id,
            Product product) throws NotFoundException, SqlExecuteException, ConflictException;

    IPage<Product> selectProduct(
            ProductCondition condition,
            Integer pageNum,
            Integer pageSize) throws NotSupportArgumentException;

}
