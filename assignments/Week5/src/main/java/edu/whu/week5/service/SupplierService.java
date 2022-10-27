package edu.whu.week5.service;

import edu.whu.week5.dao.SupplierRepository;
import edu.whu.week5.entity.Supplier;
import edu.whu.week5.exception.ConflictException;
import edu.whu.week5.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupplierService {

    @Autowired
    public SupplierService(SupplierRepository repository) {
        supplierRepository = repository;
    }

    SupplierRepository supplierRepository;

    /**
     * 添加供应商
     *
     * @param supplier 供应商
     * @return 加入的供应商
     * @throws ConflictException 已存在相同Id的供应商
     */
    public Supplier addSupplier(Supplier supplier) throws ConflictException {
        if (supplierRepository.findById(supplier.getId()).isPresent()) {
            throw new ConflictException("添加供应商失败：供应商已经存在");
        }
        return supplierRepository.save(supplier);
    }

    /**
     * 删除供应商
     *
     * @param id 供应商Id
     * @throws NotFoundException Id对应的供应商不存在
     */
    public void deleteSupplier(String id) throws NotFoundException {
        if (supplierRepository.findById(id).isPresent()) {
            supplierRepository.deleteById(id);
            return;
        }
        throw new NotFoundException(String.format("删除供应商失败：供应商 %s 不存在", id));
    }

    /**
     * 根据Id查找供应商
     *
     * @param id 供应商Id
     * @return 查找到的供应商
     * @throws NotFoundException Id对应的供应商不存在
     */
    public Supplier getSupplier(String id) throws NotFoundException {
        if (supplierRepository.findById(id).isPresent()) {
            return supplierRepository.findById(id).get();
        }
        throw new NotFoundException(String.format("查找供应商失败：供应商 %s 不存在", id));
    }

    /**
     * 根据Id更新供应商的信息
     *
     * @param id       需更新的供应商
     * @param supplier 供应商的信息（Id不包含在内）
     * @return 更新后的供应商信息
     * @throws NotFoundException Id对应的供应商不存在
     */
    public Supplier updateSupplier(String id, Supplier supplier) throws NotFoundException {
        if (!supplierRepository.findById(id).isPresent()) {
            throw new NotFoundException(String.format("更新供应商信息失败：供应商 %s 不存在", id));
        }
        supplier.setId(id);
        return supplierRepository.saveAndFlush(supplier);
    }

}
