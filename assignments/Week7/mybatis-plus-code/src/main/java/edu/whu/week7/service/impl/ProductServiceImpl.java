package edu.whu.week7.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.whu.week7.condition.ProductCondition;
import edu.whu.week7.dao.SupplierDao;
import edu.whu.week7.entity.Product;
import edu.whu.week7.dao.ProductDao;
import edu.whu.week7.entity.Supplier;
import edu.whu.week7.exception.ConflictException;
import edu.whu.week7.exception.NotFoundException;
import edu.whu.week7.exception.NotSupportArgumentException;
import edu.whu.week7.exception.SqlExecuteException;
import edu.whu.week7.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static edu.whu.week7.util.IdGenerator.generateId;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author steve-liu
 * @since 2022-10-31
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductDao, Product> implements IProductService {

    private final ProductDao dao;
    private final SupplierDao supplierDao;

    @Autowired
    public ProductServiceImpl(ProductDao dao, SupplierDao supplierDao) {
        this.dao = dao;
        this.supplierDao = supplierDao;
    }

    @Override
    @Transactional(rollbackFor = {ConflictException.class, SqlExecuteException.class})
    public Product addProduct(Product product) throws ConflictException, SqlExecuteException {
        // 防止Id重复
        String id = product.getId();
        if (dao.selectById(id) != null) {
            throw new ConflictException(String.format("添加商品失败：商品 %s 已经存在", id));
        }
        // 当Id为Null时生成UUID填入Id字段
        if (id == null) {
            product.setId(generateId());
            id = product.getId();
        }

        dao.insert(product);
        // 级联添加Supply关系
        List<Supplier> suppliers = product.getSuppliers();
        int supplierNum = suppliers.size();
        for (int i = 0; i < supplierNum; i++) {
            Supplier supplier = suppliers.get(i);
            String supplierId = supplier.getId();
            // 检查供应商Id存在性
            Integer count = supplierDao.selectCount(new QueryWrapper<Supplier>()
                    .eq("id", supplierId)
                    .last("limit 1"));
            if (count == 0) {
                throw new SqlExecuteException(String.format(
                        "添加商品供应关系失败：请检查供应商 %s 是否存在。", supplierId));
            }
            // 添加关系
            try {
                dao.insertProductSupply(id, supplierId);
                suppliers.set(i, supplierDao.selectById(supplierId));   // 保证返回的Product的suppliers填入正确的信息
            } catch (SQLIntegrityConstraintViolationException e) {
                throw new SqlExecuteException(String.format(
                        "添加商品供应关系失败：未知错误。供应商ID %s 错误信息 %s", supplierId, e.getMessage()));
            }
        }
        return product;
    }

    @Override
    @Transactional(rollbackFor = {NotFoundException.class})
    public void deleteProduct(String id) throws NotFoundException {
        if (dao.selectById(id) == null) {
            throw new NotFoundException(String.format("删除商品失败：商品 %s 不存在", id));
        }
        dao.deleteProductSupplyByProduct(id);   // 级联删除供应关系
        dao.deleteById(id);
    }

    @Override
    public Product getProduct(String id) throws NotFoundException {
        Product product = dao.selectById(id);
        if (product == null) {
            throw new NotFoundException(String.format("获取商品失败：商品 %s 不存在", id));
        }

        // 填入供应商信息
        List<Supplier> suppliers = supplierDao.findSuppliersByProduct(id);
        product.setSuppliers(suppliers);
        return product;
    }

    @Override
    @Transactional(rollbackFor = {NotFoundException.class, SqlExecuteException.class, ConflictException.class})
    public Product updateProduct(
            String id,
            Product product) throws NotFoundException, SqlExecuteException, ConflictException {
        if (dao.selectById(id) == null) {
            throw new NotFoundException(String.format("修改商品失败：商品 %s 不存在", id));
        }
        deleteProduct(id);
        product.setId(id);
        return addProduct(product);
    }

    @Override
    public IPage<Product> selectProduct(
            ProductCondition condition,
            Integer pageNum,
            Integer pageSize) throws NotSupportArgumentException {
        QueryWrapper<Product> wq = new QueryWrapper<>();
        IPage<Product> page;

        // 排序条件 默认对name字段升序排列
        String order = condition.getOrderBy() == null ? "name" : condition.getOrderBy();
        boolean orderIncrease = condition.getIncrease() == null || condition.getIncrease();

        // 支持的排序关键字
        List<String> supportOrders = new ArrayList<>(Arrays.asList("name", "price", "quantity"));
        if (!supportOrders.contains(order)) {
            throw new NotSupportArgumentException(
                    String.format("筛选商品失败：不支持的排序条件 %s", condition.getOrderBy()));
        }
        order = "p." + order;
        page = new Page<Product>(pageNum, pageSize)
                .addOrder(orderIncrease ? OrderItem.asc(order) : OrderItem.desc(order));

        // 名称包含关键字
        wq.like(condition.getName() != null, "p.name", condition.getName());
        // 价格范围
        wq.ge(condition.getLowestPrice() != null, "p.price", condition.getLowestPrice());
        wq.le(condition.getHighestPrice() != null, "p.price", condition.getHighestPrice());
        // 数量范围
        wq.ge(condition.getLowestQuantity() != null, "p.quantity", condition.getLowestQuantity());
        wq.le(condition.getHighestQuantity() != null, "p.quantity", condition.getHighestQuantity());
        // 供应商关键字
        wq.like(condition.getSupplierName() != null, "s.name", condition.getSupplierName());

        // 筛选并分页
        dao.findProductsBySupplier(page, wq);
        return page;
    }
}
