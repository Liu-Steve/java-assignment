package edu.whu.Week4.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.whu.Week4.entity.Product;
import edu.whu.Week4.service.ManageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ManageControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ManageService service;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        //remove all products in service
        List<Product> products = service.getProducts();
        for (Product product : products) {
            service.deleteProduct(product.getId());
        }
    }

    @Test
    public void testGets_whenServiceIsEmpty_thenReturnEmptyJson() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/manage")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void testGets_whenServiceHasProducts_thenReturnProductArray() throws Exception {
        service.addProduct(new Product(1, "Cola", 5, 5.00));
        service.addProduct(new Product(2, "Apple", 10, 1.22));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/manage")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.id == 1)].name").value("Cola"))
                .andExpect(jsonPath("$[?(@.id == 1)].count").value(5))
                .andExpect(jsonPath("$[?(@.id == 1)].price").value(5.00))
                .andExpect(jsonPath("$[?(@.id == 2)].name").value("Apple"))
                .andExpect(jsonPath("$[?(@.id == 2)].count").value(10))
                .andExpect(jsonPath("$[?(@.id == 2)].price").value(1.22));
    }

    @Test
    public void testGet_whenIdIsExist_thenReturnOk() throws Exception {
        service.addProduct(new Product(1, "Cola", 5, 5.00));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/manage/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Cola"))
                .andExpect(jsonPath("$.count").value(5))
                .andExpect(jsonPath("$.price").value(5.00));
    }

    @Test
    public void testGet_whenIdNotExist_thenReturnNotFount() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/manage/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAdd_whenSameIdProductNotExist_thenReturnOkAndProduct() throws Exception {
        Product product = new Product(1, "Cola", 5, 5.00);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/manage")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(product)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Cola"))
                .andExpect(jsonPath("$.count").value(5))
                .andExpect(jsonPath("$.price").value(5.00));

        Product productNew = service.getProduct(1);
        assertEquals(1, productNew.getId());
        assertEquals("Cola", productNew.getName());
        assertEquals(5, productNew.getCount());
        assertEquals(5.00, productNew.getPrice());
    }

    @Test
    public void testAdd_whenSameIdProductExist_thenReturnConflictAndExistProduct() throws Exception {
        Product product1 = new Product(1, "Cola", 5, 5.00);
        Product product2 = new Product(1, "Apple", 10, 1.22);

        service.addProduct(product1);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/manage")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(product2)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Cola"))
                .andExpect(jsonPath("$.count").value(5))
                .andExpect(jsonPath("$.price").value(5.00));

        Product productNew = service.getProduct(1);
        assertEquals(1, productNew.getId());
        assertEquals("Cola", productNew.getName());
        assertEquals(5, productNew.getCount());
        assertEquals(5.00, productNew.getPrice());
    }

    @Test
    public void testUpdate_whenIdExist_thenReturnOk() throws Exception {
        Product product1 = new Product(1, "Cola", 5, 5.00);
        Product product2 = new Product(1, "Apple", 10, 1.22);

        service.addProduct(product1);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/manage/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(product2)))
                .andDo(print())
                .andExpect(status().isOk());

        Product productNew = service.getProduct(1);
        assertEquals(1, productNew.getId());
        assertEquals("Apple", productNew.getName());
        assertEquals(10, productNew.getCount());
        assertEquals(1.22, productNew.getPrice());
    }

    @Test
    public void testUpdate_whenIdNotExist_thenReturnNotFound() throws Exception {
        Product product1 = new Product(1, "Cola", 5, 5.00);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/manage/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(product1)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDelete_whenIdExist_thenReturnNoContent() throws Exception {
        Product product1 = new Product(1, "Cola", 5, 5.00);

        service.addProduct(product1);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/manage/{id}", 1))
                .andDo(print())
                .andExpect(status().isNoContent());

        assertNull(service.getProduct(product1.getId()));
    }

    @Test
    public void testDelete_whenIdNotExist_thenReturnNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/manage/{id}", 1))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}