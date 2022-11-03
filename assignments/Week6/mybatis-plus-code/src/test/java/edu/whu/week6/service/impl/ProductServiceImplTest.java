package edu.whu.week6.service.impl;

import edu.whu.week6.condition.ProductCondition;
import edu.whu.week6.dao.ProductDao;
import edu.whu.week6.dao.SupplierDao;
import edu.whu.week6.entity.Product;
import edu.whu.week6.entity.Supplier;
import edu.whu.week6.exception.ConflictException;
import edu.whu.week6.exception.NotFoundException;
import edu.whu.week6.exception.SqlExecuteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static edu.whu.week6.util.IdGenerator.generateId;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ProductServiceImplTest {

    @Autowired
    ProductServiceImpl service;

    @Autowired
    ProductDao productDao;

    @Autowired
    SupplierDao supplierDao;

    Product product1;
    Product product2;
    Supplier supplier1;
    Supplier supplier2;

    @BeforeEach
    void setUp() {
        product1 = new Product();
        product1.setId(generateId());
        product1.setName("相机");
        product1.setPrice(BigDecimal.valueOf(8.99));
        product1.setQuantity(100);
        product1.setSuppliers(new ArrayList<>());

        product2 = new Product();
        product2.setId(generateId());
        product2.setName("手机");
        product2.setPrice(BigDecimal.valueOf(4899.99));
        product2.setQuantity(500);
        product2.setSuppliers(new ArrayList<>());

        supplier1 = new Supplier();
        supplier1.setId(generateId());
        supplier1.setName("三星");
        supplier1.setEmail("samsung@samsung.com");
        supplier1.setPhone("010-8887777");
        supplier1.setProducts(new ArrayList<>());

        supplier2 = new Supplier();
        supplier2.setId(generateId());
        supplier2.setName("华为");
        supplier2.setEmail("huawei@huawei.com");
        supplier2.setPhone("010-8999556");
        supplier2.setProducts(new ArrayList<>());
    }

    @Test
    void addProduct() {
        // 数据准备
        supplierDao.insert(supplier1);
        product1.getSuppliers().add(supplier1);
        product2.getSuppliers().add(supplier2);

        // 正常插入
        assertDoesNotThrow(() -> {
            Product productReturn = service.addProduct(product1);
            assertEquals(productReturn, product1);
        });

        // 重复插入
        assertThrows(ConflictException.class, () -> {
            service.addProduct(product1);
        });

        // 插入不存在的供应商
        assertThrows(SqlExecuteException.class, () -> {
            service.addProduct(product2);
        });
    }

    @Test
    void deleteProduct() {
        // 数据准备
        productDao.insert(product1);

        // 正常删除
        assertDoesNotThrow(() -> {
            service.deleteProduct(product1.getId());
            assertNull(productDao.selectById(product1.getId()));
        });

        // 删除不存在的
        assertThrows(NotFoundException.class, () -> {
            service.deleteProduct(product2.getId());
        });
    }

    @Test
    void getProduct() {
        // 数据准备
        productDao.insert(product1);

        // 正常获取
        assertDoesNotThrow(() -> {
            Product productReturn = service.getProduct(product1.getId());
            assertEquals(productReturn, product1);
        });

        // 获取不存在的
        assertThrows(NotFoundException.class, () -> {
            service.getProduct(product2.getId());
        });
    }

    @Test
    void updateProduct() {
        // 数据准备
        productDao.insert(product1);
        product1.setPrice(BigDecimal.valueOf(9.99));

        // 正常更新
        assertDoesNotThrow(() -> {
            Product productReturn = service.updateProduct(product1.getId(), product1);
            assertEquals(productReturn, product1);
        });

        // 更新不存在的
        assertThrows(NotFoundException.class, () -> {
            service.updateProduct(product2.getId(), product2);
        });
    }

    @Test
    void selectProduct() {
        // 数据准备
        supplierDao.insert(supplier1);
        supplierDao.insert(supplier2);
        product1.getSuppliers().add(supplier1);
        product2.getSuppliers().add(supplier2);
        assertDoesNotThrow(() -> {
            service.addProduct(product1);
            service.addProduct(product2);
        });
        ProductCondition condition = new ProductCondition();
        condition.setName("机");
        condition.setLowestPrice(BigDecimal.valueOf(1.00));
        condition.setHighestPrice(BigDecimal.valueOf(200.00));
        condition.setLowestQuantity(1L);
        condition.setHighestQuantity(10000L);
        condition.setSupplierName("三星");

        // 筛选
        assertDoesNotThrow(() -> {
            assertEquals(1, service.selectProduct(condition, 0, 10).getTotal());
        });
    }
}