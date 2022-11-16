package edu.whu.week8.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import edu.whu.week8.condition.SupplierCondition;
import edu.whu.week8.entity.Supplier;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.whu.week8.exception.ConflictException;
import edu.whu.week8.exception.NotFoundException;
import edu.whu.week8.exception.NotSupportArgumentException;
import edu.whu.week8.exception.SqlExecuteException;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author steve-liu
 * @since 2022-10-31
 */
public interface ISupplierService extends IService<Supplier> {

    Supplier addSupplier(Supplier supplier) throws ConflictException;

    void deleteSupplier(String id) throws NotFoundException;

    Supplier getSupplier(String id) throws NotFoundException;

    Supplier updateSupplier(
            String id,
            Supplier supplier) throws NotFoundException, SqlExecuteException, ConflictException;

    IPage<Supplier> selectSupplier(
            SupplierCondition condition,
            Integer pageNum,
            Integer pageSize) throws NotSupportArgumentException;

}
