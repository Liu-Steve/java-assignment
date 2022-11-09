package edu.whu.week7.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import edu.whu.week7.condition.SupplierCondition;
import edu.whu.week7.entity.Supplier;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.whu.week7.exception.ConflictException;
import edu.whu.week7.exception.NotFoundException;
import edu.whu.week7.exception.NotSupportArgumentException;
import edu.whu.week7.exception.SqlExecuteException;

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
