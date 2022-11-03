package edu.whu.week5.service;

import edu.whu.week5.dao.ProductRepository;
import edu.whu.week5.dao.SupplierRepository;
import edu.whu.week5.entity.Product;
import edu.whu.week5.entity.Supplier;
import edu.whu.week5.exception.ConflictException;
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

    Supplier supplier1;
    Supplier supplier2;
    Product product1;
    Product product2;
    Product product3;
    Product product4;

    @BeforeEach
    void setUp() {
        supplier1 = new Supplier();
        supplier1.setName("公司A");
        supplier1.setPhone("16677778888");
        supplierRepository.save(supplier1);

        supplier2 = new Supplier();
        supplier2.setName("公司B");
        supplier2.setPhone("12233334444");
        supplierRepository.save(supplier2);

        product1 = new Product();
        product1.setName("薯片");
        product1.setPrice(BigDecimal.valueOf(3.50));
        product1.setQuantity(100);
        product1.getSuppliers().add(supplier1);
        productRepository.saveAndFlush(product1);

        product2 = new Product();
        product2.setName("可乐");
        product2.setPrice(BigDecimal.valueOf(2.50));
        product2.setQuantity(200);
        product2.getSuppliers().add(supplier1);
        product2.getSuppliers().add(supplier2);
        productRepository.saveAndFlush(product2);

        product3 = new Product();
        product3.setName("雪碧");
        product3.setPrice(BigDecimal.valueOf(3.00));
        product3.setQuantity(150);
        product3.getSuppliers().add(supplier2);
        productRepository.saveAndFlush(product3);

        product4 = new Product();
        product4.setName("薯条");
        product4.setPrice(BigDecimal.valueOf(5.50));
        product4.setQuantity(350);
        product4.getSuppliers().add(supplier2);
        productRepository.saveAndFlush(product4);
    }

    @Test
    void addProduct() {
        Product product5 = new Product();
        product5.setName("炸鸡");
        product5.setPrice(BigDecimal.valueOf(12.00));
        product5.setQuantity(50);
        product5.getSuppliers().add(supplier1);

        assertDoesNotThrow(() -> {
            productService.addProduct(product5);
        });
        assertThrows(ConflictException.class, () -> {
            productService.addProduct(product5);
        });
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