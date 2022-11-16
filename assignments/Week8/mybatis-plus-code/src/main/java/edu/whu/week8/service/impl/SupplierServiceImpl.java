package edu.whu.week8.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.whu.week8.condition.SupplierCondition;
import edu.whu.week8.entity.Supplier;
import edu.whu.week8.dao.SupplierDao;
import edu.whu.week8.exception.ConflictException;
import edu.whu.week8.exception.NotFoundException;
import edu.whu.week8.exception.NotSupportArgumentException;
import edu.whu.week8.service.ISupplierService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static edu.whu.week8.util.IdGenerator.generateId;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author steve-liu
 * @since 2022-10-31
 */
@Service
public class SupplierServiceImpl extends ServiceImpl<SupplierDao, Supplier> implements ISupplierService {

    private final SupplierDao dao;

    @Autowired
    public SupplierServiceImpl(SupplierDao dao) {
        this.dao = dao;
    }

    @Override
    public Supplier addSupplier(Supplier supplier) throws ConflictException {
        // 防止Id重复
        String id = supplier.getId();
        if (dao.selectById(id) != null) {
            throw new ConflictException(String.format("添加供应商失败：供应商 %s 已经存在", id));
        }
        // 当Id为Null时生成UUID填入Id字段
        if (id == null) {
            supplier.setId(generateId());
        }

        dao.insert(supplier);
        return supplier;
    }

    @Override
    @Transactional
    public void deleteSupplier(String id) throws NotFoundException {
        if (dao.selectById(id) == null) {
            throw new NotFoundException(String.format("删除供应商失败：供应商 %s 不存在", id));
        }
        dao.deleteProductSupplyBySupplier(id);  // 级联删除供应关系
        dao.deleteById(id);
    }

    @Override
    public Supplier getSupplier(String id) throws NotFoundException {
        Supplier supplier = dao.selectById(id);
        if (supplier == null) {
            throw new NotFoundException(String.format("获取供应商失败：供应商 %s 不存在", id));
        }
        return supplier;
    }

    @Override
    public Supplier updateSupplier(
            String id,
            Supplier supplier) throws NotFoundException, ConflictException {
        if (dao.selectById(id) == null) {
            throw new NotFoundException(String.format("修改供应商失败：供应商 %s 不存在", id));
        }
        deleteSupplier(id);
        supplier.setId(id);
        return addSupplier(supplier);
    }

    @Override
    public IPage<Supplier> selectSupplier(
            SupplierCondition condition,
            Integer pageNum,
            Integer pageSize) throws NotSupportArgumentException {
        QueryWrapper<Supplier> wq = new QueryWrapper<>();
        IPage<Supplier> page;

        // 排序条件 默认对name字段升序排列
        String order = condition.getOrderBy() == null ? "name" : condition.getOrderBy();
        boolean orderIncrease = condition.getIncrease() == null || condition.getIncrease();

        // 支持的排序关键字
        List<String> supportOrders = new ArrayList<>(Arrays.asList("name", "email", "phone"));
        if (!supportOrders.contains(order)) {
            throw new NotSupportArgumentException(
                    String.format("筛选商品失败：不支持的排序条件 %s", condition.getOrderBy()));
        }
        page = new Page<Supplier>(pageNum, pageSize)
                .addOrder(orderIncrease ? OrderItem.asc(order) : OrderItem.desc(order));

        // 名称包括关键字
        wq.like(condition.getName() != null, "name", condition.getName());
        // 邮箱匹配
        wq.like(condition.getEmail() != null, "email", condition.getEmail());
        // 电话匹配
        wq.like(condition.getPhone() != null, "phone", condition.getPhone());

        dao.selectPage(page, wq);
        return page;
    }
}
