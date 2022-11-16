package edu.whu.week8.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import edu.whu.week8.condition.ProductCondition;
import edu.whu.week8.entity.Product;
import edu.whu.week8.exception.ConflictException;
import edu.whu.week8.exception.NotFoundException;
import edu.whu.week8.exception.NotSupportArgumentException;
import edu.whu.week8.exception.SqlExecuteException;
import edu.whu.week8.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author steve-liu
 * @since 2022-10-31
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    final IProductService service;

    @Autowired
    public ProductController(IProductService service) {
        this.service = service;
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('user/update')")
    Product addProduct(@RequestBody Product product) throws ConflictException, SqlExecuteException {
        return service.addProduct(product);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('user/update')")
    void deleteProduct(@PathVariable String id) throws NotFoundException {
        service.deleteProduct(id);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('user/query')")
    Product getProduct(@PathVariable String id) throws NotFoundException {
        return service.getProduct(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('user/update')")
    Product updateProduct(
            @PathVariable String id,
            @RequestBody Product product) throws ConflictException, SqlExecuteException, NotFoundException {
        return service.updateProduct(id, product);
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('user/query')")
    IPage<Product> selectProduct(
            ProductCondition condition,
            @RequestParam(defaultValue = "0") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) throws NotSupportArgumentException {
        return service.selectProduct(condition, pageNum, pageSize);
    }

}

