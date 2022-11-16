package edu.whu.week8.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import edu.whu.week8.condition.SupplierCondition;
import edu.whu.week8.entity.Supplier;
import edu.whu.week8.exception.ConflictException;
import edu.whu.week8.exception.NotFoundException;
import edu.whu.week8.exception.NotSupportArgumentException;
import edu.whu.week8.exception.SqlExecuteException;
import edu.whu.week8.service.ISupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author steve-liu
 * @since 2022-10-31
 */
@RestController
@RequestMapping("/supplier")
public class SupplierController {

    final ISupplierService service;

    @Autowired
    public SupplierController(ISupplierService service) {
        this.service = service;
    }

    @PostMapping("")
    Supplier addSupplier(@RequestBody Supplier supplier) throws ConflictException {
        return service.addSupplier(supplier);
    }

    @DeleteMapping("/{id}")
    void deleteSupplier(@PathVariable String id) throws NotFoundException {
        service.deleteSupplier(id);
    }

    @GetMapping("/{id}")
    Supplier getSupplier(@PathVariable String id) throws NotFoundException {
        return service.getSupplier(id);
    }

    @PutMapping("/{id}")
    Supplier updateSupplier(
            @PathVariable String id,
            @RequestBody Supplier supplier) throws ConflictException, SqlExecuteException, NotFoundException {
        return service.updateSupplier(id, supplier);
    }

    @GetMapping("")
    IPage<Supplier> selectSupplier(
            SupplierCondition condition,
            @RequestParam(defaultValue = "0") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) throws NotSupportArgumentException {
        return service.selectSupplier(condition, pageNum, pageSize);
    }

}

