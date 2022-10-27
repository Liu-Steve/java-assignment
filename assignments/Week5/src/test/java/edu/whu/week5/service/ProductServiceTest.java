package edu.whu.week5.service;

import edu.whu.week5.dao.ProductRepository;
import edu.whu.week5.dao.SupplierRepository;
import edu.whu.week5.entity.Product;
import edu.whu.week5.entity.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ProductServiceTest {

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    SupplierRepository supplierRepository;

    @BeforeEach
    void setUp() {
        Supplier supplier1 = new Supplier();
        supplier1.setName("公司A");
        supplier1.setPhone("16677778888");
        supplierRepository.saveAndFlush(supplier1);

        Supplier supplier2 = new Supplier();
        supplier2.setName("公司B");
        supplier2.setPhone("12233334444");
        supplierRepository.saveAndFlush(supplier2);

        Product product1 = new Product();
        product1.setName("薯片");
        product1.setPrice(BigDecimal.valueOf(3.50));
        product1.setQuantity(100);
        product1.getSuppliers().add(supplier1);
        productRepository.saveAndFlush(product1);

        Product product2 = new Product();
        product2.setName("可乐");
        product2.setPrice(BigDecimal.valueOf(2.50));
        product2.setQuantity(200);
        product2.getSuppliers().add(supplier1);
        product2.getSuppliers().add(supplier2);
        productRepository.saveAndFlush(product2);

        Product product3 = new Product();
        product3.setName("雪碧");
        product3.setPrice(BigDecimal.valueOf(3.00));
        product3.setQuantity(150);
        product3.getSuppliers().add(supplier2);
        productRepository.saveAndFlush(product3);
    }

    @Test
    void addProduct() {
    }

    @Test
    void deleteProduct() {
    }

    @Test
    void getProduct() {
    }

    @Test
    void updateProduct() {
    }

    @Test
    void selectProduct() {
    }
}