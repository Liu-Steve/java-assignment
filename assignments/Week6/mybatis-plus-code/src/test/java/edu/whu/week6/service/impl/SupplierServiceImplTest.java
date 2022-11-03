package edu.whu.week6.service.impl;

import edu.whu.week6.dao.SupplierDao;
import edu.whu.week6.entity.Supplier;
import edu.whu.week6.exception.ConflictException;
import edu.whu.week6.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static edu.whu.week6.util.IdGenerator.generateId;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class SupplierServiceImplTest {

    @Autowired
    SupplierServiceImpl service;

    @Autowired
    SupplierDao supplierDao;

    Supplier supplier1;
    Supplier supplier2;

    @BeforeEach
    void setUp() {
        supplier1 = new Supplier();
        supplier1.setId(generateId());
        supplier1.setName("三星");
        supplier1.setEmail("samsung@samsung.com");
        supplier1.setPhone("010-8887777");

        supplier2 = new Supplier();
        supplier2.setId(generateId());
        supplier2.setName("华为");
        supplier2.setEmail("huawei@huawei.com");
        supplier2.setPhone("010-8999556");
    }

    @Test
    void addSupplier() {
        // 正常插入
        assertDoesNotThrow(() -> {
            Supplier supplierReturn = service.addSupplier(supplier1);
            assertEquals(supplierReturn, supplier1);
        });

        // 重复插入
        assertThrows(ConflictException.class, () -> {
            service.addSupplier(supplier1);
        });
    }

    @Test
    void deleteSupplier() {
        // 数据准备
        supplierDao.insert(supplier1);

        // 正常删除
        assertDoesNotThrow(() -> {
            service.deleteSupplier(supplier1.getId());
            assertNull(supplierDao.selectById(supplier1.getId()));
        });

        // 删除不存在的
        assertThrows(NotFoundException.class, () -> {
            service.deleteSupplier(supplier2.getId());
        });
    }

    @Test
    void getSupplier() {
        // 数据准备
        supplierDao.insert(supplier1);

        // 正常获取
        assertDoesNotThrow(() -> {
            Supplier supplierReturn = service.getSupplier(supplier1.getId());
            assertEquals(supplierReturn, supplier1);
        });

        // 获取不存在的
        assertThrows(NotFoundException.class, () -> {
            service.getSupplier(supplier2.getId());
        });
    }

    @Test
    void updateSupplier() {
        // 数据准备
        supplierDao.insert(supplier1);
        supplier1.setName("三星星");

        // 正常更新
        assertDoesNotThrow(() -> {
            Supplier supplierReturn = service.updateSupplier(supplier1.getId(), supplier1);
            assertEquals(supplierReturn, supplier1);
        });

        // 更新不存在的
        assertThrows(NotFoundException.class, () -> {
            service.updateSupplier(supplier2.getId(), supplier2);
        });
    }
}