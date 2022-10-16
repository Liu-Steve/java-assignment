package edu.whu.Week4.service;

import edu.whu.Week4.entity.Product;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ManageService {

    private final Map<Long, Product> products = new Hashtable<>();

    /**
     * add new product if no product with same id exist
     *
     * @param product new product
     * @return whether add is successful
     */
    public boolean addProduct(Product product) {
        if (product == null) {
            return false;
        }
        long id = product.getId();
        // the product has existed
        if (products.get(id) != null) {
            return false;
        }
        products.put(product.getId(), product);
        return true;
    }

    /**
     * delete an existed product
     *
     * @param id product id
     * @return whether delete is successful
     */
    public boolean deleteProduct(long id) {
        Product product = products.remove(id);
        return product != null;
    }

    /**
     * get a product
     *
     * @param id product id
     * @return Product if the product exist, else return null
     */
    public Product getProduct(long id) {
        return products.get(id);
    }

    /**
     * get all products in Arraylist type
     *
     * @return products
     */
    public ArrayList<Product> getProducts() {
        return new ArrayList<>(products.values());
    }

    /**
     * reform the product with id
     *
     * @param product new product
     * @return whether product is existed
     */
    public boolean updateProduct(long id, Product product) {
        product.setId(id);
        // the product doesn't exist
        if (products.get(id) == null) {
            return false;
        }
        products.remove(id);
        products.put(id, product);
        return true;
    }

}
