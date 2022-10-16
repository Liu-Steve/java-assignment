package edu.whu.Week4.service;

import edu.whu.Week4.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ManageServiceTest {

    @Configuration
    static class ManageServiceConfig {
        @Bean
        public ManageService manageService() {
            return new ManageService();
        }
    }

    @Autowired
    private ManageService service;

    @BeforeEach
    void setUp() {
        service = new ManageService();
    }

    @Test
    public void testAdd_returnIsIdUnique() {
        Product product1 = new Product(1, "Cola", 5, 5.00);
        Product product2 = new Product(1, "Apple", 10, 1.22);
        assertTrue(service.addProduct(product1));
        assertFalse(service.addProduct(product2));
        assertFalse(service.addProduct(null));
    }

    @Test
    public void testDelete_returnIsDeleteSuccess() {
        Product product1 = new Product(1, "Cola", 5, 5.00);
        service.addProduct(product1);
        assertTrue(service.deleteProduct(1));
        assertFalse(service.deleteProduct(1));
    }

    @Test
    public void testGet_returnRealProductOrNullIfNotExist() {
        Product product1 = new Product(1, "Cola", 5, 5.00);
        service.addProduct(product1);
        Product productNew = service.getProduct(1);
        assertEquals(product1, productNew);
        assertNull(service.getProduct(2));
    }

    @Test
    public void testUpdate_returnIsUpdateSuccess() {
        Product product1 = new Product(1, "Cola", 5, 5.00);
        Product product2 = new Product(1, "Apple", 10, 1.22);
        service.addProduct(product1);
        assertTrue(service.updateProduct(1, product2));
        assertEquals(product2, service.getProduct(1));
        assertFalse(service.updateProduct(2, product2));
    }

}