package edu.whu.Week4.controller;

import edu.whu.Week4.entity.Product;
import edu.whu.Week4.service.ManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "商品管理")
@RestController
@RequestMapping("manage")
public class ManageController {

    final ManageService service;

    public ManageController(ManageService service) {
        this.service = service;
    }

    @ApiOperation("查询所有的商品")
    @GetMapping("")
    public ResponseEntity<List<Product>> getProducts() {
        return new ResponseEntity<>(service.getProducts(), HttpStatus.OK);
    }

    @ApiOperation("根据Id查询相应的商品")
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@ApiParam("商品Id") @PathVariable long id) {
        Product product = service.getProduct(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @ApiOperation("当Id不重复时添加新商品")
    @PostMapping("")
    public ResponseEntity<Product> addProduct(@ApiParam("新的商品") @RequestBody Product product) {
        boolean result = service.addProduct(product);
        if (result) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(service.getProduct(product.getId()), HttpStatus.CONFLICT);
        }
    }

    @ApiOperation("更新指定Id商品信息")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(
            @ApiParam("商品Id") @PathVariable long id,
            @ApiParam("商品数据") @RequestBody Product product) {
        boolean result = service.updateProduct(id, product);
        if (result) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ApiOperation("删除指定Id的商品")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@ApiParam("商品Id") @PathVariable long id) {
        boolean result = service.deleteProduct(id);
        if (result) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
