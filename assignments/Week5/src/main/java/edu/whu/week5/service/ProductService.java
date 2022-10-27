package edu.whu.week5.service;

import edu.whu.week5.dao.ProductRepository;
import edu.whu.week5.entity.Product;
import edu.whu.week5.entity.Supplier;
import edu.whu.week5.exception.ConflictException;
import edu.whu.week5.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    @Autowired
    public ProductService(ProductRepository repository) {
        productRepository = repository;
    }

    ProductRepository productRepository;

    /**
     * 添加商品
     *
     * @param product 商品
     * @return 加入的商品
     * @throws ConflictException 已存在相同Id的商品
     */
    public Product addProduct(@NonNull Product product) throws ConflictException {
        if (productRepository.findById(product.getId()).isPresent()) {
            throw new ConflictException("添加商品失败：商品已经存在");
        }
        return productRepository.save(product);
    }

    /**
     * 删除商品
     *
     * @param id 商品Id
     * @throws NotFoundException Id对应的商品不存在
     */
    public void deleteProduct(@NonNull String id) throws NotFoundException {
        if (productRepository.findById(id).isPresent()) {
            productRepository.deleteById(id);
            return;
        }
        throw new NotFoundException(String.format("删除商品失败：商品 %s 不存在", id));
    }

    /**
     * 根据Id查找商品
     *
     * @param id 商品Id
     * @return 查找到的商品
     * @throws NotFoundException Id对应的商品不存在
     */
    public Product getProduct(@NonNull String id) throws NotFoundException {
        if (productRepository.findById(id).isPresent()) {
            return productRepository.findById(id).get();
        }
        throw new NotFoundException(String.format("查找商品失败：商品 %s 不存在", id));
    }

    /**
     * 根据Id更新商品的信息
     *
     * @param id      需更新的商品
     * @param product 商品的信息（Id不包含在其中）
     * @return 更新后的商品信息
     * @throws NotFoundException Id对应的商品不存在
     */
    public Product updateProduct(@NonNull String id,
                                 @NonNull Product product) throws NotFoundException {
        if (!productRepository.findById(id).isPresent()) {
            throw new NotFoundException(String.format("更新商品信息失败：商品 %s 不存在", id));
        }
        product.setId(id);
        return productRepository.saveAndFlush(product);
    }

    /**
     * 筛选商品
     *
     * @param condition 筛选条件
     * @param pageable 分页条件
     * @return 查询结果
     */
    public Page<Product> selectProduct(@NonNull Map<String, Object> condition,
                                       @NonNull Pageable pageable) {
        return productRepository.findAll(((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (condition.containsKey("name")) {
                predicates.add(cb.like(root.get("name"),
                        String.format("%%%s%%", condition.get("name"))));
            }
            if (condition.containsKey("priceLow")) {
                predicates.add(cb.ge(root.get("price"),
                        (BigDecimal) condition.get("price")));
            }
            if (condition.containsKey("priceHigh")) {
                predicates.add(cb.le(root.get("price"),
                        (BigDecimal) condition.get("price")));
            }
            if (condition.containsKey("quantityLow")) {
                predicates.add(cb.ge(root.get("quantity"),
                        (long) condition.get("quantity")));
            }
            if (condition.containsKey("quantityHigh")) {
                predicates.add(cb.le(root.get("quantity"),
                        (long) condition.get("quantity")));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        }), pageable);
    }

}
